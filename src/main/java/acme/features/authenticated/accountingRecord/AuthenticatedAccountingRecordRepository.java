
package acme.features.authenticated.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.accountingRecords.AccountingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAccountingRecordRepository extends AbstractRepository {

	@Query("select ar from AccountingRecord ar where ar.investmentRound.id =?1 and ar.status = true")
	Collection<AccountingRecord> findAllByInvestmentRoundId(int investmentRoundId);

	@Query("select ar from AccountingRecord ar where ar.id = ?1")
	AccountingRecord findOneById(int id);

}
