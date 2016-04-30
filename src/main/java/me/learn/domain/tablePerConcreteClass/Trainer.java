package me.learn.domain.tablePerConcreteClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import me.learn.domain.TRAINER_TYPE;

@Entity
@Table(name="trainer_table_per_concrete_class")
public class Trainer extends Participant{
	@Column(name="rates_per_hour")
	private Double ratesPerHour;
	@Column(name="trainer_type", columnDefinition="TEXT")
	@Enumerated(EnumType.STRING)
	private TRAINER_TYPE type;
	@Column(name="linked_in_url")
	private String linkedInURL;
	public Double getRatesPerHour() {
		return ratesPerHour;
	}
	public void setRatesPerHour(Double ratesPerHour) {
		this.ratesPerHour = ratesPerHour;
	}
	public TRAINER_TYPE getType() {
		return type;
	}
	public void setType(TRAINER_TYPE type) {
		this.type = type;
	}
	public String getLinkedInURL() {
		return linkedInURL;
	}
	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}
	
	
}
