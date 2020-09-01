
package acme.features.authenticated.accountingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedAccountingRecordShowService implements AbstractShowService<Authenticated, AccountingRecord> {

	@Autowired
	AuthenticatedAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		boolean result;
		int accountingRecordId;
		AccountingRecord accountingRecord;

		accountingRecordId = request.getModel().getInteger("id");
		accountingRecord = this.repository.findOneById(accountingRecordId);
		result = accountingRecord.isStatus();

		return result;
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "status", "creationMoment", "body");
		model.setAttribute("investmentRoundTicker", entity.getInvestmentRound().getTicker());
		String statusl = entity.isStatus() ? "draft" : "published";
		model.setAttribute("statusl", statusl);
		model.setAttribute("bookkeeper", entity.getBookkeeper().getUserAccount().getUsername());
	}

	@Override
	public AccountingRecord findOne(final Request<AccountingRecord> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

}
