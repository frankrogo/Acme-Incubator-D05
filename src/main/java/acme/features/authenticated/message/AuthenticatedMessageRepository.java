
package acme.features.authenticated.message;

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
public interface AuthenticatedMessageRepository extends AbstractRepository {

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findAllMessagesByForumId(int id);

	@Query("select f from Forum f where f.id=?1")
	Forum findForumById(int id);

	@Query("select m from Message m where m.id = ?1")
	Message findOneById(int id);

	@Query("select m from Messenger m where m.ownsTheForum=true and m.forum.id = ?1")
	Messenger findTheOwner(int id);

	@Query("select ua.username from UserAccount ua where ua.id in (select au.userAccount.id from Authenticated au where au.id in (select ms.authenticated.id from Message ms where ms.id = ?1))")
	String findUser(int messageId);

	@Query("select ua.username from UserAccount ua where ua.id in (select au.userAccount.id from Authenticated au where au.id in (select m.authenticated.id from Messenger m where m.forum.id = ?1))")
	Collection<String> findUserNamesFromMessengers(int forumId);

	@Query("select a from Authenticated a where a.id=?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select c from Configuration c")
	Configuration findConfiguration();
	
	@Query("select m from Messenger m where m.forum.id = ?1")
	Collection<Messenger> findMessengersByForumId(int id);
}
