
package acme.features.entrepreneur.investmentRound;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.features.entrepreneur.activity.EntrepreneurActivityRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurInvestmentRoundCreateService implements AbstractCreateService<Entrepreneur, InvestmentRound> {

	@Autowired
	EntrepreneurInvestmentRoundRepository repository;
	@Autowired
	EntrepreneurActivityRepository activityRepository;

	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "round", "title", "description", "moneyAmount", "moreInfo", "finalMode", "ticker",
				"creationMoment", "entrepreneur"

		);
		model.setAttribute("titleActivity", "");
		model.setAttribute("deadLineActivity", "");
		model.setAttribute("budgetActivity", "");
	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		InvestmentRound result;
		Principal principal = request.getPrincipal();


		result = new InvestmentRound();
		Entrepreneur entrepreneur = this.repository.findEntrepreneurById(principal.getActiveRoleId());
		result.setEntrepreneur(entrepreneur);
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		String sss = entrepreneur.getAuthorityName().substring(0, 3).toUpperCase();
		String año = String.valueOf(result.getCreationMoment().getYear());
		String yy = año.substring(año.length() - 2);

		result.setTicker(sss + "-" + yy + "-" + getNNNNNNN());

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		String titleActivity = request.getModel().getString("titleActivity");
		Date deadLineActivity = request.getModel().getDate("deadLineActivity");
		String budgetActivity = request.getModel().getString("budgetActivity");
		Boolean finalMode = entity.isFinalMode();
		errors.state(request, !titleActivity.equals(""), "titleActivity","Entrepreneur.InvestmentRound.error.titleActivity.notblank");
		errors.state(request, deadLineActivity != null, "deadLineActivity","Entrepreneur.InvestmentRound.error.deadLineActivity.notnull");
		errors.state(request, !budgetActivity.isEmpty(), "budgetActivity","Entrepreneur.InvestmentRound.error.budgetActivity.notblank");
		errors.state(request, moneyBudget(budgetActivity) == true, "budgetActivity","Entrepreneur.InvestmentRound.error.budgetActivity.notvalid");
		errors.state(request, finalModeValidate(entity.getMoneyAmount(), budgetActivity, finalMode) == true, "finalMode",
				"Entrepreneur.InvestmentRound.error.finalMode.notvalid");

	}

	private boolean moneyBudget(String budget) {
		boolean res = true;
		if (!budget.contains("€") || budget.matches(".*[a-zA-Z]+.*") || budget.contains("$")
				|| !budget.matches(".*\\d.*") || budget.isEmpty() ) {
			res = false;
		}
		return res;
	}

	private String getNNNNNNN() {
		String random = String.valueOf((int) (Math.random() * 99999999 + 1));
		String res = random;
		for (int i = 7; i > random.length(); i--) {
			res = "0" + res;
		}
		return res;
	}

	private boolean finalModeValidate(Money moneyAmount, String budgetActivity, boolean finalMode) {
		boolean res = false;
		if (!budgetActivity.isEmpty()&& budgetActivity!=null && moneyAmount != null && budgetActivity!=null && moneyAmount.getCurrency().equals("€") && moneyAmount.getAmount()!=null) {
			if (moneyBudget(budgetActivity) ) {
				Double budget = Double.valueOf(budgetActivity.replace("€", "").replace(".", ""));
				if (finalMode && budget >= moneyAmount.getAmount()) {
					res = true;
				}	
			}
			if (!finalMode) {
				res = true;
			}
		}
		return res;

	}

	@Override
	public void create(final Request<InvestmentRound> request, final InvestmentRound entity) {
		this.repository.save(entity);
		String titleActivity = request.getModel().getString("titleActivity");
		Date deadLineActivity = request.getModel().getDate("deadLineActivity");
		String budgetActivity = request.getModel().getString("budgetActivity");
		Money budget = new Money();
		budget.setCurrency("€");
		String amount = budgetActivity.replace("€", "").replace(".", "");

		budget.setAmount(Double.valueOf(amount));

		Activity activity = new Activity();
		activity.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		activity.setDeadline(deadLineActivity);
		activity.setTitle(titleActivity);
		activity.setBudget(budget);
		activity.setInvestmentRound(entity);
		this.activityRepository.save(activity);

	}
}
