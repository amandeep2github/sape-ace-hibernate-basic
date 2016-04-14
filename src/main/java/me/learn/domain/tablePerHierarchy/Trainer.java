package me.learn.domain.tablePerHierarchy;

import javax.persistence.Column;
import javax.persistence.Entity;

import me.learn.domain.TRAINER_TYPE;

@Entity
public class Trainer extends Participant{
	@Column(name="rates_per_hour")
	private Double ratesPerHour;
	@Column(name="trainer_type")
	private TRAINER_TYPE type;
	@Column(name="linked_in_url")
	private String linkedInURL;
	
}
