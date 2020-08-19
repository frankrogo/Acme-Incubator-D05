
package acme.features.authenticated.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messengers.Messenger;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessengerShowService implements AbstractShowService<Authenticated, Messenger> {

	@Autowired
	AuthenticatedMessengerRepository repository;


	@Override
	public boolean authorise(final Request<Messenger> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Messenger> request, final Messenger entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ownsTheForum");
		Integer forumId = entity.getForum().getId();
		model.setAttribute("forumName", entity.getForum().getTitle());

		boolean ownerForum = false;
		Messenger owner;
		Principal principal;

		owner = this.repository.findTheOwner(forumId);
		principal = request.getPrincipal();

		if (owner.getAuthenticated().getId() == principal.getActiveRoleId() && !entity.getOwnsTheForum()) {
			ownerForum = true;
		}
		model.setAttribute("ownerForum", ownerForum);
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
}
