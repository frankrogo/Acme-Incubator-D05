
package acme.entities.toolRecords;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ToolRecord extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				title;

	@NotBlank
	private String				sector;

	@NotBlank
	private String				inventorName;

	@NotBlank
	@Column(length = 1024)
	private String				description;

	@URL
	@NotBlank
	private String				website;

	@NotBlank
	@Email
	private String				email;

	@Range(min = -5, max = 5)
	private Integer				stars;

	@NotNull
	private Boolean				isOpenSource;
}
