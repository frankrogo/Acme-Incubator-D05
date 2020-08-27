
package acme.features.entrepreneur.accountingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EntrepreneurAccountingRecordListByInvestmentRoundService implements AbstractListService<Entrepreneur, AccountingRecord> {

	@Autowired
	EntrepreneurAccountingRecordRepository repository;


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
	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> res;
		int investmentRoundId;

		investmentRoundId = request.getModel().getInteger("investmentRoundId");
		res = this.repository.findAllByInvestmentRoundId(investmentRoundId);

		return res;
	}

}
