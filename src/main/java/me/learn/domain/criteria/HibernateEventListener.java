package me.learn.domain.criteria;
import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.type.Type;

public class HibernateEventListener implements LoadEventListener {
    

	@Override
	public void onLoad(LoadEvent arg0, LoadType arg1) throws HibernateException {
		System.out.println(arg0.getEntityClassName());
		
	}
}