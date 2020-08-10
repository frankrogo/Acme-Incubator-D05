
package acme.features.bookkeeper.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BookkeeperInvestmentRoundRepository extends AbstractRepository {

	@Query("select ir from AccountingRecord a join a.investmentRound ir where a.bookkeeper.id = ?1 and ir.finalMode = true")
	Collection<InvestmentRound> findManyByBookkeeperId(int id);

	@Query("select ir from InvestmentRound ir where not exists(select a from AccountingRecord a where a.investmentRound.id = ir.id and a.bookkeeper.id = ?1) and ir.finalMode = true")
	Collection<InvestmentRound> findManyByNotMyBookkeeperId(int id);

	@Query("select j from InvestmentRound j where j.id=?1")
	InvestmentRound findOneById(int id);

}
