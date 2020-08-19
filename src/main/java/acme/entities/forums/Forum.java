
package acme.entities.forums;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.Authenticated;
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

	//@NotNull
	@Valid
	@OneToOne(optional = true) //No todos los foros están relacionados con un inv.round.
	//Un inv.round sí o sí tiene un forum.
	//Un forum no tiene por qué tener sí o sí un inv.round asociado.
	private InvestmentRound		investmentRound;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated		authenticated;

}
