
package acme.features.authenticated.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messengers.Messenger;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedForumShowService implements AbstractShowService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		boolean result;
		int forumId;
		Messenger owner;
		Principal principal;
		forumId = request.getModel().getInteger("id");
		owner = this.repository.findTheOwner(forumId);
		principal = request.getPrincipal();

		result = owner.getAuthenticated().getId() == principal.getActiveRoleId();
		return result;
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");
		model.setAttribute("forumId", entity.getId());

		boolean ownerForum = false;
		Messenger owner;
		Principal principal;

		owner = this.repository.findTheOwner(entity.getId());
		principal = request.getPrincipal();
		if (owner.getAuthenticated().getId() == principal.getActiveRoleId()) {
			ownerForum = true;
		}
		model.setAttribute("ownerForum", ownerForum);
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

}
