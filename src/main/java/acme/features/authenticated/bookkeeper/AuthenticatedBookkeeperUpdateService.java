
package acme.features.authenticated.bookkeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedBookkeeperUpdateService implements AbstractUpdateService<Authenticated, Bookkeeper> {

	@Autowired
	AuthenticatedBookkeeperRepository repository;


	@Override
	public boolean authorise(final Request<Bookkeeper> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Bookkeeper> request, final Bookkeeper entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Bookkeeper> request, final Bookkeeper entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "firm", "responsabilityStatement");
	}

	@Override
	public Bookkeeper findOne(final Request<Bookkeeper> request) {
		assert request != null;
		Bookkeeper res = this.repository.findOneBookkeeperByUserAccountId(request.getPrincipal().getAccountId());
		return res;
	}

	@Override
	public void validate(final Request<Bookkeeper> request, final Bookkeeper entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(final Request<Bookkeeper> request, final Bookkeeper entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Bookkeeper> request, final Response<Bookkeeper> response) {
		assert request != null;
		assert response != null;
		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
