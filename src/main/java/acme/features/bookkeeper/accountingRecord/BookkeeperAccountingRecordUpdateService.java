package acme.features.bookkeeper.accountingRecord;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;
@Service
public class BookkeeperAccountingRecordUpdateService implements AbstractUpdateService<Bookkeeper, AccountingRecord>{
	@Autowired
	BookkeeperAccountingRecordRepository repository;

	@Override
	public boolean authorise(Request<AccountingRecord> request) {
		assert request != null;
		boolean result;
		int investmentRoundId;
		AccountingRecord accountingRecord;
		InvestmentRound investmentRound;

		investmentRoundId = request.getModel().getInteger("id");
		accountingRecord = this.repository.findOneById(investmentRoundId);
		investmentRound = accountingRecord.getInvestmentRound();
		result = accountingRecord.getInvestmentRound().getId() == investmentRound.getId();

		return result;
	}

	@Override
	public void bind(Request<AccountingRecord> request, AccountingRecord entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "investmentRound");
		
	}

	@Override
	public void unbind(Request<AccountingRecord> request, AccountingRecord entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model,"title", "creationMoment", "body", "status","investmentRoundId");
		
	}

	@Override
	public AccountingRecord findOne(Request<AccountingRecord> request) {
		assert request != null;
		AccountingRecord res = this.repository.findOneById(request.getModel().getInteger("id"));
		return res;
	}

	@Override
	public void validate(Request<AccountingRecord> request, AccountingRecord entity, Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void update(Request<AccountingRecord> request, AccountingRecord entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
		
	}

}
