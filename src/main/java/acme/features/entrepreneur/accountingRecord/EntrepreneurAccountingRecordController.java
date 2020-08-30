
package acme.features.entrepreneur.accountingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepreneur/accounting-record/")
public class EntrepreneurAccountingRecordController extends AbstractController<Entrepreneur, AccountingRecord> {

	@Autowired
	private EntrepreneurAccountingRecordListByInvestmentRoundService	listByIrService;
	@Autowired
	private EntrepreneurAccountingRecordShowService						showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_BY_IR, BasicCommand.LIST, this.listByIrService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
