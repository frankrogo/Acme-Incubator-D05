
package acme.features.authenticated.messenger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.messengers.Messenger;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/messenger/")
public class AuthenticatedMessengerController extends AbstractController<Authenticated, Messenger> {

	@Autowired
	private AuthenticatedMessengerListByForumService	listByForumService;

	@Autowired
	private AuthenticatedMessengerCreateService			createService;

	@Autowired
	private AuthenticatedMessengerDeleteService			deleteService;

	@Autowired
	private AuthenticatedMessengerShowService			showService;


	// Cosntructors

	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_BY_FORUM, BasicCommand.LIST, this.listByForumService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
