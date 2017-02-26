package me.learn.domain.criteria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import me.learn.domain.PROFICIENCY;

@Entity
@Immutable
public class Skill {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String name;
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
	
	@Override
	public int hashCode(){
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Skill))
			return false;
		
		return name.equals(((Skill)obj).getName());
		
	}
	
}
