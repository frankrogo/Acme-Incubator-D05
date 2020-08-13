
package acme.features.authenticated.messenger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messengers.Messenger;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessengerCreateService implements AbstractCreateService<Authenticated, Messenger> {

	@Autowired
	AuthenticatedMessengerRepository repository;


	@Override
	public boolean authorise(final Request<Messenger> request) {
		assert request != null;
		boolean result;
		int forumId;
		Messenger messenger;
		Principal principal;

		forumId = request.getModel().getInteger("forumId");
		messenger = this.repository.findTheOwner(forumId);
		principal = request.getPrincipal();
		result = messenger.getAuthenticated().getId() == principal.getActiveRoleId();
		return result;
	}

	@Override
	public void bind(final Request<Messenger> request, final Messenger entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "isOwner", "forum", "authenticated");

	}

	@Override
	public void unbind(final Request<Messenger> request, final Messenger entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model);
		Integer forumId = request.getModel().getInteger("forumId");
		model.setAttribute("forumId", forumId);
	}

	@Override
	public Messenger instantiate(final Request<Messenger> request) {
		Messenger result = new Messenger();
		result.setOwnsTheForum(false);

		Integer forumId = request.getModel().getInteger("forumId");
		result.setForum(this.repository.findForumById(forumId));
		result.setAuthenticated(this.repository.findAuthByAccountId(request.getPrincipal().getActiveRoleId()));

		return result;
	}

	@Override
	public void validate(final Request<Messenger> request, final Messenger entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isNull = false;
		boolean isEmpty = false;

		if (!errors.hasErrors("userName")) {
			String userName = request.getModel().getString("userName");
			if (userName.trim().isEmpty()) {
				isEmpty = true;
			} else {
				UserAccount uaccount = this.repository.findUserByName(userName);
				List<String> isAMessenger = (List<String>) this.repository.findInvolvedUsers(request.getModel().getInteger("forumId"));
				isNull = uaccount == null || isAMessenger.contains(uaccount.getUsername());
			}
			errors.state(request, !isNull, "*", "authenticated.messenger.error.nullName");
			errors.state(request, !isEmpty, "*", "authenticated.messenger.error.emptyName");
		}

	}

	@Override
	public void create(final Request<Messenger> request, final Messenger entity) {

		String userName = request.getModel().getString("userName");
		UserAccount uaccount = this.repository.findUserByName(userName);
		Authenticated authenticated = this.repository.findAuthByAccountId(uaccount.getId());
		entity.setAuthenticated(authenticated);

		this.repository.save(entity);

	}

}
