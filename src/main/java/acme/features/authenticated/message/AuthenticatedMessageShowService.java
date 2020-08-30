
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.features.authenticated.forum.AuthenticatedForumRepository;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageShowService implements AbstractShowService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository		repository;

	@Autowired
	AuthenticatedForumRepository		forumRepository;

	@Autowired
	AuthenticatedMessengerRepository	messengerRepository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		boolean result;
		boolean containsMessenger = false;
		int messageId;
		Message message;
		int forumId;
		Messenger owner;
		Authenticated auth;
		Principal principal;

		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneById(messageId);
		owner = this.repository.findTheOwner(message.getForum().getId());
		principal = request.getPrincipal();
		forumId = message.getForum().getId();
		auth = this.messengerRepository.findAuthByAccountId(principal.getAccountId());
		Collection<Messenger> msgs;
		msgs = this.forumRepository.findMessengersByForumId(forumId);

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
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment", "tags", "body");
		model.setAttribute("forumTitle", entity.getForum().getTitle());
		model.setAttribute("userName", this.repository.findUser(entity.getId()));
	}

	@Override
	public Message findOne(final Request<Message> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

}
