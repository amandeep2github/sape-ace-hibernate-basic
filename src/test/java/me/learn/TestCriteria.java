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
import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Skill;
import me.learn.domain.criteria.Trainer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCriteria {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final Logger logger = Logger.getLogger(TestCriteria.class.getName());
	 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createSessionFactory();
		Trainer trnr1 = createTrainer("Amandeep", "amandeep@sapient.xyz", "04-11-2008", TITLE.MGR);
		Trainer trnr2 = createTrainer("Amit", "amitj@sapient.xyz", "15-06-2011", TITLE.SAL1);
		Skill skillJava = new Skill();
		skillJava.setName("Java");
		skillJava.setProficiency(PROFICIENCY.INTERMEDIATE);
		Skill skillOracle = new Skill();
		skillOracle.setName("Oracle");
		skillOracle.setProficiency(PROFICIENCY.INTERMEDIATE);
		Set<Skill> skills = new HashSet<Skill>();
		skills.add(skillJava);
		skills.add(skillOracle);
		trnr1.setSkills(skills);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(trnr1);
		session.save(trnr2);
		
		session.flush();

		session.close();
	}
	
	private static void createSessionFactory(){
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		.configure() // configures settings from hibernate.cfg.xml
		.build();
		try {
			sessionFactory = new MetadataSources( registry ).addAnnotatedClass(Participant.class).addAnnotatedClass(Trainer.class).addAnnotatedClass(Skill.class)
					.buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//delete all data
		Session session = sessionFactory.openSession();
		//Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Trainer.class);
		List<Trainer> trainers = criteria.list();
		for(Trainer trnr: trainers){
			logger.log(Level.INFO, String.format("Participant - %s", trnr));
			for(Skill skill:trnr.getSkills()){
				logger.log(Level.INFO, String.format("Skill - %s", skill));
				
			}
			
			//session.delete(participant);
		}
		//t.commit();
		session.close();

		if(!sessionFactory.isClosed())
			sessionFactory.close();
		
	}

	private static SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCreationTablePerHierarchyData() {
		//fail("Not yet implemented");
		assert(true);
		
		
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
	
	

}
