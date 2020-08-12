
package acme.features.entrepreneur.forum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Entrepreneur;
import acme.features.authenticated.message.AuthenticatedMessageRepository;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

public class EntrepreneurForumDeleteService implements AbstractDeleteService<Entrepreneur, Forum> {

	@Autowired
	EntrepreneurForumRepository			repository;

	@Autowired
	AuthenticatedMessengerRepository	messengerRepository;

	@Autowired
	AuthenticatedMessageRepository		messageRepository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "investmentRound");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("forumId"));
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
