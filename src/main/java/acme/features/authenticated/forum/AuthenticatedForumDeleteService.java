
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.features.authenticated.message.AuthenticatedMessageRepository;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedForumDeleteService implements AbstractDeleteService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository		repository;
	@Autowired
	AuthenticatedMessengerRepository	messengerRepository;
	@Autowired
	AuthenticatedMessageRepository		messageRepository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;
		boolean result;
		int forumId;
		Messenger owner;
		Forum erased;
		Principal principal;

		erased = this.repository.findOneById(request.getModel().getInteger("id"));
		forumId = erased.getId();
		owner = this.repository.findTheOwner(forumId);
		principal = request.getPrincipal();
		result = owner.getOwnsTheForum() && owner.getAuthenticated().getId() == principal.getActiveRoleId();
		return result;
	}

	@Override
	public void bind(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "title", "investmentRound", "authenticated");
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model);
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;
		Forum result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		Collection<Message> messages;
		Collection<Messenger> messengers;

		messages = this.repository.findMessagesByForumId(entity.getId());
		messengers = this.repository.findMessengersByForumId(entity.getId());

		for (Message m : messages) {
			this.messageRepository.delete(m);
		}
		for (Messenger m : messengers) {
			this.messengerRepository.delete(m);
		}
		this.repository.delete(entity);
	}

}
