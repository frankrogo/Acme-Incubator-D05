
package acme.features.authenticated.bookkeeperRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bookkeeperRequests.BookkeeperRequest;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedBookeeperRequestCreateService implements AbstractCreateService<Authenticated, BookkeeperRequest> {

	@Autowired
	private AuthenticatedBookkeeperRequestRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequest> request) {
		assert request != null;
		boolean result;
		Principal principal;
		principal = request.getPrincipal();
		result = !principal.hasRole(Bookkeeper.class);
		return result;
	}

	@Override
	public void bind(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firm", "responsabilityStatement", "status");
		Principal principal = request.getPrincipal();
		int userAccountId = principal.getAccountId();
		BookkeeperRequest bookkeeper;

		bookkeeper = this.repository.findBookkeeperRequestByUserAccountId(userAccountId);

		if (bookkeeper != null) {
			if (bookkeeper.getStatus().equals("rejected")) {
				model.setAttribute("alreadyRejected", true);
			} else if (bookkeeper.getStatus().equals("accepted")) {
				model.setAttribute("alreadyAccepted", true);
			} else {
				model.setAttribute("alreadyRejected", false);
			}
			model.setAttribute("alreadyRequested", true);
		} else {
			model.setAttribute("alreadyRequested", false);
		}
		PrincipalHelper.handleUpdate();

	}

	@Override
	public BookkeeperRequest instantiate(final Request<BookkeeperRequest> request) {
		assert request != null;

		BookkeeperRequest result = new BookkeeperRequest();
		Principal principal = request.getPrincipal();
		int userAccountId;
		UserAccount userAccount;
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		result.setStatus("pending");
		result.setUserAccount(userAccount);
		return result;
	}

	@Override
	public void validate(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//		if (!errors.hasErrors("firm")) {
		//			errors.state(request, entity.getFirm().isEmpty() || entity.getFirm() == null, "firm", "authenticated.bookkeeper.request.form.error.firm");
		//		}
		//		if (!errors.hasErrors("responsabilityStatement")) {
		//			errors.state(request, entity.getResponsabilityStatement().isEmpty() || entity.getResponsabilityStatement() == null, "responsabilityStatement", "authenticated.bookkeeper.request.form.error.responsabilityStatement");
		//		}
	}

	@Override
	public void create(final Request<BookkeeperRequest> request, final BookkeeperRequest entity) {
		assert request != null;
		assert entity != null;
		entity.setStatus("pending");
		this.repository.save(entity);

	}

}
