package org.kontr.hib;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;


@Entity
@Table(name = "ship")
public class Ship implements Item {
	@Override
	public String toString() {
		return "Ship [id=" + id + ", name=" + name + ", date=" + date + ", port=" + port + "]";
	}

	public Ship() {
	}

	public Ship(String name, Date date) {
		this.name = name;
		this.date = date;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
     
    @Column(name = "name")
    private String name;
    
    @Column(name= "date")
    Date date;
    
	@ManyToOne
    @JoinColumn(name="port_id", nullable=false)
    private Port port;
     
    public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
