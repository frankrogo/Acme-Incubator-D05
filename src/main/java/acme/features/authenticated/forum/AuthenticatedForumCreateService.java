
package acme.features.authenticated.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messengers.Messenger;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumCreateService implements AbstractCreateService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository repository;


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

		//request.bind(entity, errors, "investmentRound");
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

	}

	@Override
	public Forum instantiate(final Request<Forum> request) {
		assert request != null;

		Forum result = new Forum();
		int authId;
		Principal principal;
		Authenticated owner;

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

		this.repository.save(entity);

		Messenger owner = new Messenger();
		owner.setOwnsTheForum(true);
		owner.setAuthenticated(this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId()));
		owner.setForum(this.repository.findOneById(entity.getId()));
		this.repository.save(owner);

	}
}
