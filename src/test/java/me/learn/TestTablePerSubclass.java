package me.learn;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import me.learn.domain.TITLE;
import me.learn.domain.TRAINER_TYPE;
import me.learn.domain.tablePerSubclass.Participant;
import me.learn.domain.tablePerSubclass.Trainee;
import me.learn.domain.tablePerSubclass.Trainer;

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

public class TestTablePerSubclass {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static final Logger logger = Logger.getLogger(TestTablePerSubclass.class.getName());
	 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FileHandler fh = new FileHandler("./logs/TestTablePerSubclass.log");
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//delete all data
		Session session = sessionFactory.openSession();
		//Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Participant.class);
		List<Participant> participants = criteria.list();
		for(Participant participant: participants){
			logger.log(Level.INFO, String.format("Participant - %s", participant));
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
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).addAnnotatedClass(Participant.class).addAnnotatedClass(Trainer.class).addAnnotatedClass(Trainee.class)
					.buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCreationTablePerHierarchyData() {
		//fail("Not yet implemented");
		Participant trnr1 = createTrainer("Amandeep", "amandeep@sapient.xyz", "04-11-2008", TITLE.MGR);
		Participant trnr2 = createTrainer("Amit", "amitj@sapient.xyz", "15-06-2011", TITLE.SAL1);
		Participant trne1 = createTrainee("Priya", "priyas@sapient.xyz", "10-09-2013", TITLE.AL2);
		Participant trne2 = createTrainee("Mona", "monas@sapient.xyz", "10-09-2013", TITLE.AL2);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(trnr1);
		session.save(trnr2);
		session.save(trne1);
		session.save(trne2);
		session.flush();

		session.close();
		
		assertNotNull(trnr1.getId());
		
	}
	
	private Participant createTrainer(String name, String email, String doj, TITLE title){
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
	
	private Participant createTrainee(String name, String email, String doj, TITLE title){
		Trainee trnee = new Trainee();
		trnee.setName(name);
		trnee.setDateCreated(new Date());
		try {
			trnee.setDateOfJoining(sdf.parse(doj));
			trnee.setDateOfLeaving(sdf.parse("31-12-2099"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trnee.setEmail(email);
		trnee.setLastUpdated(new Date());
		trnee.setPhoneNumber(999999999);
		trnee.setLastUpdated(new Date());
		trnee.setTitle(title);
		trnee.setCollege("AIT Pune");
		trnee.setDegree("Engineering");
		trnee.setYearOfPassingOut(2013);
		return trnee;
	}

}
