
package acme.features.entrepreneur.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.features.investor.application.InvestorApplicationRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepreneurInvestmentRoundShowService implements AbstractShowService<Entrepreneur, InvestmentRound> {

	@Autowired
	EntrepreneurInvestmentRoundRepository repository;
	
	@Autowired
	InvestorApplicationRepository applicationRepository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		boolean result;
		int investmentRoundId;
		InvestmentRound investmentRound;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentRoundId = request.getModel().getInteger("id");
		investmentRound = this.repository.findOneById(investmentRoundId);
		entrepreneur = investmentRound.getEntrepreneur();
		principal = request.getPrincipal();
		result = investmentRound.isFinalMode() || !investmentRound.isFinalMode() && entrepreneur.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		int investmentRoundId = request.getModel().getInteger("id");
		request.unbind(entity, model, "ticker", "title", "creationMoment", "round", "description", "moneyAmount", "moreInfo", "finalMode");
		model.setAttribute("investmentRoundId", investmentRoundId);
		boolean haveApplications = haveApplications(investmentRoundId);
		model.setAttribute("haveApplications", haveApplications);
		

	}
	private boolean haveApplications(int investmentRoundId) {
		boolean res=true;
		Collection<Application> applications = this.applicationRepository.findApplicationsByInvestmentRoundId(investmentRoundId);
		if(applications.size()>0) {
			res=false;
		}
		return res;
		
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound res;
		int id;
		id = request.getModel().getInteger("id");
		res = this.repository.findOneById(id);

		return res;
	}

}
