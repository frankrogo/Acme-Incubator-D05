
package acme.features.entrepreneur.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepreneurForumShowService implements AbstractShowService<Entrepreneur, Forum> {

	@Autowired
	EntrepreneurForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		boolean result;
		int forumId;
		Forum forum;
		Entrepreneur entrepreneur;
		Principal principal;

		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneById(forumId);
		entrepreneur = forum.getInvestmentRound().getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title");
		model.setAttribute("investmentRoundTicker", entity.getInvestmentRound().getTicker());
		model.setAttribute("forumId", request.getModel().getInteger("id"));
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
