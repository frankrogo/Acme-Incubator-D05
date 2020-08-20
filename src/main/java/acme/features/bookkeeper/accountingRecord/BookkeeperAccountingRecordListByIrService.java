
package acme.features.bookkeeper.accountingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class BookkeeperAccountingRecordListByIrService implements AbstractListService<Bookkeeper, AccountingRecord> {

	@Autowired
	BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "creationMoment");
		request.getModel().setAttribute("listInvestmentRoundId", entity.getInvestmentRound().getId());
	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> accountingRecords;
		int investmentRoundId;

		investmentRoundId = request.getModel().getInteger("investmentRoundId");
		accountingRecords = this.repository.findAllByInvestmentRoundId(investmentRoundId);
		for(AccountingRecord ar: accountingRecords) {
			if(ar.isStatus()==false) {
				accountingRecords.remove(ar);
				
			}
		}

		return accountingRecords;
	}

}
