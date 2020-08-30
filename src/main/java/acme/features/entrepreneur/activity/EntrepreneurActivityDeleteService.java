package acme.features.entrepreneur.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepreneurActivityDeleteService implements AbstractDeleteService<Entrepreneur, Activity>{

	@Autowired
	EntrepreneurActivityRepository repository;
	
	@Override
	public boolean authorise(Request<Activity> request) {
		return true;
	}

	@Override
	public void bind(Request<Activity> request, Activity entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
		
	}

	@Override
	public void unbind(Request<Activity> request, Activity entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creationMoment", "deadline", "budget");
		
	}

	@Override
	public Activity findOne(Request<Activity> request) {
		assert request != null;

		Activity result;
		int id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(Request<Activity> request, Activity entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(Request<Activity> request, Activity entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
		
	}

}
