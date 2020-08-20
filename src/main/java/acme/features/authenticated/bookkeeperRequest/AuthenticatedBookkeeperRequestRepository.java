
package acme.features.authenticated.bookkeeperRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bookkeeperRequests.BookkeeperRequest;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBookkeeperRequestRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findUserAccountById(int id);

	@Query("select ar from BookkeeperRequest ar where ar.userAccount.id = ?1")
	BookkeeperRequest findBookkeeperRequestByUserAccountId(int id);

}
