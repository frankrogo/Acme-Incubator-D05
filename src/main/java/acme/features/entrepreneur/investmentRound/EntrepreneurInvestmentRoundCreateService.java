
package acme.features.entrepreneur.investmentRound;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamChecker;
import acme.entities.activities.Activity;
import acme.entities.configurations.Configuration;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Entrepreneur;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.features.entrepreneur.activity.EntrepreneurActivityRepository;
import acme.features.entrepreneur.forum.EntrepreneurForumRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurInvestmentRoundCreateService implements AbstractCreateService<Entrepreneur, InvestmentRound> {

	@Autowired
	EntrepreneurInvestmentRoundRepository repository;
	@Autowired
	EntrepreneurActivityRepository activityRepository;
	@Autowired
	EntrepreneurForumRepository forumRepository;
	@Autowired
	AuthenticatedMessengerRepository messengerRepository;
	
	

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
		model.setAttribute("titleForum", "");
	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		InvestmentRound result;
		Principal principal = request.getPrincipal();



		result = new InvestmentRound();
		Entrepreneur entrepreneur = this.repository.findEntrepreneurById(principal.getActiveRoleId());
		result.setEntrepreneur(entrepreneur);
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		String sss = getSSS(entrepreneur.getSector());
		String año = String.valueOf(result.getCreationMoment().getYear());
		String yy = año.substring(año.length() - 2);

		result.setTicker(sss + "-" + yy + "-" + getNNNNNN());

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
		
		errors.state(request, !request.getModel().getString("titleForum").isEmpty(), "titleForum","Entrepreneur.InvestmentRound.error.titleForum.notblank");
		
		boolean spamCheckOk;
		Configuration configuration = this.repository.findConfiguration();
		String spam = request.getModel().getString("title")+ " " + request.getModel().getString("description") + " " + request.getModel().getString("titleActivity")  ;
		spamCheckOk = SpamChecker.spamChecker(configuration, spam);
		errors.state(request, !spamCheckOk, "*", "Entrepreneur.InvestmentRound.error.spam");
	}

	private boolean moneyBudget(String budget) {
		boolean res = true;
		if (!budget.contains("€") || budget.matches(".*[a-zA-Z]+.*") || budget.contains("$")
				|| !budget.matches(".*\\d.*") || budget.isEmpty() ) {
			res = false;
		}
		return res;
	}

	private String getSSS(String sector) {
		String res = "XXX";
		if(sector.length()==1) {
			res= sector.toUpperCase() + "XX" ;
		}else if(sector.length()==2) {
			res= sector.toUpperCase() + "X";
		}else if(sector.length()>2) {
			res = sector.substring(0,3).toUpperCase();
		}
		return res;
		
	}
	private String getNNNNNN() {
		String random = String.valueOf((int) (Math.random() * 999999 + 1));
		String res = random;
		for (int i = 6; i  > random.length(); i--) {
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
		assert request != null;
		assert entity != null;
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
		
		Forum forum = new Forum();
		Principal principal = request.getPrincipal();
		int userAccountId = principal.getAccountId();
		Authenticated authenticated = this.repository.findOneAuthenticatedByUserAccountId(userAccountId);
		forum.setAuthenticated(authenticated);
		forum.setInvestmentRound(entity);
		forum.setTitle(request.getModel().getString("titleForum"));
		this.forumRepository.save(forum);
		
		Messenger messenger = new Messenger();
		messenger.setAuthenticated(authenticated);
		messenger.setForum(forum);
		messenger.setOwnsTheForum(true);
		this.messengerRepository.save(messenger);
		
		
		
		


	}
}
