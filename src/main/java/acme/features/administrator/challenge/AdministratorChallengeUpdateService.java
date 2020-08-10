
package acme.features.administrator.challenge;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.challenges.Challenge;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorChallengeUpdateService implements AbstractUpdateService<Administrator, Challenge> {

	@Autowired
	AdministratorChallengeRepository repository;


	@Override
	public boolean authorise(final Request<Challenge> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Challenge> request, final Challenge entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "title", "deadline", "description", "rookieGoal", "rookieReward", "averageGoal", "averageReward", "expertGoal", "expertReward");
	}

	@Override
	public Challenge findOne(final Request<Challenge> request) {
		assert request != null;
		return this.repository.findOneById(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		//Deadline validation
		Calendar calendar;
		Date minimumDeadline;

		if (!errors.hasErrors("deadline")) {
			calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, +1);
			minimumDeadline = calendar.getTime();
			boolean future = entity.getDeadline().after(minimumDeadline);
			errors.state(request, future, "deadline", "administrator.challenge.error.future-deadline");
		}

		//Rewards validation
		Money rookieReward = entity.getRookieReward();
		Money averageReward = entity.getAverageReward();
		Money expertReward = entity.getExpertReward();
		boolean eurRookieReward, eurAverageReward, eurExpertReward, averageMoreThanRookie, expertMoreThanAverage;

		if (!errors.hasErrors("rookieReward") && averageReward != null) {
			eurRookieReward = rookieReward.getCurrency().equals("EUR") || rookieReward.getCurrency().equals("€");
			errors.state(request, eurRookieReward, "rookieReward", "administrator.challenge.error.eurRookieReward");

			averageMoreThanRookie = averageReward.getAmount() > rookieReward.getAmount();
			errors.state(request, averageMoreThanRookie, "rookieReward", "administrator.challenge.error.averageMoreThanRookie");
		}
		if (!errors.hasErrors("averageReward") && expertReward != null) {
			eurAverageReward = averageReward.getCurrency().equals("EUR") || averageReward.getCurrency().equals("€");
			errors.state(request, eurAverageReward, "averageReward", "administrator.challenge.error.eurAverageReward");

			expertMoreThanAverage = expertReward.getAmount() > averageReward.getAmount();
			errors.state(request, expertMoreThanAverage, "averageReward", "administrator.challenge.error.expertMoreThanAverage");
		}
		if (!errors.hasErrors("expertReward")) {
			eurExpertReward = expertReward.getCurrency().equals("EUR") || expertReward.getCurrency().equals("€");
			errors.state(request, eurExpertReward, "expertReward", "administrator.challenge.error.eurExpertReward");
		}

	}

	@Override
	public void update(final Request<Challenge> request, final Challenge entity) {
		assert request != null;
		assert entity != null;
		this.repository.save(entity);
	}

}
