package me.learn;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Skill;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestHQL {

	private static SessionFactory sessionFactory;

	@Test
	public void testRetrievalOfSkills() {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("select s from Skill s");
		List<Skill> skills = q.getResultList();
		assertEquals(8, skills.size());
	}

	@Test
	public void testRetrievalOfSkillsUsingNamedQuery() {
		Session session = sessionFactory.openSession();
		TypedQuery<Participant> q = session.createNamedQuery("findAllParticipant", Participant.class);
		List<Participant> participants = q.getResultList();
		assertEquals(2, participants.size());
	}
	
	@Test
	public void testRetrievalOfSkillsUsingSQL() {
		Session session = sessionFactory.openSession();
		Query query = session.createNativeQuery("select * from skill");
		List<Skill> skills = query.getResultList();
		assertEquals(8, skills.size());
		
	}
	
	@Test
	public void testRetrievalOfSkillsUsingSQLWithEntityMapping() {
		Session session = sessionFactory.openSession();
		SQLQuery<Skill> query = session.createSQLQuery("select * from skill");
		List<Skill> skills = query.getResultList();
		assertEquals(8, skills.size());
		assertEquals(Skill.class, skills.get(0).getClass());
		
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
