
package acme.features.anonymous.pereaBulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pereaBulletins.pereaBulletin;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class PereaBulletinListService implements AbstractListService<Anonymous, pereaBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	PereaBulletinRepository repository;


	@Override
	public boolean authorise(final Request<pereaBulletin> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<pereaBulletin> request, final pereaBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "body", "moment");
	}

	@Override
	public Collection<pereaBulletin> findMany(final Request<pereaBulletin> request) {
		assert request != null;
		Collection<pereaBulletin> res = this.repository.findMany();
		return res;
	}
}
