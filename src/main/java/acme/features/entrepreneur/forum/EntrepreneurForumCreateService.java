
package acme.features.entrepreneur.forum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Entrepreneur;
import acme.entities.roles.Investor;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurForumCreateService implements AbstractCreateService<Entrepreneur, Forum> {

	@Autowired
	EntrepreneurForumRepository			repository;

	@Autowired
	AuthenticatedMessengerRepository	messengerRepository;


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
		result = forum.getInvestmentRound().isFinalMode() && entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
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

		Collection<Forum> forums;
		forums = this.repository.findManyForumsByInvestmentRoundId(entity.getInvestmentRound().getId());
		boolean one = false;
		if (forums.size() == 1) {
			one = true;
		}
		model.setAttribute("investmentRoundid", entity.getInvestmentRound().getId());
		model.setAttribute("one", one);
		request.unbind(entity, model, "title", "investmentRound");
	}

	@Override
	public Forum instantiate(final Request<Forum> request) {
		assert request != null;

		Forum result = new Forum();
		int investmentRoundId;
		int authId;
		InvestmentRound investmentRound;
		Principal principal;
		Authenticated owner;

		investmentRoundId = request.getModel().getInteger("investmentRoundId");
		investmentRound = this.repository.findOneInvestmentRoundById(investmentRoundId);
		result.setInvestmentRound(investmentRound);

		principal = request.getPrincipal();
		authId = principal.getAccountId();
		owner = this.repository.findAuthByAccountId(authId);
		result.setAuthenticated(owner);

		return result;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		//Set the investment round
		int investmentRoundId = request.getModel().getInteger("investmentRoundId");
		InvestmentRound investmentRound = this.repository.findOneInvestmentRoundById(investmentRoundId);
		entity.setInvestmentRound(investmentRound);
		this.repository.save(entity);

		//Set messenger from the owner entrepreneur of the forum
		Messenger owner = new Messenger();
		owner.setOwnsTheForum(true);
		owner.setAuthenticated(this.messengerRepository.findAuthByAccountId(request.getPrincipal().getActiveRoleId()));
		owner.setForum(entity);
		this.messengerRepository.save(entity);

		//Set messengers from investors
		Collection<Investor> investors = this.repository.findManyInvestorsByInvestmentRoundId(investmentRoundId);

		for (Investor i : investors) {
			int investorId = i.getUserAccount().getId();
			Messenger messenger = new Messenger();
			messenger.setOwnsTheForum(true);
			messenger.setAuthenticated(this.messengerRepository.findAuthByAccountId(investorId));
			messenger.setForum(entity);
			this.messengerRepository.save(messenger);
		}

	}

}
