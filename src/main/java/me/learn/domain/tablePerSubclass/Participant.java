package me.learn.domain.tablePerSubclass;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import me.learn.domain.TITLE;

@Entity
@Table(name="participant_table_per_subclass")
@Inheritance(strategy=InheritanceType.JOINED)
public class Participant {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private int phoneNumber;
	@Column(name="date_joining")
	private Date dateOfJoining;
	@Column(columnDefinition="TEXT")
	@Enumerated(EnumType.STRING)
	private TITLE title;
	@Column(name="date_leaving")
	private Date dateOfLeaving;
	@Column(name="ins_ts")
	private Date dateCreated;
	@Column(name="upd_ts")
	private Date lastUpdated;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public TITLE getTitle() {
		return title;
	}
	public void setTitle(TITLE title) {
		this.title = title;
	}
	public Date getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(Date dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public int getId() {
		return id;
	}
	
	public String toString(){
		return name;
		
	}
	
}
