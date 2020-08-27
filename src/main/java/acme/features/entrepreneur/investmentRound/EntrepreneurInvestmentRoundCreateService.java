
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
		model.setAttribute("es", request.getLocale().getLanguage().equals("es"));

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

		String first = this.getNNNNNN();
		result.setTicker(this.tickerChecker(sss, yy, first));
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
		if (request.getLocale().getDisplayLanguage().equals("english") || request.getLocale().getDisplayLanguage().equals("English") || request.getLocale().getLanguage().equals("en")) {
			errors.state(request, deadLineActivity.matches("[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"), "deadLineActivity", "Entrepreneur.InvestmentRound.error.deadLineActivity.format");
		}
		if (request.getLocale().getDisplayLanguage().equals("spanish") || request.getLocale().getDisplayLanguage().equals("Spanish") || request.getLocale().getLanguage().equals("es")) {
			errors.state(request, deadLineActivity.matches("(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/[0-9]{4} (2[0-3]|[01][0-9]):[0-5][0-9]"), "deadLineActivity", "Entrepreneur.InvestmentRound.error.deadLineActivity.format");
		}
		if (deadLineActivity.matches("[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]") && request.getLocale().getLanguage().equals("en")
			|| deadLineActivity.matches("(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/[0-9]{4} (2[0-3]|[01][0-9]):[0-5][0-9]") && request.getLocale().getLanguage().equals("es")) {

			Calendar calendar;
			Date minimumDeadline;
			calendar = new GregorianCalendar();
			minimumDeadline = calendar.getTime();
			request.getModel().setAttribute("fecha", deadLineActivity);
			Date activityDeadline = request.getModel().getDate("fecha");
			boolean future = activityDeadline.after(minimumDeadline);
			errors.state(request, future, "fecha", "entrepreneur.investmentRound.error.deadlineActivity.future");

		}

		errors.state(request, !budgetActivity.isEmpty(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.notblank");
		errors.state(request, request.getModel().getAttribute("moneyAmount") != "", "moneyAmount", "Entrepreneur.InvestmentRound.error.moneyAmount.notblank");

		if (request.getLocale().getDisplayLanguage().equals("spanish") || request.getLocale().getDisplayLanguage().equals("Spanish") || request.getLocale().getLanguage().equals("es")) {
			errors.state(request, this.moneyBudgetEs(budgetActivity), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.notvalid");
			if (this.moneyBudgetEs(budgetActivity)) {
				errors.state(request, this.finalModeValidateEs(entity.getMoneyAmount(), budgetActivity, finalMode) == true, "finalMode", "Entrepreneur.InvestmentRound.error.finalMode.notvalid");
			}
		}
		if (request.getLocale().getDisplayLanguage().equals("english") || request.getLocale().getDisplayLanguage().equals("English") || request.getLocale().getLanguage().equals("en")) {
			errors.state(request, this.moneyBudgetEn(budgetActivity), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.notvalid");
			if (this.moneyBudgetEn(budgetActivity)) {
				errors.state(request, this.finalModeValidateEn(entity.getMoneyAmount(), budgetActivity, finalMode) == true, "finalMode", "Entrepreneur.InvestmentRound.error.finalMode.notvalid");
			}

		}

		//spanish
		if (request.getLocale().getDisplayLanguage().equals("spanish") || request.getLocale().getDisplayLanguage().equals("Spanish") || request.getLocale().getLanguage().equals("es")) {
			if (this.moneyBudgetEs(budgetActivity)) {
				if (request.getModel().getAttribute("moneyAmount") != "") {
					if (budgetActivity != "" && budgetActivity.contains("€")) {
						String valor = budgetActivity;
						Double budget = Double.valueOf(valor.replace("€", "").replace(",", "."));
						errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
					}
					if (budgetActivity != "" && budgetActivity.contains("EUR")) {
						String valor = budgetActivity;
						Double budget = Double.valueOf(valor.replace("EUR", "").replace(",", "."));
						errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
					}
				}
			}
		}
		//english
		else if (request.getLocale().getDisplayLanguage().equals("english") || request.getLocale().getDisplayLanguage().equals("English") || request.getLocale().getLanguage().equals("en")) {
			if (this.moneyBudgetEs(budgetActivity)) {
				if (request.getModel().getAttribute("moneyAmount") != "") {
					if (budgetActivity != "" && budgetActivity.contains("€")) {
						String valor = budgetActivity;
						Double budget = Double.valueOf(valor.replace("€", "").replace(",", ""));
						errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
					}
					if (budgetActivity != "" && budgetActivity.contains("EUR")) {
						String valor = budgetActivity;
						Double budget = Double.valueOf(valor.replace("EUR", "").replace(",", ""));
						errors.state(request, budget <= entity.getMoneyAmount().getAmount(), "budgetActivity", "Entrepreneur.InvestmentRound.error.budgetActivity.novalidAmount");
					}
				}
			}
		} else {
			errors.state(request, request.getModel().getAttribute("moneyAmount") != null, "moneyAmount", "Entrepreneur.InvestmentRound.error.moneyAmount.notblank");
		}

		errors.state(request, !request.getModel().getString("titleForum").isEmpty(), "titleForum", "Entrepreneur.InvestmentRound.error.titleForum.notblank");

		boolean spamCheckOk;
		Configuration configuration = this.repository.findConfiguration();
		String spam = request.getModel().getString("title") + " " + request.getModel().getString("description") + " " + request.getModel().getString("titleActivity");
		spamCheckOk = SpamChecker.spamChecker(configuration, spam);
		errors.state(request, !spamCheckOk, "*", "Entrepreneur.InvestmentRound.error.spam");
	}

	private boolean moneyBudgetEs(final String budget) {
		boolean res = false;
		if (!budget.isEmpty() && (budget.matches("^[+-]?[0-9]{1,3}(?:[0-9]*(?:[,][0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)\\s?€") || budget.matches("€\\s?[+-]?[0-9]{1,3}(?:[0-9](?:[,][0-9]{2})?|(?:.[0-9]{3})(?:,[0-9]{2})?)")
			|| budget.matches("^[+-]?[0-9]{1,3}(?:[0-9]*(?:[,][0-9]{2})?|(?:\\.[0-9]{3})*(?:,[0-9]{2})?)\\s?(?:^|)EUR(?:$|)") || budget.matches("(?:^|)EUR(?:$|)\\s?[+-]?[0-9]{1,3}(?:[0-9](?:[,][0-9]{2})?|(?:.[0-9]{3})(?:,[0-9]{2})?)"))) {
			res = true;
		}
		return res;
	}

	private boolean moneyBudgetEn(final String budget) {
		boolean res = false;
		if (!budget.isEmpty() && (budget.matches("([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?\\s?\\€") || budget.matches("^\\€\\s?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?")
			|| budget.matches("([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?\\s?(?:^|)EUR(?:$|)") || budget.matches("(?:^|)EUR(?:$|)\\s?([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?"))) {
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

	private String tickerChecker(final String sss, final String yy, final String NNNNNN) {
		String tryone = sss + "-" + yy + "-" + NNNNNN;
		if (this.repository.InvestmentRoundTickers().contains(tryone)) {
			String last = this.getNNNNNN();
			tryone = sss + "-" + yy + "-" + last;
			this.tickerChecker(sss, yy, last);
		}

		return tryone;
	}

	private String getNNNNNN() {
		String random = String.valueOf((int) (Math.random() * 999999 + 1));
		String res = random;
		for (int i = 6; i > random.length(); i--) {
			res = "0" + res;
		}
		return res;
	}
	private boolean finalModeValidateEs(final Money moneyAmount, final String budgetActivity, final boolean finalMode) {
		boolean res = false;
		if (!budgetActivity.isEmpty() && budgetActivity != null && moneyAmount != null && budgetActivity != null && (budgetActivity.contains("€") || budgetActivity.contains("EUR")) && moneyAmount.getAmount() != null) {
			if (this.moneyBudgetEs(budgetActivity)) {
				Double budget = Double.valueOf(budgetActivity.replace("€", "").replace("EUR", "").replace(".", "").replace(",", "."));
				if (finalMode && budget.equals(moneyAmount.getAmount())) {
					res = true;
				}
			}
			if (!finalMode) {
				res = true;
			}
		}
		return res;

	}

	private boolean finalModeValidateEn(final Money moneyAmount, final String budgetActivity, final boolean finalMode) {
		boolean res = false;
		if (!budgetActivity.isEmpty() && budgetActivity != null && moneyAmount != null && budgetActivity != null && (budgetActivity.contains("€") || budgetActivity.contains("EUR")) && moneyAmount.getAmount() != null) {
			if (this.moneyBudgetEn(budgetActivity)) {
				Double budget = Double.valueOf(budgetActivity.replace("€", "").replace("EUR", "").replace(",", ""));
				if (finalMode && budget.equals(moneyAmount.getAmount())) {
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
		if (request.getLocale().getLanguage().equals("es")) {

			String amount = budgetActivity.replace("€", "").replace("EUR", "").replace(".", "").replace(",", ".");
			budget.setAmount(Double.valueOf(amount));
		}
		if (request.getLocale().getLanguage().equals("en")) {
			String amount = budgetActivity.replace("€", "").replace("EUR", "").replace(",", "");
			budget.setAmount(Double.valueOf(amount));
		}

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
