package acme.features.entrepreneur.activity;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamChecker;
import acme.entities.activities.Activity;
import acme.entities.configurations.Configuration;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepreneurActivityUpdateService implements AbstractUpdateService<Entrepreneur, Activity>{
	
	@Autowired
	EntrepreneurActivityRepository repository;
	@Override
	public boolean authorise(Request<Activity> request) {
		boolean result;
		InvestmentRound investmentRound;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentRound = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		entrepreneur = investmentRound.getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
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

		request.unbind(entity, model, "title", "deadline", "budget", "creationMoment");
		model.setAttribute("finalmode", entity.getInvestmentRound().isFinalMode());
		model.setAttribute("previousBudget", entity.getBudget().getAmount());

		
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
		Collection<Activity> activities = this.repository.findManyByInvestmentRoundId(entity.getInvestmentRound().getId());
		InvestmentRound ivr = entity.getInvestmentRound();
		Double previousBudget= request.getModel().getDouble("previousBudget");
		if (!errors.hasErrors("budget")) {
			Double actualBudget = entity.getBudget().getAmount();
			errors.state(request, actualBudget != null, "budget", "entrepreneur.activity.error.budget.null");
			Double resta = 0.0;
			boolean res = this.overAmount(activities, ivr, actualBudget, previousBudget); //si false, la activity actual excede el amount
			if (res == false && !errors.hasErrors("budget")) {//en ese caso
				resta = this.quantityLeft(activities, ivr, actualBudget,previousBudget);//print cuanto me he pasado
				if (request.getLocale().getLanguage().equals("es")) {
					errors.state(request, res, "budget", "El presupuesto de las actividades ha superado la cantidad monetaria de la ronda de inversión por: " + resta + "EUR" );
				}
				if (request.getLocale().getLanguage().equals("en")) {
					errors.state(request, res, "budget", "The budget of the activities have surpased the investment round amount by: " + resta + "EUR" );
				}
			}
		}
		//Deadline validation
        Calendar calendar;
        Date minimumDeadline;

        if (!errors.hasErrors("deadline")) {
            calendar = new GregorianCalendar();
            minimumDeadline = calendar.getTime();
            boolean future = entity.getDeadline().after(minimumDeadline);
            errors.state(request, future, "deadline", "entrepreneur.activity.error.future-deadline");
        }
        
        Configuration configuration = this.repository.findConfiguration();
		String spamTitle = request.getModel().getString("title") ;
		boolean spamCheckTitle = SpamChecker.spamChecker(configuration, spamTitle);
		errors.state(request, !spamCheckTitle, "title", "Entrepreneur.activity.error.spam");

	}

	private boolean overAmount(final Collection<Activity> activities, final InvestmentRound ivr, final Double actualBudget, Double previousBudget) {
		boolean res = true;
		Double ivrAmount = ivr.getMoneyAmount().getAmount();
		Double rest = 0.0;
		for (Activity a : activities) {
			Double acMoney = a.getBudget().getAmount();
			rest = acMoney + rest ;
		}
		if (rest - previousBudget + actualBudget > ivrAmount) {
			res = false;
		} else {
			return res;
		}
		return res;
	}

	private Double quantityLeft(final Collection<Activity> activities, final InvestmentRound ivr, final Double actualBudget, Double previousBudget) {
		Double rest = 0.0;
		Double resta = 0.0;
		Double acMoney =0.0;
		Double ivrAmount = ivr.getMoneyAmount().getAmount();
		for (Activity a : activities) {
			acMoney = acMoney + a.getBudget().getAmount();
			
		}
		rest = actualBudget- previousBudget + acMoney;
		
		resta = rest - ivrAmount;
		return resta;
	}

	@Override
	public void update(Request<Activity> request, Activity entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
