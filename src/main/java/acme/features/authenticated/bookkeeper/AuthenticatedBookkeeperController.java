
package acme.features.authenticated.bookkeeper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.Bookkeeper;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/bookkeeper/")
public class AuthenticatedBookkeeperController extends AbstractController<Authenticated, Bookkeeper> {

	@Autowired
	private AuthenticatedBookkeeperUpdateService updateService;


	@PostConstruct
	private void initialize() {
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}

}
