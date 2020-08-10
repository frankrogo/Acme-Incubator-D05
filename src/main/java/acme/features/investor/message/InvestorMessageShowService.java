
package acme.features.investor.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.roles.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class InvestorMessageShowService implements AbstractShowService<Investor, Message> {

	@Autowired
	InvestorMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		int messageId;
		Message message;
		InvestmentRound investmentRound;
		Principal principal;

		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneById(messageId);
		investmentRound = message.getForum().getInvestmentRound();
		principal = request.getPrincipal();

		return !this.repository.findInvestmentRound(principal.getActiveRoleId(), investmentRound.getId()).isEmpty();
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
