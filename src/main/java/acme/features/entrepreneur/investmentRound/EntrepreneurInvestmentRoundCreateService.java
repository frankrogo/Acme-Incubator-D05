
package acme.features.entrepreneur.investmentRound;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	EntrepreneurInvestmentRoundRepository	repository;
	@Autowired
	EntrepreneurActivityRepository			activityRepository;
	@Autowired
	EntrepreneurForumRepository				forumRepository;
	@Autowired
	AuthenticatedMessengerRepository		messengerRepository;


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
		request.unbind(entity, model, "round", "title", "description", "moneyAmount", "moreInfo", "finalMode", "ticker", "creationMoment", "entrepreneur"

		);
		model.setAttribute("titleActivity", "");
		model.setAttribute("deadLineActivity", "");
		model.setAttribute("budgetActivity", "");
		model.setAttribute("titleForum", "");
		model.setAttribute("fecha", "");
		
	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		InvestmentRound result;
		Principal principal = request.getPrincipal();

		result = new InvestmentRound();
		Entrepreneur entrepreneur = this.repository.findEntrepreneurById(principal.getActiveRoleId());
		result.setEntrepreneur(entrepreneur);
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		String sss = this.getSSS(entrepreneur.getSector());
		String año = String.valueOf(result.getCreationMoment().getYear());
		String yy = año.substring(año.length() - 2);

		result.setTicker(sss + "-" + yy + "-" + this.getNNNNNN());

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		String titleActivity = request.getModel().getString("titleActivity");
		String budgetActivity = request.getModel().getString("budgetActivity");
		String deadLineActivity = request.getModel().getString("deadLineActivity");
		Boolean finalMode = entity.isFinalMode();
		errors.state(request, !titleActivity.equals(""), "titleActivity", "Entrepreneur.InvestmentRound.error.titleActivity.notblank");
		errors.state(request, !deadLineActivity.equals(""), "deadLineActivity", "Entrepreneur.InvestmentRound.error.deadLineActivity.notblank");
		errors.state(request, deadLineActivity.matches("[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"), "deadLineActivity", "Entrepreneur.InvestmentRound.error.deadLineActivity.format");
		
		if(deadLineActivity.matches("[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]")) {
			Calendar calendar;
			Date minimumDeadline;
			calendar = new GregorianCalendar();
			minimumDeadline = calendar.getTime();
			request.getModel().setAttribute("fecha", deadLineActivity);
			Date activityDeadline = request.getModel().getDate("fecha");
			boolean future = activityDeadline.after(minimumDeadline);
			errors.state(request, future, "fecha", "entrepreneur.investmentRound.error.deadlineActivity.future");
			
		}
		
//		//Deadline validation
//		Calendar calendar;
//		Date minimumDeadline;
//
//		String deadLineActivity = request.getModel().getString("deadLineActivity");
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd hh:mm");
//
//		boolean notEmpty = true;
//		boolean format = true;
//
//		if (!errors.hasErrors("deadLineActivity") && deadLineActivity.isEmpty()) {//null o vacio
//			notEmpty = false;
//			errors.state(request, notEmpty, "deadLineActivity", "Entrepreneur.InvestmentRound.error.deadLineActivity.notnull");
//		} else if (!errors.hasErrors("deadLineActivity") && !deadLineActivity.isEmpty()) { //no null ni vacio
//			if (!deadLineActivity.matches("[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]")) {
//				format = false;
//				errors.state(request, format, "deadlineActivity", "entrepreneur.investmentRound.error.deadlineActivity.format");
//			} else {//cumple el formato apropiado y no hay error previo con el deadline
//				if (!errors.hasErrors("deadlineActivity")) {
//					calendar = new GregorianCalendar();
//					minimumDeadline = calendar.getTime();
//					Date deadLineDate = null;
//					try {
//						deadLineDate = formatter.parse(deadLineActivity);
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					boolean future = deadLineDate.after(minimumDeadline);
//					errors.state(request, future, "deadlineActivity", "entrepreneur.investmentRound.error.deadlineActivity.future");
//				}
//			}
//
//		}

		errors.state(request, !budgetActivity.isEmpty(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.notblank");
		errors.state(request, request.getModel().getAttribute("moneyAmount")!="", "moneyAmount", "Entrepreneur.InvestmentRound.error.moneyAmount.notblank");
		errors.state(request, this.moneyBudget(budgetActivity), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.notvalid");
		errors.state(request, this.finalModeValidate(entity.getMoneyAmount(), budgetActivity, finalMode) == true, "finalMode", "Entrepreneur.InvestmentRound.error.finalMode.notvalid");
		
		if (request.getModel().getAttribute("moneyAmount")!="") {
			if (budgetActivity != "" && budgetActivity.contains("€")) {
				String valor = budgetActivity;
				Double budget = Double.valueOf(valor.replace("€", "").replace(".", ""));
				errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity",
						"Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
			}
			if (budgetActivity != "" && budgetActivity.contains("EUR")) {
				String valor = budgetActivity;
				Double budget = Double.valueOf(valor.replace("EUR", "").replace(".", ""));
				errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity",
						"Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
			}
		} else {
			errors.state(request, request.getModel().getAttribute("moneyAmount")!=null, "moneyAmount",
					"Entrepreneur.InvestmentRound.error.moneyAmount.notblank");
		}
		errors.state(request, !request.getModel().getString("titleForum").isEmpty(), "titleForum", "Entrepreneur.InvestmentRound.error.titleForum.notblank");

		boolean spamCheckOk;
		Configuration configuration = this.repository.findConfiguration();
		String spam = request.getModel().getString("title") + " " + request.getModel().getString("description") + " " + request.getModel().getString("titleActivity");
		spamCheckOk = SpamChecker.spamChecker(configuration, spam);
		errors.state(request, !spamCheckOk, "*", "Entrepreneur.InvestmentRound.error.spam");
	}

	private boolean moneyBudget(final String budget) {
		boolean res = false;
		if ( !budget.isEmpty() &&(budget.matches("([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?\\s?\\€") || 
				budget.matches("^\\€\\s?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?") || 
				budget.matches("([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?\\s?(?:^|)EUR(?:$|)") ||
				budget.matches("(?:^|)EUR(?:$|)\\s?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?"))) {
			res = true;
		}
		return res;
	}

	private String getSSS(final String sector) {
		String res = "XXX";
		if (sector.length() == 1) {
			res = sector.toUpperCase() + "XX";
		} else if (sector.length() == 2) {
			res = sector.toUpperCase() + "X";
		} else if (sector.length() > 2) {
			res = sector.substring(0, 3).toUpperCase();
		}
		return res;

	}
	private String getNNNNNN() {
		String random = String.valueOf((int) (Math.random() * 999999 + 1));
		String res = random;
		for (int i = 6; i > random.length(); i--) {
			res = "0" + res;
		}
		return res;
	}
	private boolean finalModeValidate(final Money moneyAmount, final String budgetActivity, final boolean finalMode) {
		boolean res = false;
		if (!budgetActivity.isEmpty() && budgetActivity != null && moneyAmount != null && budgetActivity != null
				&& (budgetActivity.contains("€") || budgetActivity.contains("EUR"))
				&& moneyAmount.getAmount() != null) {
			if (this.moneyBudget(budgetActivity)) {
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
		Date deadLineActivity = request.getModel().getDate("fecha");
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
