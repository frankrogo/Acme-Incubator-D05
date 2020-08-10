
package acme.features.investor.forum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.forums.Forum;
import acme.entities.roles.Investor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/investor/forum/")
public class InvestorForumController extends AbstractController<Investor, Forum> {

	@Autowired
	private InvestorForumListMineService	listMineSerivce;
	@Autowired
	private InvestorForumShowService		showService;


	@PostConstruct
	private void initialize() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineSerivce);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
