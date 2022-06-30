package org.kontr;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.kontr.dao.ItemService;
import org.kontr.hib.Plane;
import org.kontr.hib.Item;
import org.kontr.hib.Ship;
import org.kontr.hib.Port;

public class Main {
	
	public static Date convertToDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	 public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("Имя CSV файла не задано. Нужно задать имя файла");
	        return;
	    }

	    String file = args[0];
	    
	    ArrayList<Item> records = getDataFromCSV(file);
	    
	    System.out.println("Получено объектов из csv файла: " + records.size());
	    
	    for(Item i: records) {
	    	System.out.println(i);
	    }
	    
	    System.out.println("База данных в процессе заполнения...");
	    
	    ItemService itemService = new ItemService();
	    
	    fillBase(records, itemService);
	    
	    System.out.println("Получение объектов из базы данных...");
	    
	    ArrayList<Item> records1 = (ArrayList<Item>) itemService.getAll();
	    
	    System.out.println("Получено объектов из базы данных: " + records1.size());

	    Port pl = null;
	    
	    ArrayList<Ship> ship = new ArrayList<Ship>();
	    
	    for(Item i: records1) {
	    	if(i instanceof Port && pl == null)
	    		pl = (Port)i;
	    	if(i instanceof Ship)
	    		ship.add((Ship)i);
	    	System.out.println(i);
	    }
	    
	    System.out.println("Изменение данных объекта");
	    System.out.println("Начальное состояние");
	    System.out.println(pl);
	    pl.setCode("456");
	    itemService.update(pl);
	    System.out.println("После изменения");
	    Port pl2 = (Port) itemService.getPort(pl.getId());
	    System.out.println(pl2);
	    
	    ArrayList<Item> rds = new ArrayList<>(); 
	    
	    System.out.println("Изменение группы объектов");
	    System.out.println("Начальное состояние");
	    for(Ship i: ship) {
	    	System.out.println(i);
	    	i.setTitle("Arvia");
	    	rds.add(i);
	    }
	    
	    itemService.updateAll(rds);
	    
	    System.out.println("После изменения");
	    ArrayList<Item> rds1 = (ArrayList<Item>) itemService.getAll();
	    for(Item i: rds1) {
	    	if(i instanceof Ship)
	    		System.out.println(i);
	    }
	    
	    System.out.println("Получение книги через сложный запрос");
	    Item plane = itemService.getPlaneByMagAndPort(80, convertToDate(LocalDate.of(2022, 10, 21)));
	    System.out.println(plane);
	    
	    System.out.println("Завершено");
	 }

	private static void fillBase(ArrayList<Item> records, ItemService itemService) {
		ArrayList<Port> ports = new ArrayList<>();
	    ArrayList<Plane> planes = new ArrayList<>();
	    ArrayList<Ship> ships = new ArrayList<>();
	    
	    for(Item i: records) {
	    	if(i instanceof Port)
	    	    ports.add((Port)i);
	    	if(i instanceof Plane)
	    	    planes.add((Plane)i);
	    	if(i instanceof Ship)
	    	    ships.add((Ship)i);
	    }
	    
	    planes.get(0).setPort(ports.get(0));
	    planes.get(1).setPort(ports.get(0));
	    ships.get(0).setPort(ports.get(0));
	    
	    planes.get(2).setPort(ports.get(1));
	    ships.get(1).setPort(ports.get(1));
	    ships.get(2).setPort(ports.get(1));
	    
	    Set<Plane> itemsSet1 = new HashSet<Plane>();
	    itemsSet1.add(planes.get(0));
	    itemsSet1.add(planes.get(1));
	    
	    Set<Ship> itemsSet2 = new HashSet<Ship>();
	    itemsSet2.add(ships.get(0));
	    
	    Set<Plane> itemsSet3 = new HashSet<Plane>();
	    itemsSet3.add(planes.get(2));
	    
	    Set<Ship> itemsSet4 = new HashSet<Ship>();
	    itemsSet4.add(ships.get(1));
	    itemsSet4.add(ships.get(2));
	    
	    ports.get(0).setPlanes(itemsSet1);
	    ports.get(0).setShips(itemsSet2);
	    
	    ports.get(1).setPlanes(itemsSet3);
	    ports.get(1).setShips(itemsSet4);
	    
	    
	    
	    itemService.insert(ports.get(0));
	    itemService.insert(ports.get(1));
	    
	    itemService.insert(planes.get(0));
	    itemService.insert(planes.get(1));
	    itemService.insert(planes.get(2));
	    
	    itemService.insert(ships.get(0));
	    itemService.insert(ships.get(1));
	    itemService.insert(ships.get(2));
	}

	private static ArrayList<Item>  getDataFromCSV(String file) {
		ArrayList<Item> records = new ArrayList<Item>();
	    
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] values = line.split(";");
				if(values.length > 0)
				{
					if(values[0].equals("Plane") && values.length == 3) {
						records.add(new plane(values[1], values[2]));
					}
					
					if(values[0].equals("Ship") && values.length == 5) {
						try {
							Date dt = convertToDate(LocalDate.of(Integer.parseInt(values[2]) , 
									Integer.parseInt(values[3]), Integer.parseInt(values[4])));
							records.add(new Ship(values[1], dt));
						}catch (Exception e) {}	
					}
					
					if(values[0].equals("Port") && values.length == 3) {
						try {
							records.add(new Port(values[1], Integer.parseInt(values[2])));
						}catch (Exception e) {}	
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
}
