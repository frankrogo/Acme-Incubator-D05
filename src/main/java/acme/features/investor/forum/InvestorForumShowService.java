
package acme.features.investor.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class InvestorForumShowService implements AbstractShowService<Investor, Forum> {

	@Autowired
	InvestorForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		boolean result;
		int forumId;
		Forum forum;
		InvestmentRound investmentRound;
		Principal principal;

		principal = request.getPrincipal();

		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneById(forumId);
		investmentRound = forum.getInvestmentRound();

		result = !this.repository.findInvestmentRound(principal.getActiveRoleId(), investmentRound.getId()).isEmpty();

		return result;
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title");
		model.setAttribute("investmentRoundTicker", entity.getInvestmentRound().getTicker());
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

}
