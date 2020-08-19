
package acme.entities.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bookkeeper extends UserRole {

	private static final long	serialVersionUID	= 1L;

	//Properties

	@NotBlank
	private String				firm;

	@NotBlank
	@Column(length = 1024)
	private String				responsabilityStatement;

}
