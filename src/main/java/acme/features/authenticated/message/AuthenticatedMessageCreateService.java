
package acme.features.authenticated.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamChecker;
import acme.entities.configurations.Configuration;
import acme.entities.messages.Message;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessageCreateService implements AbstractCreateService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		boolean result;
		String nameOwner;
		Principal principal;
		List<String> messsengersOfTheForum = new ArrayList<>();
		principal = request.getPrincipal();
		nameOwner = principal.getUsername();

		messsengersOfTheForum = (List<String>) this.repository.findUserNamesFromMessengers(request.getModel().getInteger("forumId"));
		result = messsengersOfTheForum.contains(nameOwner);

		return result;
	}

	@Override
	public void bind(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "creationMoment", "forum", "authenticated");
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "tags", "body");
		model.setAttribute("forumId", entity.getForum().getId());

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("check", "false");
		} else {
			request.transfer(model, "check");
		}
	}

	@Override
	public Message instantiate(final Request<Message> request) {
		Message result;
		Date moment;
		result = new Message();
		moment = new Date(System.currentTimeMillis() - 1);

		result.setCreationMoment(moment);
		result.setAuthenticated(this.repository.findAuthenticatedById(request.getPrincipal().getActiveRoleId()));
		result.setForum(this.repository.findForumById(request.getModel().getInteger("forumId")));

		return result;
	}

	@Override
	public void validate(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Spam validation
		boolean spamBody, spamTitle, spamTags, isAccepted;

		if (!errors.hasErrors()) {
			Configuration configuration = this.repository.findConfiguration();
			String title = entity.getTitle();
			String body = entity.getBody();
			String tags = entity.getTags();
			spamTitle = SpamChecker.spamChecker(configuration, title);
			spamBody = SpamChecker.spamChecker(configuration, body);
			spamTags = SpamChecker.spamChecker(configuration, tags);
			errors.state(request, !spamTitle, "title", "employer.job.error.spamTitle");
			errors.state(request, !spamBody, "body", "employer.job.error.spamBody");
			errors.state(request, !spamTags, "tags", "employer.job.error.spamTags");
		}

		//Checkbox validation
		String res = request.getModel().getString("check");
		isAccepted = res.equals("true");
		errors.state(request, isAccepted, "check", "provider.request.error.must-accept");
	}

	@Override
	public void create(final Request<Message> request, final Message entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

}
