package me.learn.domain.tablePerHierarchy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("trainee")
public class Trainee extends Participant{
	@Column
	private String college;
	@Column(name="year_passing_out")
	private int yearOfPassingOut;
	@Column
	private String degree;
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public int getYearOfPassingOut() {
		return yearOfPassingOut;
	}
	public void setYearOfPassingOut(int yearOfPassingOut) {
		this.yearOfPassingOut = yearOfPassingOut;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	
}
