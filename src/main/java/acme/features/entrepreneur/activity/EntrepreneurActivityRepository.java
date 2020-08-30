
package acme.features.entrepreneur.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.configurations.Configuration;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.investmentRound.id = ?1")
	Collection<Activity> findManyByInvestmentRoundId(int investmentRoundId);

	@Query("select a from Activity a where a.id = ?1")
	Activity findOneById(int id);
	
	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findInvestmentRoundById(int id);

	@Query("select i from Activity a join a.investmentRound i where a.investmentRound.id = i.id and a.id = ?1")
	InvestmentRound finOneInvestmentRoundByActivityId(int id);
	
	@Query("select c from Configuration c")
	Configuration findConfiguration();
}


