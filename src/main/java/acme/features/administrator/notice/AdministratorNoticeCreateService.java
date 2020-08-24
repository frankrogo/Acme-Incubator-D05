
package acme.features.administrator.notice;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notices.Notice;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorNoticeCreateService implements AbstractCreateService<Administrator, Notice> {

	@Autowired
	AdministratorNoticeRepository repository;


	@Override
	public boolean authorise(final Request<Notice> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "creationMoment");
	}

	@Override
	public void unbind(final Request<Notice> request, final Notice entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "headerImage", "title", "deadline", "body", "links");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("checked", "false");
		} else {
			request.transfer(model, "checked");
		}
	}

	@Override
	public Notice instantiate(final Request<Notice> request) {
		assert request != null;

		Notice res = new Notice();
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		res.setCreationMoment(moment);
		return res;
	}

	@Override
	public void validate(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Deadline validation
		Calendar calendar;
		Date minimumDeadline;

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			minimumDeadline = calendar.getTime();
			boolean future = entity.getDeadline().after(minimumDeadline);
			errors.state(request, future, "deadline", "administrator.notice.error.future-deadline");
		}
		//Checked related notices
		String links =entity.getLinks() + ",";
		boolean comma = this.checkComma(links);
		boolean linksChecked = this.checkLinks(links);

		if (!entity.getLinks().isEmpty() && comma == false && linksChecked == true) {
			errors.state(request, comma, "links", "administrator.notice.error.comma");
		} else if (!entity.getLinks().isEmpty() && linksChecked == false && comma == true) {
			errors.state(request, linksChecked, "links", "administrator.notice.error.links");
		} else if (!entity.getLinks().isEmpty() && comma == false && linksChecked == false) {
			errors.state(request, comma, "links", "administrator.notice.error.comma");
			errors.state(request, linksChecked, "links", "administrator.notice.error.links");
		}

		//Checkbox validation
		boolean isChecked = request.getModel().getBoolean("checked");
		errors.state(request, isChecked, "checked", "administrator.notice.error.must-check");
	}

	private boolean checkComma(final String links) {
		boolean res = true;
		if (!links.contains(",")) {
			res = false;
		}
		return res;
	}

	private boolean checkLinks(final String links) {
		boolean res = true;
		String[] morelinks = links.split(",");
		for (String s : morelinks) {
			res = s.matches("^http[s]?:\\/\\/[\\w]{0,}[.]?[\\wñç]+[.]\\w+\\/?[\\w\\W\\D]+|http[s]?:\\/\\/[\\w]{0,}[.]?[\\wñç]+[.]\\w+$");
			if (!res) {
				break;
			}
		}
		return res;
	}

	@Override
	public void create(final Request<Notice> request, final Notice entity) {

		assert request != null;
		assert entity != null;

		Date now = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(now);
		this.repository.save(entity);
	}

}
