
package acme.features.bookkeeper.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.entities.roles.Entrepreneur;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BookkeeperAccountingRecordRepository extends AbstractRepository {

	@Query("select ar from AccountingRecord ar where ar.investmentRound.id =?1 and ar.status = true")
	Collection<AccountingRecord> findAllByInvestmentRoundId(int investmentRoundId);

	@Query("select ar from AccountingRecord ar where ar.bookkeeper.id =?1")
	Collection<AccountingRecord> findManyByBookkeeperId(int entrepreneurId);

	@Query("select ar from AccountingRecord ar where ar.id = ?1")
	AccountingRecord findOneById(int id);
	
	@Query("select b from Bookkeeper b where b.id = ?1")
	Bookkeeper findBookkeeperById(int id);
	
	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findInvestmentRoundById(int investmentRoundid);

}
