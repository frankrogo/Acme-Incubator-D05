
package acme.features.anonymous.rodriguezBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.rodriguezBulletins.rodriguezBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface RodriguezBulletinRepository extends AbstractRepository {

	@Query("select b from rodriguezBulletin b")
	Collection<rodriguezBulletin> findMany();

}
