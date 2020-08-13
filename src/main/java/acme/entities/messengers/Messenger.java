
package acme.entities.messengers;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.forums.Forum;
import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Messenger extends DomainEntity {

	// Serialisation identifier -------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------

	private Boolean				ownsTheForum;

	// Relationships ------------------------------------------

	@NotNull
	@Valid
	@ManyToOne //(optional = false)
	private Forum				forum;

	@NotNull
	@Valid
	@ManyToOne //(optional = true)
	private Authenticated		authenticated;

}
