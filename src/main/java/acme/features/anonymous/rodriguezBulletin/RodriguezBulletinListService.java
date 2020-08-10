
package acme.features.anonymous.rodriguezBulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rodriguezBulletins.rodriguezBulletin;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class RodriguezBulletinListService implements AbstractListService<Anonymous, rodriguezBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	RodriguezBulletinRepository repository;


	@Override
	public boolean authorise(final Request<rodriguezBulletin> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<rodriguezBulletin> request, final rodriguezBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "author", "description", "moment");
	}

	@Override
	public Collection<rodriguezBulletin> findMany(final Request<rodriguezBulletin> request) {
		assert request != null;
		Collection<rodriguezBulletin> res = this.repository.findMany();
		return res;
	}
}
