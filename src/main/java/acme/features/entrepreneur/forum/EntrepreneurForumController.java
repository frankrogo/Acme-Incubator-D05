
package acme.features.entrepreneur.forum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.forums.Forum;
import acme.entities.roles.Entrepreneur;
import acme.features.authenticated.messenger.AuthenticatedMessengerListByForumService;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepreneur/forum/")
public class EntrepreneurForumController extends AbstractController<Entrepreneur, Forum> {

	@Autowired
	private EntrepreneurForumListMineService	listMineSerivce;
	@Autowired
	private EntrepreneurForumShowService		showService;
	//	@Autowired
	//	private EntrepreneurForumDeleteService		deleteService;


	@PostConstruct
	private void initialize() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineSerivce);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		//		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		//		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
