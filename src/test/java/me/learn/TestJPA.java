package me.learn;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import me.learn.domain.PROFICIENCY;
import me.learn.domain.criteria.Participant;
import me.learn.domain.criteria.Trainer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestJPA {
	private static EntityManagerFactory emf;

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	@Ignore
	public void testTrainerExpertInJavaUsingJPACriteria(){
		EntityManager em = emf.createEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Participant> cq = cb.createQuery(Participant.class);
		Root<Participant> root = cq.from(Participant.class);
		cq.select(root);
		List<Participant> list = em.createQuery(cq).getResultList();
		
		assertEquals(2, list.size());
		assertEquals("Trainer1", list.get(0).getName());
		assertEquals("Trainer2", list.get(1).getName());
		em.close();
	}
	
	@BeforeClass
	public static void setBeforeClass() throws Exception{
		emf = TestUtilsJPA.createEntityManagerFactory();
		TestUtilsJPA.setUpBeforeClass(emf);
	}
	
	@AfterClass
	public static void setAfterClass(){
		emf.close();
	}

}
