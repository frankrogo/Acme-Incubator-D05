package acme.features.bookkeeper.accountingRecord;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.features.bookkeeper.investmentRound.BookkeeperInvestmentRoundRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;
@Service
public class BookkeeperAccountingRecordCreateService implements AbstractCreateService<Bookkeeper, AccountingRecord> {

	@Autowired
	BookkeeperAccountingRecordRepository repository;

	@Autowired
	BookkeeperInvestmentRoundRepository  investmentRoundrepository;
	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;
		return true;
	}
	@Override
	public void bind(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "investmentRound");
	}
	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model,"title", "creationMoment", "body", "status");
		model.setAttribute("investmentRoundId", entity.getInvestmentRound().getId());


		}
	@Override
	public AccountingRecord instantiate(final Request<AccountingRecord> request) {
		assert request != null;
		
		AccountingRecord result;
		result = new AccountingRecord();
		result.setCreationMoment(new Date(System.currentTimeMillis() - 1));
		
		Principal principal = request.getPrincipal();
		Bookkeeper bookkeeper = this.repository.findBookkeeperById(principal.getActiveRoleId());
		result.setBookkeeper(bookkeeper);

		int investmentRoundId = request.getModel().getInteger("investmentRoundId");
		InvestmentRound investmentRound = this.investmentRoundrepository.findOneById(investmentRoundId);
		result.setInvestmentRound(investmentRound);
		
		return result;
	}

	@Override
	public void validate(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<AccountingRecord> request, final AccountingRecord entity) {

		assert request != null;
		assert entity != null;


		this.repository.save(entity);
		


	}
}
