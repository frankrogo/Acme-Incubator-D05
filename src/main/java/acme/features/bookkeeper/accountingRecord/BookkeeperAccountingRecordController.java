
package acme.features.bookkeeper.accountingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/bookkeeper/accounting-record/")
public class BookkeeperAccountingRecordController extends AbstractController<Bookkeeper, AccountingRecord> {

	@Autowired
	private BookkeeperAccountingRecordListMineService	listMineService;
	@Autowired
	private BookkeeperAccountingRecordListByIrService	listByIrService;
	@Autowired
	private BookkeeperAccountingRecordShowService		showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addCustomCommand(CustomCommand.LIST_BY_IR, BasicCommand.LIST, this.listByIrService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
