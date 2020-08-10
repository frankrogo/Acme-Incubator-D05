
package acme.features.entrepreneur.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurForumRepository extends AbstractRepository {

	@Query("select f from Forum f where f.investmentRound.entrepreneur.id = ?1")
	Collection<Forum> findManyByEntrepreneurId(int entrepreneurId);

	@Query("select f from Forum f where f.id = ?1")
	Forum findOneById(int id);
}
