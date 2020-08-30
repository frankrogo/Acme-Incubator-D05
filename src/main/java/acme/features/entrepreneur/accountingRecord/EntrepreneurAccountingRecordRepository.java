
package acme.features.entrepreneur.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurAccountingRecordRepository extends AbstractRepository {

	@Query("select ar from AccountingRecord ar where ar.investmentRound.id =?1 and ar.status = true")
	Collection<AccountingRecord> findAllByInvestmentRoundId(int investmentRoundId);

	@Query("select ar from AccountingRecord ar where ar.id = ?1")
	AccountingRecord findOneById(int id);
	
	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findInvestmentRoundById(int id);

}
