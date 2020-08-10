
package acme.features.administrator.inquiry;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquiry;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorInquiryCreateService implements AbstractCreateService<Administrator, Inquiry> {

	@Autowired
	AdministratorInquiryRepository repository;


	@Override
	public boolean authorise(final Request<Inquiry> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Inquiry> request, final Inquiry entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "creationMoment");
	}

	@Override
	public void unbind(final Request<Inquiry> request, final Inquiry entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "deadline", "description", "minMoney", "maxMoney", "email");
	}

	@Override
	public Inquiry instantiate(final Request<Inquiry> request) {
		assert request != null;

		Inquiry res = new Inquiry();
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		res.setCreationMoment(moment);
		return res;
	}

	@Override
	public void validate(final Request<Inquiry> request, final Inquiry entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Deadline validation
		Calendar calendar;
		Date minimumDeadline;

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, +7);
			minimumDeadline = calendar.getTime();
			boolean future = entity.getDeadline().after(minimumDeadline);
			errors.state(request, future, "deadline", "administrator.inquiry.error.future-deadline");
		}

		//Currency validation
		Money minMoney = entity.getMinMoney();
		Money maxMoney = entity.getMaxMoney();

		boolean eurMin, eurMax, maxMoreThanMin;

		if (!errors.hasErrors("maxMoney")) {
			eurMax = maxMoney.getCurrency().equals("EUR") || maxMoney.getCurrency().equals("€");
			errors.state(request, eurMax, "maxMoney", "administrator.inquiry.error.maxMoney");
		}

		if (!errors.hasErrors("minMoney") && maxMoney != null) {
			maxMoreThanMin = maxMoney.getAmount() > minMoney.getAmount();
			errors.state(request, maxMoreThanMin, "maxMoney", "administrator.inquiry.error.maxMoreThanMin");
		}

		if (!errors.hasErrors("minMoney")) {
			eurMin = minMoney.getCurrency().equals("EUR") || minMoney.getCurrency().equals("€");
			errors.state(request, eurMin, "minMoney", "administrator.inquiry.error.minMoney");
		}
	}

	@Override
	public void create(final Request<Inquiry> request, final Inquiry entity) {

		assert request != null;
		assert entity != null;

		Date now = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(now);
		this.repository.save(entity);
	}

}
