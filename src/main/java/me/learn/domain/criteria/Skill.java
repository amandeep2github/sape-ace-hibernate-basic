package me.learn.domain.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import me.learn.domain.PROFICIENCY;

@Entity
public class Skill {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String name;
	@Column
	@Enumerated(value=EnumType.STRING)
	private PROFICIENCY proficiency;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PROFICIENCY getProficiency() {
		return proficiency;
	}
	public void setProficiency(PROFICIENCY proficiency) {
		this.proficiency = proficiency;
	}
	public String toString(){
		return String.format("Skill name - %s,  proficiency - %s", name, proficiency);
		
	}
	
}
