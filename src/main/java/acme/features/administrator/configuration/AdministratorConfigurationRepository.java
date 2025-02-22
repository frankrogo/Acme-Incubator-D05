
package acme.features.administrator.configuration;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configurations.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorConfigurationRepository extends AbstractRepository {

	@Query("select c from Configuration c")
	Collection<Configuration> findConfiguration();

	@Query("select c from Configuration c where c.id = ?1")
	Configuration findOneById(int id);
	
	@Query("select s from Configuration s")
    Configuration findOneConfiguration();
}
