
package acme.entities.forums;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Forum extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	//Properties

	@NotBlank
	private String				title;

	//Relationships

	@NotNull
	@Valid
	@OneToOne(optional = false)
	private InvestmentRound		investmentRound;

}
