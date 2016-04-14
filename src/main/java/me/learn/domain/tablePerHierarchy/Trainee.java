package me.learn.domain.tablePerHierarchy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("trinee")
public class Trainee extends Participant{
	@Column
	private String college;
	@Column(name="year_passing_out")
	private int yearOfPassingOut;
	@Column
	private String degree;
}
