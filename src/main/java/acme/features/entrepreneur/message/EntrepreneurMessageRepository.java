
package acme.features.entrepreneur.message;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurMessageRepository extends AbstractRepository {

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findManyByForumId(int id);

	@Query("select f from Forum f where f.id=?1")
	Forum findOneForumById(int id);

	@Query("select m from Message m where m.id = ?1")
	Message findOneById(int id);

}
