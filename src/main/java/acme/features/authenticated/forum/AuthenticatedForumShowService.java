
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messengers.Messenger;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedForumShowService implements AbstractShowService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository		repository;

	@Autowired
	AuthenticatedMessengerRepository	messengerRepository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		boolean result;
		boolean containsMessenger = false;
		int forumId;
		Messenger owner;
		Authenticated auth;
		Principal principal;
		principal = request.getPrincipal();

		Collection<Messenger> msgs;
		forumId = request.getModel().getInteger("id");
		owner = this.repository.findTheOwner(forumId);
		auth = this.messengerRepository.findAuthByAccountId(principal.getAccountId());
		msgs = this.repository.findMessengersByForumId(forumId);

		for (Messenger m : msgs) {
			if (m.getAuthenticated() == auth) {
				containsMessenger = true;
				break;
			}

		}
		result = owner.getAuthenticated().getId() == principal.getActiveRoleId() || containsMessenger;
		return result;
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");
		if (entity.getInvestmentRound() != null) {
			model.setAttribute("investmentRoundTicker", entity.getInvestmentRound().getTicker());
		}
		model.setAttribute("forumId", entity.getId());

		boolean ownerForum = false;
		Messenger owner;
		Principal principal;

		owner = this.repository.findTheOwner(entity.getId());
		principal = request.getPrincipal();
		if (owner.getAuthenticated().getId() == principal.getActiveRoleId()) {
			ownerForum = true;
		}
		model.setAttribute("ownerForum", ownerForum);
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
