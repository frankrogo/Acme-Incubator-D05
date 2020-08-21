
package acme.features.entrepreneur.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.activities.Activity;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepreneur/activity/")
public class EntrepreneurActivityController extends AbstractController<Entrepreneur, Activity> {

	@Autowired
	private EntrepreneurActivityListByInvestmentRoundService	listByInvestmentRoundService;
	@Autowired
	private EntrepreneurActivityShowService						showService;
	@Autowired
	private EntrepreneurActivityCreateService					createService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_BY_IR, BasicCommand.LIST, this.listByInvestmentRoundService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
