
package acme.features.bookkeeper.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BookkeeperActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.investmentRound.id = ?1")
	Collection<Activity> findManyByInvestmentRoundId(int investmentRoundId);

	@Query("select a from Activity a where a.id = ?1")
	Activity findOneById(int id);
}
