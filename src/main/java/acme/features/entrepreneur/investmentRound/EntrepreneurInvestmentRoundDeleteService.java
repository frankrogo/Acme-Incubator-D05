
package acme.features.entrepreneur.investmentRound;

import java.util.Collection;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.activities.Activity;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.messengers.Messenger;
import acme.entities.roles.Entrepreneur;
import acme.features.authenticated.messenger.AuthenticatedMessengerRepository;
import acme.features.entrepreneur.accountingRecord.EntrepreneurAccountingRecordRepository;
import acme.features.entrepreneur.activity.EntrepreneurActivityRepository;
import acme.features.entrepreneur.forum.EntrepreneurForumRepository;
import acme.features.entrepreneur.message.EntrepreneurMessageRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepreneurInvestmentRoundDeleteService implements AbstractDeleteService<Entrepreneur, InvestmentRound> {

	@Autowired
	EntrepreneurInvestmentRoundRepository repository;
	@Autowired
	EntrepreneurActivityRepository activityRepository;
	@Autowired
	EntrepreneurForumRepository forumRepository;
	@Autowired
	EntrepreneurAccountingRecordRepository accountingRecordRepository;
	@Autowired
	AuthenticatedMessengerRepository messengerRepository;
	@Autowired
	EntrepreneurMessageRepository messageRepository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "ticker", "title", "creationMoment", "round", "description", "moneyAmount", "moreInfo", "finalMode");
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		InvestmentRound res = this.repository.findOneById(request.getModel().getInteger("id"));
		return res;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;
		int investmentRoundId = entity.getId();
		Collection<Activity> activities = this.activityRepository.findManyByInvestmentRoundId(investmentRoundId);
		for(Activity a: activities) {
			this.activityRepository.delete(a);
		} 
		Forum forum = this.forumRepository.findOneByInvestmentRoundId(investmentRoundId);
		
		Collection<Messenger> messengers  = this.forumRepository.findMessengersByForumId(forum.getId());
		for(Messenger m: messengers) {
			this.messengerRepository.delete(m);
		}
		
		Collection<Message> messages  = this.forumRepository.findMessagesByForumId(forum.getId());
		for(Message m: messages) {
			this.messageRepository.delete(m);
		}
		
		this.forumRepository.delete(forum);
		
		
		Collection<AccountingRecord> acountingRecords = this.accountingRecordRepository.findAllByInvestmentRoundId(investmentRoundId);
		for(AccountingRecord a: acountingRecords) {
			this.accountingRecordRepository.delete(a);
		} 
		
		this.repository.delete(entity);
	}

}
