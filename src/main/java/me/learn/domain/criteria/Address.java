package me.learn.domain.criteria;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;





//@Entity
@Table
@Embeddable
public class Address {
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private long id;
	@Column
	private String line1;
	@Column
	private String line2;
	@Column
	private String city;
	@Column
	private String pin;
	public Address(String line1, String line2, String city, String pin) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.pin = pin;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
