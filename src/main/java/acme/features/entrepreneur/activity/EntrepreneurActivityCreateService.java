package acme.features.entrepreneur.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurActivityCreateService implements AbstractCreateService<Entrepreneur, Activity>{
	
	@Autowired
	EntrepreneurActivityRepository repository;

	@Override
	public boolean authorise(Request<Activity> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(Request<Activity> request, Activity entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "investmentRoundId");
	}

	@Override
	public void unbind(Request<Activity> request, Activity entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "deadline","budget");
		model.setAttribute("investmentRoundId", request.getModel().getInteger("investmentRoundId"));
		
	}

	@Override
	public Activity instantiate(Request<Activity> request) {
		Activity result = new Activity();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		InvestmentRound investmentRound = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		result.setInvestmentRound(investmentRound);
		return result;
	}

	@Override
	public void validate(Request<Activity> request, Activity entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;	
	}
	
	

	@Override
	public void create(Request<Activity> request, Activity entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
		
	}

}
