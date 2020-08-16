
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Investor extends UserRole {

	private static final long	serialVersionUID	= 1L;

	//Properties

	@NotBlank
	private String				firm;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{3}-[a-zA-Z0-9]", message = "{acme.validation.investor.sector.pattern}")
	private String				sector;

	@NotBlank
	private String				profile;

	//Relationships

}
