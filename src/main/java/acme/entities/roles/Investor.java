
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

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
	private String				sector;

	@NotBlank
	private String				profile;

	//Relationships

}
