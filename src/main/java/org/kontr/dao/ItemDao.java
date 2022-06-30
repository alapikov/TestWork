package org.kontr.dao;

import java.util.List;
import java.sql.Date;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.kontr.hib.Item;
import org.kontr.hib.Plane;
import org.kontr.hib.Ship;
import org.kontr.hib.Port;

public class ItemDao implements ItemDaoInterface<Item, Integer> {
	
	private Session currentSession;
    private Transaction currentTransaction;
    public ItemDao() {
    }
 
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
 
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
     
    public void closeCurrentSession() {
        currentSession.close();
    }
     
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
     
    private static SessionFactory getSessionFactory() {
    	Configuration config = new Configuration();
		config.configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }
 
    public Session getCurrentSession() {
        return currentSession;
    }
 
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
 
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
 
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
 
    public void insert(Item entity) {
        getCurrentSession().save(entity);
    }

	public List<Item> getAll() {
		Session session = getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Port> sh = cb.createQuery(Port.class);
		Root<Port> root = sh.from(Port.class);
		pr.select(root);

		Query query = session.createQuery(pr);
		List<Item> ports = query.getResultList();
		
		CriteriaQuery<Plane> pl = cb.createQuery(Plane.class);
		Root<Plane> root1 = pl.from(Plane.class);
		pl.select(root1);

		query = session.createQuery(pl);
		List<Item> planes = query.getResultList();
		
		CriteriaQuery<Ship> sh = cb.createQuery(Ship.class);
		Root<Ship> root2 = sh.from(Ship.class);
		sh.select(root2);

		query = session.createQuery(sh);
		List<Item> ships = query.getResultList();
		
		planes.addAll(ships);
		ports.addAll(planes);
		return ports;
	}

	@Override
	public Item getPlanesByShipsAndPorts(int capPort, Date date) {
		Query query = getCurrentSession().createQuery("SELECT new Plane(pl.id, pl.title, pl.author, pl.shelf)\r\n"
			+ "	FROM Plane pl JOIN Port sh ON pl.port.id = sh.id\r\n"
			+ "	RIGHT JOIN Ship sh ON pr.id = sh.port.id \r\n"
			+ "	WHERE sh.date = '" + date + "' AND pr.cap = " + capPort);
		return (Plane) query.getSingleResult();
	}

	@Override
	public Item getPort(int id) {
		return getCurrentSession().get(Port.class, id);
	}

	@Override
	public void update(Item entity) {
		getCurrentSession().update(entity);
	}

	@Override
	public void updateAll(List<Item> entity) {
		for(Item ent: entity)
			getCurrentSession().update(ent);
	}
}
