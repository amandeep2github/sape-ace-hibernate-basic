package me.learn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.learn.domain.PROFICIENCY;
import me.learn.domain.TITLE;
import me.learn.domain.TRAINER_TYPE;
import me.learn.domain.criteria.Address;
import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Skill;
import me.learn.domain.criteria.Trainer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestUtilsHibernate {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final Logger logger = Logger.getLogger(TestCriteria.class.getName());
	
	public static void setUpBeforeClass(SessionFactory sessionFactory) throws Exception {
		Trainer trnrTrainer1 = createTrainer("Trainer1", "Trainer1@sapient.xyz", "04-11-2008", TITLE.MGR);
		Trainer trnrTrainer2 = createTrainer("Trainer2", "Trainer2j@sapient.xyz", "15-06-2011", TITLE.SAL1);
		
		Address addr1 = new Address("Steet 1","lane 3","Gurgaon","122015");
		Address addr2 = new Address("Steet 2","global square","Gurgaon","122101");
		
		Address pAddr1 = new Address("New Station","new postoffice","Rohtak","124103");
		Address pAddr2 = new Address("Near ABC School","Main street","Jaipur","156171");
		
		trnrTrainer1.setAddress(addr1);
		//trnrTrainer1.setPermanentAddress(pAddr1);
		trnrTrainer2.setAddress(addr2);
		//trnrTrainer2.setPermanentAddress(pAddr2);
		
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
		
		Session session = sessionFactory.openSession();
		Transaction trn = session.beginTransaction();
		session.save(trnrTrainer1);
		session.save(trnrTrainer2);
		trn.commit();
		//session.flush();
		session.close();
	}

	public static Skill createSkill(String name, PROFICIENCY proficiency) {
		Skill skillJava = new Skill();
		skillJava.setName(name);
		skillJava.setProficiency(proficiency);
		return skillJava;
	}
	
	public static SessionFactory createSessionFactory(){
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		.configure() // configures settings from hibernate.cfg.xml
		.build();
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new MetadataSources( registry ).addAnnotatedClass(Participant.class).addAnnotatedClass(Trainer.class).addAnnotatedClass(Skill.class)
					.addAnnotatedClass(Address.class).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		return sessionFactory;

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
		//delete all data
		Session session = sessionFactory.openSession();
		//Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Trainer.class);
		List<Trainer> trainers = criteria
//				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();//
		for(Trainer trnr: trainers){
			logger.log(Level.INFO, String.format("Participant - %s", trnr));
			//logger.info("Participant - {}", trnr);
//			for(Skill skill:trnr.getSkills()){
//				logger.log(Level.INFO, String.format("Skill - %s", skill));
//				
//			}
			
			//session.delete(participant);
		}
		//t.commit();
		session.close();

		if(!sessionFactory.isClosed())
			sessionFactory.close();
		
	}
}
