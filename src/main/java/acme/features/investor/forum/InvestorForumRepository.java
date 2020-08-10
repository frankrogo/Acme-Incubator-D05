
package acme.features.investor.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InvestorForumRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findOneById(int id);

	@Query("select f from Forum f where exists(select i from InvestmentRound i where exists(select a from Application a where a.investor.id = ?1 and a.investmentRound.id = i.id and a.status = 'accepted') and i.id = f.investmentRound.id)")
	Collection<Forum> findManyByInvestorId(int id);

	@Query("select i from InvestmentRound i where exists(select a from Application a where a.investor.id = ?1 and a.investmentRound.id = i.id and a.status = 'accepted') and i.id = ?2")
	Collection<InvestmentRound> findInvestmentRound(int id, int iid);

}
