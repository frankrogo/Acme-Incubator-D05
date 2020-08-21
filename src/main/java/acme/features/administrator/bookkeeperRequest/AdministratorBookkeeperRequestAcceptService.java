
package acme.features.administrator.bookkeeperRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bookkeeperRequests.BookkeeperRequest;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorBookkeeperRequestAcceptService implements AbstractUpdateService<Administrator, BookkeeperRequest> {

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
		request.unbind(entity, model, "firm", "responsabilityStatement");
		model.setAttribute("userName", entity.getUserAccount().getUsername());
	}

	@Override
	public BookkeeperRequest findOne(final Request<BookkeeperRequest> request) {
		assert request != null;
		int id;
		BookkeeperRequest res;
		id = request.getModel().getInteger("id");
		res = this.repository.findBookkeeperRequestById(id);
		return res;
	}

	@Override
	public void validate(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<BookkeeperRequest> request, final BookkeeperRequest entity) {
		assert request != null;
		assert entity != null;

		entity.setStatus("accepted");
		this.repository.save(entity);
		Bookkeeper bookkeeper = new Bookkeeper();
		bookkeeper.setFirm(entity.getFirm());
		bookkeeper.setResponsabilityStatement(entity.getResponsabilityStatement());
		bookkeeper.setUserAccount(entity.getUserAccount());
		this.repository.save(bookkeeper);

	}

}