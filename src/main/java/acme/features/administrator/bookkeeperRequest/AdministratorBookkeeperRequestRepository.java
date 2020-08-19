
package acme.features.administrator.bookkeeperRequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bookkeeperRequests.BookkeeperRequest;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBookkeeperRequestRepository extends AbstractRepository {

	@Query("select ar from BookkeeperRequest ar where ar.id = ?1")
	BookkeeperRequest findBookkeeperRequestById(int id);

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findUserAccountById(int id);

	@Query("select ar from BookkeeperRequest ar")
	Collection<BookkeeperRequest> findAllBookkeeperRequests();

}
