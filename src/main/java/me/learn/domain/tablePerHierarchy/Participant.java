package me.learn.domain.tablePerHierarchy;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import me.learn.domain.TITLE;

@Entity
@Table(name="participant_table_per_hierarchy")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="participant_type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("participant")
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
	@Column
	private TITLE title;
	@Column(name="date_leaving")
	private Date dateOfLeaving;
	@Column(name="ins_ts")
	private Date dateCreated;
	@Column(name="upd_ts")
	private Date lastUpdated;
	
}
