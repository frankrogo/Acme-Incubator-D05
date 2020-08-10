
package acme.features.bookkeeper.investmentRound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class BookkeeperInvestmentRoundShowService implements AbstractShowService<Bookkeeper, InvestmentRound> {

	@Autowired
	BookkeeperInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		boolean result;
		int investmentRoundId;
		InvestmentRound investmentRound;

		investmentRoundId = request.getModel().getInteger("id");
		investmentRound = this.repository.findOneById(investmentRoundId);
		result = investmentRound.isFinalMode();

		return result;
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		int investmentRoundId = request.getModel().getInteger("id");
		request.unbind(entity, model, "ticker", "title", "creationMoment", "round", "description", "moneyAmount", "moreInfo", "finalMode");
		model.setAttribute("investmentRoundId", investmentRoundId);
		//model.setAttribute("investmentRoundId", entity.getId());
		model.setAttribute("investmentRoundTicker", entity.getTicker());
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

}
