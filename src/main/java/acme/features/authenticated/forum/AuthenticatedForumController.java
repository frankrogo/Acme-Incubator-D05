
package acme.features.authenticated.forum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.forums.Forum;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/entrepreneur/forum/")
public class AuthenticatedForumController extends AbstractController<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumListMineService	listMineSerivce;
	@Autowired
	private AuthenticatedForumShowService		showService;
	@Autowired
	private AuthenticatedForumCreateService		createService;


	@PostConstruct
	private void initialize() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineSerivce);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
