
package acme.features.administrator.overture;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.overtures.Overture;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorOvertureCreateService implements AbstractCreateService<Administrator, Overture> {

	@Autowired
	AdministratorOvertureRepository repository;


	@Override
	public boolean authorise(final Request<Overture> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "creationMoment");
	}

	@Override
	public void unbind(final Request<Overture> request, final Overture entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "deadline", "description", "minMoney", "maxMoney", "email");
	}

	@Override
	public Overture instantiate(final Request<Overture> request) {
		assert request != null;

		Overture res = new Overture();
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		res.setCreationMoment(moment);
		return res;
	}

	@Override
	public void validate(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Deadline validation
		Calendar calendar;
		Date minimumDeadline;

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			minimumDeadline = calendar.getTime();
			boolean future = entity.getDeadline().after(minimumDeadline);
			errors.state(request, future, "deadline", "administrator.overture.error.future-deadline");
		}

		//Currency validation
		Money minMoney = entity.getMinMoney();
		Money maxMoney = entity.getMaxMoney();

		boolean eurMin, eurMax, maxMoreThanMin;

		if (!errors.hasErrors("minMoney") && maxMoney != null) {
			maxMoreThanMin = maxMoney.getAmount() > minMoney.getAmount();
			errors.state(request, maxMoreThanMin, "maxMoney", "administrator.overture.error.maxMoreThanMin");
		}

		if (!errors.hasErrors("minMoney")) {
			eurMin = minMoney.getCurrency().equals("EUR") || minMoney.getCurrency().equals("€");
			errors.state(request, eurMin, "minMoney", "administrator.overture.error.minMoney");
		}

		if (!errors.hasErrors("maxMoney")) {
			eurMax = maxMoney.getCurrency().equals("EUR") || maxMoney.getCurrency().equals("€");
			errors.state(request, eurMax, "maxMoney", "administrator.overture.error.maxMoney");
		}
	}

	@Override
	public void create(final Request<Overture> request, final Overture entity) {

		assert request != null;
		assert entity != null;

		Date now = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(now);
		this.repository.save(entity);
	}

}
