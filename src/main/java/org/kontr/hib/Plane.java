package org.kontr.hib;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

 
@Entity
@Table(name = "plane")
public class Plane implements Item {
	@Override
	public String toString() {
		return "Plane [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", port=" + port + "]";
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
     
    @Column(name = "name")
    private String name;
     
    @Column(name= "manufacturer")
    String manufacturer;
    
    @ManyToOne
    @JoinColumn(name="port_id", nullable=false)
    private Port port;
     
    public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public Plane() {
    }
 
    public Plane(String name, String manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }
     
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getManufacturer() {
        return manufacturer;
    }
 
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public Plane(int id, String name, String manufacturer, Shelf port) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.port = port;
	}
}