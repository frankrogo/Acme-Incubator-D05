
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedMessageListByForumService implements AbstractListService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		boolean result = false;
		Collection<Messenger> messengers;
		Principal principal = request.getPrincipal();
		
		messengers = this.repository.findMessengersByForumId(request.getModel().getInteger("forumId"));
		for(Messenger m : messengers) {
			if(m.getAuthenticated().getUserAccount().getId() == principal.getAccountId()) {
				result= true;
				break;
			}
		}
		return result;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment", "body");
		model.setAttribute("forumTitle", entity.getForum().getTitle());
		model.setAttribute("userName", this.repository.findUser(entity.getId()));
	}

	@Override
	public Collection<Message> findMany(final Request<Message> request) {
		assert request != null;

		Collection<Message> result;
		int forumId;

		forumId = request.getModel().getInteger("forumId");
		result = this.repository.findAllMessagesByForumId(forumId);

		return result;
	}

}
