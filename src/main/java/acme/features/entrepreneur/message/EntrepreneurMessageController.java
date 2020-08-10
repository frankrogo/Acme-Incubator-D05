
package acme.features.entrepreneur.message;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.messages.Message;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepreneur/message/")
public class EntrepreneurMessageController extends AbstractController<Entrepreneur, Message> {

	@Autowired
	private EntrepreneurMessageListMineService	listMineService;
	@Autowired
	private EntrepreneurMessageShowService		showService;


	@PostConstruct
	private void initialize() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listMineService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
