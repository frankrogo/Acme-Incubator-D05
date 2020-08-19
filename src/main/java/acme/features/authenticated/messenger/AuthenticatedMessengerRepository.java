
package acme.features.authenticated.messenger;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.entities.messengers.Messenger;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessengerRepository extends AbstractRepository {

	@Query("select m from Messenger m where m.ownsTheForum=true and m.forum.id = ?1")
	Messenger findTheOwner(int id);

	@Query("select m from Messenger m where m.id = ?1")
	Messenger findOneById(int id);

	@Query("select m from Messenger m where m.forum.id = ?1")
	Collection<Messenger> findMessengersByForumId(int id);

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select au from Authenticated au where au.id=?1")
	Authenticated findAuthById(int id);

	@Query("select ua.username from UserAccount ua where ua.id in (select au.userAccount.id from Authenticated au where au.id in (select ms.authenticated.id from Messenger ms where ms.forum.id = ?1))")
	Collection<String> findInvolvedUsers(int forumId);

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findUserByName(String userName);

	@Query("select au from Authenticated au where au.userAccount.id = ?1")
	Authenticated findAuthByAccountId(int id);

	@Query("select au from Authenticated au where au.userAccount.username = ?1")
	Authenticated findAuthByName(String userName);

}
