
package acme.features.entrepreneur.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Investor;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurForumRepository extends AbstractRepository {

	@Query("select f from Forum f where f.investmentRound.entrepreneur.id = ?1")
	Collection<Forum> findManyByEntrepreneurId(int entrepreneurId);

	@Query("select f from Forum f where f.authenticated.id = ?1")
	Collection<Forum> findManyByAuthenticatedId(int entrepreneurId);
	
	@Query("select f from Forum f where f.id = ?1")
	Forum findOneById(int id);

	@Query("select f from Forum f where f.investmentRound.id = ?1")
	Collection<Forum> findManyForumsByInvestmentRoundId(int investmentRoundId);

	@Query("select ir from InvestmentRound ir where ir.id = ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select au from Authenticated au where au.userAccount.id = ?1")
	Authenticated findAuthByAccountId(int authenticatedId);

	@Query("select a.investor from Application a where a.investmentRound.id = ?1 and a.status ='accepted'")
	Collection<Investor> findManyInvestorsByInvestmentRoundId(int investmentRoundId);

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findMessagesByForumId(int id);

	@Query("select m from Messenger m where m.forum.id = ?1")
	Collection<Messenger> findMessengersByForumId(int id);
}
