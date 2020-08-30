
package acme.features.authenticated.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messengers.Messenger;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedMessengerDeleteService implements AbstractDeleteService<Authenticated, Messenger> {

	@Autowired
	AuthenticatedMessengerRepository repository;


	@Override
	public boolean authorise(final Request<Messenger> request) {
		assert request != null;
		boolean result;
		int forumId;
		Messenger owner;
		Messenger erased;
		Principal principal;

		erased = this.repository.findOneById(request.getModel().getInteger("id"));
		forumId = erased.getForum().getId();
		owner = this.repository.findTheOwner(forumId);
		principal = request.getPrincipal();
		result = !erased.getOwnsTheForum() && owner.getAuthenticated().getId() == principal.getActiveRoleId();
		return result;
	}

	@Override
	public void bind(final Request<Messenger> request, final Messenger entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "ownsTheForum", "forum", "authenticated");

	}

	@Override
	public void unbind(final Request<Messenger> request, final Messenger entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model);
	}

	@Override
	public Messenger findOne(final Request<Messenger> request) {
		assert request != null;

		Messenger result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

	@Override
	public void validate(final Request<Messenger> request, final Messenger entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Messenger> request, final Messenger entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}

}
