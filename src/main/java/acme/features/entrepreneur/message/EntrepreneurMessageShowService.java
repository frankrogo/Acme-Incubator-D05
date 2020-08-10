
package acme.features.entrepreneur.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.Message;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepreneurMessageShowService implements AbstractShowService<Entrepreneur, Message> {

	@Autowired
	EntrepreneurMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		boolean result;
		int messageId;
		Message message;
		Entrepreneur entrepreneur;
		Principal principal;

		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneById(messageId);
		entrepreneur = message.getForum().getInvestmentRound().getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment", "tags", "body");
	}

	@Override
	public Message findOne(final Request<Message> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

}
