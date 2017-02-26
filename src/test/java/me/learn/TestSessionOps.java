package me.learn;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;

import me.learn.domain.PROFICIENCY;
import me.learn.domain.criteria.Address;
import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Skill;
import me.learn.domain.criteria.Trainer;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.PessimisticLockException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSessionOps {
	
	private static SessionFactory sessionFactory;

	@Test
	public void testSaveChanges() {
		Session session = sessionFactory.openSession();
		//session.setFlushMode(FlushMode.MANUAL);
		Transaction trn = session.beginTransaction();
		Skill skill = session.get(Skill.class, 5);
		skill.setName("Spring boot");
		session.save(skill);
		trn.commit();
		//session.flush();
		session.close();
		//session.get(Skill.class, 9);
	}
	
	
	@Test
	public void testGet() {
		Session session = sessionFactory.openSession();
		Skill skill = session.get(Skill.class, 10);
		assertNotNull(skill);
	}
	
	@Test(expected=ObjectNotFoundException.class)	
	public void testLoadOnNonExistentRecord() {
		Session session = sessionFactory.openSession();
		Skill skill = session.load(Skill.class, 11);
		assertNotNull(skill);
		assertNotNull(skill.getName());
	}
	
	
	@Test
	public void testUpdateMethod() {
		Session session1 = sessionFactory.openSession();
		Skill skill1 = session1.get(Skill.class, 9);
		session1.close();
		
		skill1.setName("Oracle 11");

		Session session2 = sessionFactory.openSession();

		session2.update(skill1);
		
		session2.flush();
		
		session2.close();
		
		//Session session3 = sessionFactory.openSession();

		//assertNotEquals("Oracle 11", session3.get(Skill.class, 9).getName());
		//session3.close();
		
	}
	
	@Test
	public void testUpdateMethod2() {
		Session session1 = sessionFactory.openSession();
		Skill skill1 = session1.get(Skill.class, 8);
		session1.close();
		
		skill1.setName("Perl 1.1");

		Session session2 = sessionFactory.openSession();
		Skill skill2 = session2.get(Skill.class, 8);
		skill2.setName("Perl 1.2");
		session2.update(skill2);
		session2.flush();
		session2.close();

		Session session3 = sessionFactory.openSession();
		session3.update(skill1);
		//session3.flush();
		session3.close();
		
		
	}
	
	@Test
	public void testUpdateOnPersistedEntityWorks() {
		Session session1 = sessionFactory.openSession();
		Criteria trnr = createCriteriaOnTrainerName("Trainer1", session1);
		Participant p = (Participant) trnr.uniqueResult();
		assertEquals("Trainer1", p.getName());
		
		session1.close();
		p.setName("Trainer11");
		

		Session session2 = sessionFactory.openSession();
		Criteria trnr1 = createCriteriaOnTrainerName("Trainer1", session2);
		session2.update(p);
		session2.flush();
		session2.close();

		
		
	}
	
	@Test
	public void testDirtyChecking(){
		Session session1 = sessionFactory.openSession();
		Criteria trnr = createCriteriaOnTrainerName("Trainer1", session1);
		Participant p = (Participant) trnr.uniqueResult();
		assertEquals("Trainer1", p.getName());
		
		assertFalse(session1.isDirty());
		
		p.setName("Trainer11");
		assertTrue(session1.isDirty());

		session1.close();
	}
	
	@Test(expected=PessimisticLockException.class)
	public void testUpdateAndMergeMethodFail() {
		Session session1 = sessionFactory.openSession();
		Skill skill1 = session1.get(Skill.class, 10);
		

		skill1.setName("Spring 1");

		Session session2 = sessionFactory.openSession();

		Skill skill2 = session2.get(Skill.class, 10);
		skill2.setName("Spring 2");
		session2.update(skill2);
		session2.flush();

		session1.update(skill1);
		session1.flush();
		session2.close();
		session1.close();
	}
	
	@Test
	public void testMultipleSaveAndSaveOrUpdate() {
		Session session1 = sessionFactory.openSession();
		
		Skill skill1 = TestUtilsHibernate.createSkill("aws", PROFICIENCY.BEGINNER);
		session1.save(skill1);
		session1.flush();
		session1.close();

		Session session3 = sessionFactory.openSession();
		session3.save(skill1);
		session3.flush();
		session3.close();
		
		skill1.setName("amazon services");
		
		Session session4 = sessionFactory.openSession();
		session4.saveOrUpdate(skill1);
		session4.flush();
		session4.close();
		
		
	}

	@Test
	public void testMergeMethodWithDifferentSessions() {
		Session session1 = sessionFactory.openSession();
		
		Skill skill1 = TestUtilsHibernate.createSkill("jQuery", PROFICIENCY.BEGINNER);
		session1.save(skill1);
		session1.flush();
		session1.close();

		skill1.setName("jQuery 2");

		Session session3 = sessionFactory.openSession();
		Skill skill4 = (Skill) session3.merge(skill1);
		session3.flush();
		session3.close();
		
	}
	
	@Test
	public void testMergeMethodAfterDeleteOnDifferentSessions() {
		Session session1 = sessionFactory.openSession();
		
		Skill skill1 = TestUtilsHibernate.createSkill("Angular", PROFICIENCY.BEGINNER);
		session1.save(skill1);
		session1.flush();
		session1.close();

		skill1.setName("Angular 2");

		Session session2 = sessionFactory.openSession();
		Criteria criteria = createCriteriaOnSkillName("Angular", session2);
		Skill skill2 = (Skill) criteria.uniqueResult();
		session2.delete(skill2);
		session2.flush();
		session2.close();

		Session session3 = sessionFactory.openSession();
		Skill skill4 = (Skill) session3.merge(skill1);
		session3.flush();
		session3.close();
		
		
		
	}
	
	@Test
	public void testOpenVsCurrentSession(){
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactory.getCurrentSession();
		assertEquals(session1, session2);
	}
	
	@Test
	public void testImmutability(){
		Session session1 = sessionFactory.openSession();
		Transaction trn = session1.beginTransaction();
		Criteria criteria = createCriteriaOnTrainerName("Trainer1", session1);
		Participant participant = (Participant) criteria.uniqueResult();
		participant.setEmail("Trainer1@sapient.com");
		session1.update(participant);
		trn.commit();
		//session1.flush();
		session1.close();
	}
	
	@Test
	public void testAddressSave(){
		Session session = sessionFactory.openSession();
		//Transaction trn = session.beginTransaction();
		//Address addr = new Address("Steet 1","lane 3","Gurgaon","122015");
		//session.save(addr);
		//trn.commit();
		//session1.flush();
		session.close();
		
	}


	private Criteria createCriteriaOnSkillName(String skillName,
			Session session2) {
		Criteria criteria = session2.createCriteria(Skill.class);
		criteria.add(Restrictions.eq("name", skillName));
		return criteria;
	}
	
	private Criteria createCriteriaOnTrainerName(String trainerName,
			Session session2) {
		Criteria criteria = session2.createCriteria(Participant.class);
		criteria.add(Restrictions.eq("name", trainerName));
		return criteria;
	}

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sessionFactory = TestUtilsHibernate.createSessionFactory();
		TestUtilsHibernate.setUpBeforeClass(sessionFactory);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestUtilsHibernate.tearDownAfterClass(sessionFactory);
		
	}

}
