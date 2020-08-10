
package acme.features.authenticated.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findOneById(int id);

	@Query("select i from InvestmentRound i where i.finalMode = true")
	Collection<InvestmentRound> findAllActiveInvestmentRounds();

	@Query("select count(ar) from AccountingRecord ar where ar.investmentRound.id = ?1")
	int findAccountingRecordByInvestmentRoundId(int investmentRoundId);

}
