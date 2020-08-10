
package acme.features.anonymous.pereaBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.pereaBulletins.pereaBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface PereaBulletinRepository extends AbstractRepository {

	@Query("select b from pereaBulletin b")
	Collection<pereaBulletin> findMany();

}
