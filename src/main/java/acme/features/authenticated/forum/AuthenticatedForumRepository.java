
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configurations.Configuration;
import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedForumRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findOneById(int id);

	@Query("select c from Configuration c")
	Configuration findConfiguration();
	
	@Query("select f from Forum f where f.id in(select m.forum.id from Messenger m where m.authenticated.id=?1)")
	Collection<Forum> findManyByAuthenticatedId(int authenticatedId);

	@Query("select m from Messenger m where m.ownsTheForum=true and m.forum.id = ?1")
	Messenger findTheOwner(int id);

	@Query("select au from Authenticated au where au.userAccount.id = ?1")
	Authenticated findAuthByAccountId(int authenticatedId);

	@Query("select a from Authenticated a where a.id=?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findMessagesByForumId(int id);

	@Query("select m from Messenger m where m.forum.id = ?1")
	Collection<Messenger> findMessengersByForumId(int id);
}
