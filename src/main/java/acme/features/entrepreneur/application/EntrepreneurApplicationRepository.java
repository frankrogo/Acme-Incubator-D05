
package acme.features.entrepreneur.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.configurations.Configuration;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id = ?1")
	Application findOneById(int id);

	@Query("select a from Application a where a.investmentRound.entrepreneur.id =?1")
	Collection<Application> findManyByEntrepreneurId(int entrepreneurId);

	@Query("select c from Configuration c")
	Configuration findConfiguration();
	
	@Query("select a from Authenticated a where  a.userAccount.id = ?1")
	Authenticated findOneAuthenticatedByUserAccountId(int id);

}
