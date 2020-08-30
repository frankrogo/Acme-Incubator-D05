
package acme.features.entrepreneur.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepreneurActivityShowService implements AbstractShowService<Entrepreneur, Activity> {

	@Autowired
	EntrepreneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result;
		int activityId;
		InvestmentRound investmentRound;
		Entrepreneur entrepreneur;
		Principal principal;

		activityId = request.getModel().getInteger("id");
		investmentRound = this.repository.finOneInvestmentRoundByActivityId(activityId);
		entrepreneur = investmentRound.getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment", "deadline", "budget");
		model.setAttribute("investmentRoundId", entity.getInvestmentRound().getId());
		model.setAttribute("finalMode", entity.getInvestmentRound().isFinalMode());
		
		Integer numberOfActivities = this.repository.findManyByInvestmentRoundId(entity.getInvestmentRound().getId()).size();
		model.setAttribute("numberOfActivities", numberOfActivities);
		
		
	}

	@Override
	public Activity findOne(final Request<Activity> request) {
		assert request != null;

		Activity result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

}
