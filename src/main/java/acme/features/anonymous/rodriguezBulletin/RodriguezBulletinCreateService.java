
package acme.features.anonymous.rodriguezBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rodriguezBulletins.rodriguezBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class RodriguezBulletinCreateService implements AbstractCreateService<Anonymous, rodriguezBulletin> {

	@Autowired
	RodriguezBulletinRepository repository;


	@Override
	public boolean authorise(final Request<rodriguezBulletin> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<rodriguezBulletin> request, final rodriguezBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<rodriguezBulletin> request, final rodriguezBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "author", "description");
	}

	@Override
	public rodriguezBulletin instantiate(final Request<rodriguezBulletin> request) {
		assert request != null;

		rodriguezBulletin res;
		Date moment;
		res = new rodriguezBulletin();
		moment = new Date(System.currentTimeMillis() - 1);

		res.setAuthor("Frank");
		res.setDescription("This is my bulletin");
		res.setMoment(moment);
		return res;
	}

	@Override
	public void validate(final Request<rodriguezBulletin> request, final rodriguezBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<rodriguezBulletin> request, final rodriguezBulletin entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}
}
