package acme.features.entrepreneur.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamChecker;
import acme.entities.activities.Activity;
import acme.entities.configurations.Configuration;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.features.entrepreneur.activity.EntrepreneurActivityRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepreneurInvestmentRoundUpdateService implements AbstractUpdateService<Entrepreneur, InvestmentRound>{

	@Autowired
	EntrepreneurInvestmentRoundRepository repository;
	@Autowired
	EntrepreneurActivityRepository activityRepository;
	
	@Override
	public boolean authorise(Request<InvestmentRound> request) {
		assert request != null;

		boolean result;
		int investmentRoundId = request.getModel().getInteger("id");
		InvestmentRound investmentRound = this.repository.findOneById(investmentRoundId);
		Entrepreneur entrepreneur = investmentRound.getEntrepreneur();
		Principal principal;
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void bind(Request<InvestmentRound> request, InvestmentRound entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(Request<InvestmentRound> request, InvestmentRound entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "round", "title", "description", "moneyAmount", "moreInfo", "finalMode", "ticker",
				"creationMoment"

		);}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound res;
		int id;
		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);

		return res;
	}

	@Override
	public void validate(Request<InvestmentRound> request, InvestmentRound entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		int investmentRoundId = request.getModel().getInteger("id");
		Collection<Activity> activities = this.activityRepository.findManyByInvestmentRoundId(investmentRoundId);
		errors.state(request, AmountFinalMode(entity.isFinalMode(),entity.getMoneyAmount(), getSumBudgets(activities)) == true, "finalMode",
				"Entrepreneur.InvestmentRound.error.finalMode.notvalid");
		boolean spamCheckOk;
		Configuration configuration = this.repository.findConfiguration();
		String spam = request.getModel().getString("title")+ " " + request.getModel().getString("description") ;
		spamCheckOk = SpamChecker.spamChecker(configuration, spam);
		errors.state(request, !spamCheckOk, "*", "Entrepreneur.InvestmentRound.error.update.spam");
		
	}

	private Double getSumBudgets(Collection<Activity> activities) {
		Double suma = 0.0;
		for (Activity a: activities) {
			suma=+a.getBudget().getAmount();
		}
		return suma;
	}
	private boolean AmountFinalMode(boolean finalMode, Money moneyAmount, Double sumBudgets) {
		boolean res= false;
		if(moneyAmount.getAmount()!=null &&moneyAmount.getCurrency()!=null) {
			if(finalMode && moneyAmount.getAmount()<=sumBudgets) {
				res= true;
			}
			if(!finalMode)res=true;	
		}
		return res;
	}
	@Override
	public void update(Request<InvestmentRound> request, InvestmentRound entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
		
	}

}
