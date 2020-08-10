
package acme.features.anonymous.pereaBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pereaBulletins.pereaBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class PereaBulletinCreateService implements AbstractCreateService<Anonymous, pereaBulletin> {

	@Autowired
	PereaBulletinRepository repository;


	@Override
	public boolean authorise(final Request<pereaBulletin> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<pereaBulletin> request, final pereaBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<pereaBulletin> request, final pereaBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "body");
	}

	@Override
	public pereaBulletin instantiate(final Request<pereaBulletin> request) {
		assert request != null;

		pereaBulletin res;
		Date moment;
		res = new pereaBulletin();
		moment = new Date(System.currentTimeMillis() - 1);

		res.setTitle("How to make awesome news");
		res.setBody("This is the first step to learn properly:");
		res.setMoment(moment);
		return res;
	}

	@Override
	public void validate(final Request<pereaBulletin> request, final pereaBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<pereaBulletin> request, final pereaBulletin entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}
}
