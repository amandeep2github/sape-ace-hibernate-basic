package me.learn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import me.learn.domain.PROFICIENCY;
import me.learn.domain.TITLE;
import me.learn.domain.TRAINER_TYPE;
import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Skill;
import me.learn.domain.criteria.Trainer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestUtilsJPA {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final Logger logger = Logger.getLogger(TestCriteria.class.getName());
	
	public static void setUpBeforeClass(EntityManagerFactory emf) throws Exception {
		Trainer trnrTrainer1 = createTrainer("Trainer1", "Trainer1@sapient.xyz", "04-11-2008", TITLE.MGR);
		Trainer trnrTrainer2 = createTrainer("Trainer2", "Trainer2j@sapient.xyz", "15-06-2011", TITLE.SAL1);
		
		Set<Skill> skillsTrainer1 = new HashSet<Skill>();
		skillsTrainer1.add(createSkill("Java", PROFICIENCY.EXPERT));
		skillsTrainer1.add(createSkill("Oracle", PROFICIENCY.INTERMEDIATE));
		skillsTrainer1.add(createSkill("Spring", PROFICIENCY.INTERMEDIATE));
		skillsTrainer1.add(createSkill("perl", PROFICIENCY.INTERMEDIATE));
		trnrTrainer1.setSkills(skillsTrainer1);
		
		Set<Skill> skillsTrainer2 = new HashSet<Skill>();
		skillsTrainer2.add(createSkill("Java", PROFICIENCY.EXPERT));
		skillsTrainer2.add(createSkill("Oracle", PROFICIENCY.INTERMEDIATE));
		skillsTrainer2.add(createSkill("Spring", PROFICIENCY.EXPERT));
		skillsTrainer2.add(createSkill("perl", PROFICIENCY.BEGINNER));
		trnrTrainer2.setSkills(skillsTrainer2);
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(trnrTrainer1);
		em.persist(trnrTrainer2);
		et.commit();
		em.close();
	}

	public static Skill createSkill(String name, PROFICIENCY proficiency) {
		Skill skillJava = new Skill();
		skillJava.setName(name);
		skillJava.setProficiency(proficiency);
		return skillJava;
	}
	
	public static EntityManagerFactory createEntityManagerFactory(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-basic");
		
		return emf;

	}
	
	private static Trainer createTrainer(String name, String email, String doj, TITLE title){
		Trainer trnr = new Trainer();
		trnr.setName(name);
		trnr.setDateCreated(new Date());
		try {
			trnr.setDateOfJoining(sdf.parse(doj));
			trnr.setDateOfLeaving(sdf.parse("31-12-2099"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trnr.setEmail(email);
		trnr.setLastUpdated(new Date());
		trnr.setPhoneNumber(999999999);
		trnr.setLastUpdated(new Date());
		trnr.setLinkedInURL("www.linkedin.com/"+name.toLowerCase());
		trnr.setRatesPerHour(new Double(100.00));
		trnr.setTitle(title);
		trnr.setType(TRAINER_TYPE.INHOUSE);
		return trnr;
	}
	
	public static void tearDownAfterClass(SessionFactory sessionFactory) throws Exception {
				
	}
}
