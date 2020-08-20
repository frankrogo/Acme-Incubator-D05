
package acme.features.administrator.bookkeeperRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bookkeeperRequests.BookkeeperRequest;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorBookkeeperRequestShowService implements AbstractShowService<Administrator, BookkeeperRequest> {

	@Autowired
	private AdministratorBookkeeperRequestRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequest> request) {
		assert request != null;

		boolean result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;
		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);
		result = userAccount.hasRole(Administrator.class);
		return result;
	}

	@Override
	public void unbind(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "firm", "responsabilityStatement", "status");
		model.setAttribute("userName", entity.getUserAccount().getUsername());
	}

	@Override
	public BookkeeperRequest findOne(final Request<BookkeeperRequest> request) {
		assert request != null;
		int id;
		BookkeeperRequest result;
		id = request.getModel().getInteger("id");
		result = this.repository.findBookkeeperRequestById(id);
		return result;
	}

}
