package me.learn;

import static org.junit.Assert.assertEquals;

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
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestCriteria {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final Logger logger = Logger.getLogger(TestCriteria.class.getName());

	@Test
	public void testParticularTrainerIsFetched(){
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Trainer.class);
		List<Trainer> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("name", "Amit")).list();
		assertEquals(1, list.size());
		assertEquals("Amit", list.get(0).getName());
	}
	
	@Test
	public void testTrainerExpertInJava(){
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Trainer.class);
		List<Trainer> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).
				createCriteria("skills").add(
						Restrictions.and(
							Restrictions.eq("name", "Java"),
							Restrictions.eq("proficiency", PROFICIENCY.EXPERT)
						)).addOrder(Property.forName("name").asc())
						.list();
		assertEquals(2, list.size());
		assertEquals("Amandeep", list.get(0).getName());
		assertEquals("Amit", list.get(1).getName());
	}
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createSessionFactory();
		Trainer trnrAmandeep = createTrainer("Amandeep", "amandeep@sapient.xyz", "04-11-2008", TITLE.MGR);
		Trainer trnrAmit = createTrainer("Amit", "amitj@sapient.xyz", "15-06-2011", TITLE.SAL1);
		
		Set<Skill> skillsAmandeep = new HashSet<Skill>();
		skillsAmandeep.add(createSkill("Java", PROFICIENCY.EXPERT));
		skillsAmandeep.add(createSkill("Oracle", PROFICIENCY.INTERMEDIATE));
		skillsAmandeep.add(createSkill("Spring", PROFICIENCY.INTERMEDIATE));
		skillsAmandeep.add(createSkill("perl", PROFICIENCY.INTERMEDIATE));
		trnrAmandeep.setSkills(skillsAmandeep);
		
		Set<Skill> skillsAmit = new HashSet<Skill>();
		skillsAmit.add(createSkill("Java", PROFICIENCY.EXPERT));
		skillsAmit.add(createSkill("Oracle", PROFICIENCY.INTERMEDIATE));
		skillsAmit.add(createSkill("Spring", PROFICIENCY.EXPERT));
		skillsAmit.add(createSkill("perl", PROFICIENCY.BEGINNER));
		trnrAmit.setSkills(skillsAmit);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(trnrAmandeep);
		session.save(trnrAmit);
		
		session.flush();
		session.close();
	}

	private static Skill createSkill(String name, PROFICIENCY proficiency) {
		Skill skillJava = new Skill();
		skillJava.setName(name);
		skillJava.setProficiency(proficiency);
		return skillJava;
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
		List<Trainer> trainers = criteria.list();//
		for(Trainer trnr: trainers){
			logger.log(Level.INFO, String.format("Participant - %s", trnr));
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
