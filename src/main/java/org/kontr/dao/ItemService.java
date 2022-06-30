package org.kontr.dao;

import java.sql.Date;
import java.util.List;

import org.kontr.hib.Item;

public class ItemService {
	private static ItemDao itemDao;
	 
    public ItemService() {
        itemDao = new ItemDao();
    }
 
    public void insert(Item entity) {
        itemDao.openCurrentSessionwithTransaction();
        itemDao.insert(entity);
        itemDao.closeCurrentSessionwithTransaction();
    }
    
    public void update(Item entity) {
        itemDao.openCurrentSessionwithTransaction();
        itemDao.update(entity);
        itemDao.closeCurrentSessionwithTransaction();
    }
    
    public void updateAll(List<Item> entity) {
        itemDao.openCurrentSessionwithTransaction();
        itemDao.updateAll(entity);
        itemDao.closeCurrentSessionwithTransaction();
    }
    
    public Item getPort(int id) {
    	itemDao.openCurrentSessionwithTransaction();
    	Item item = itemDao.getPort(id);
    	itemDao.closeCurrentSessionwithTransaction();
    	return item;
    }
    
    public List<Item> getAll() {
    	itemDao.openCurrentSessionwithTransaction();
    	List<Item> lst = itemDao.getAll();
    	itemDao.closeCurrentSessionwithTransaction();
    	return lst;
    }
    
    public Item getPlanesByShipsAndPorts(int capPort, Date date) {
    	itemDao.openCurrentSessionwithTransaction();
    	Item item = itemDao.getPlanesByShipsAndPorts(capPort, date);
    	itemDao.closeCurrentSessionwithTransaction();
    	return item;
    }
 
    public ItemDao itemDao() {
        return itemDao;
    }
}
