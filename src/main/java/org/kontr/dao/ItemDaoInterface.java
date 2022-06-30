package org.kontr.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public interface ItemDaoInterface<T, Id extends Serializable> {
	public void insert(T entity);
	
	public T getPort(int id);
	
	public List<T> getAll();
	
	public T getPlanesByShipsAndPorts(int capPort, Date date);
	
	public void update(T entity);
	
	public void updateAll(List<T> entity);
}
