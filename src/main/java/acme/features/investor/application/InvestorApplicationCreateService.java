
package acme.features.investor.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class InvestorApplicationCreateService implements AbstractCreateService<Investor, Application> {

	@Autowired
	InvestorApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		InvestmentRound invround = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		return invround == null || invround.isFinalMode();
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "ticker", "statement", "moneyOffer");
		model.setAttribute("investmentRoundId", request.getModel().getInteger("investmentRoundId"));
		model.setAttribute("investmentRoundTicker", entity.getInvestmentRound().getTicker());
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result;
		result = new Application();
		Investor investor;
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		result.setCreationMoment(moment);
		result.setStatus("pending");

		investor = this.repository.findInvestorById(request.getPrincipal().getActiveRoleId());
		result.setInvestor(investor);
		result.setInvestmentRound(this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId")));

		String sss = investor.getSector().substring(0, 3).toUpperCase();
		String año = String.valueOf(result.getCreationMoment().getYear());
		String yy = año.substring(año.length() - 2);
		result.setTicker(sss + "-" + yy + "-" + this.getNNNNNN());

		return result;
	}

	private String getNNNNNN() {
		String random = String.valueOf((int) (Math.random() * 999999 + 1));
		String res = random;
		for (int i = 6; i > random.length(); i--) {
			res = "0" + res;
		}
		return res;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		String ticker = entity.getTicker().trim();
		boolean noOtherSameTicker;

		if (!errors.hasErrors("ticker")) {
			noOtherSameTicker = this.repository.findApplicationByTicker(ticker) == null;
			errors.state(request, noOtherSameTicker, "ticker", "investor.application.form.error.ticker");
		}

		if (!errors.hasErrors("moneyOffer")) {
			errors.state(request, entity.getMoneyOffer().getCurrency().equals("EUR") || entity.getMoneyOffer().getCurrency().equals("€"), "moneyOffer", "investor.application.form.error.zoneEur");
		}
	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

}
