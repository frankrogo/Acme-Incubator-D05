
package acme.features.bookkeeper.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.activities.Activity;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/bookkeeper/activity/")
public class BookkeeperActivityController extends AbstractController<Bookkeeper, Activity> {

	@Autowired
	private BookkeeperActivityListByInvestmentRoundService	listByInvestmentRoundService;
	@Autowired
	private BookkeeperActivityShowService					showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_BY_IR, BasicCommand.LIST, this.listByInvestmentRoundService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
