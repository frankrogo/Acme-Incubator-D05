
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
	private EntrepreneurMessageShowService			showService;
	@Autowired
	private EntrepreneurMessageCreateService		createService;


	@PostConstruct
	private void initialize() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
