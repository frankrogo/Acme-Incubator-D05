
package acme.features.entrepreneur.message;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EntrepreneurMessageListByForumService implements AbstractListService<Entrepreneur, Message> {

	@Autowired
	EntrepreneurMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment");
		model.setAttribute("userName", this.repository.findUser(entity.getId()));
	}

	@Override
	public Collection<Message> findMany(final Request<Message> request) {
		assert request != null;
		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));
		return this.repository.findManyByForumId(forum.getId());
	}

}
