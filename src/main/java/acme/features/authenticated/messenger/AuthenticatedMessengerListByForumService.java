
package acme.features.authenticated.messenger;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messengers.Messenger;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedMessengerListByForumService implements AbstractListService<Authenticated, Messenger> {

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
		model.setAttribute("forumId", entity.getForum().getId());
		model.setAttribute("forumName", entity.getForum().getTitle());
		model.setAttribute("authenticatedName", entity.getAuthenticated().getUserAccount().getUsername());
	}

	@Override
	public Collection<Messenger> findMany(final Request<Messenger> request) {
		assert request != null;

		Collection<Messenger> result;

		Integer forumId = request.getModel().getInteger("forumId");
		result = this.repository.findMessengersByForumId(forumId);

		return result;
	}

}
