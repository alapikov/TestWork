package org.kontr.hib;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "port")
public class Port implements Item {
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Port(int id, String code, int cap, Set<Plane> planes, Set<Ship> ships) {
		super();
		this.id = id;
		this.code = code;
		this.cap = cap;
		this.planes = planes;
		this.ships = ships;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
     
    @Column(name = "code")
    private String code;
     
    @Column(name= "cap")
    int cap;
    
    public Port() {
	}

	public Port(String code, int cap) {
		this.code = code;
		this.cap = cap;
	}

	@OneToMany(mappedBy="port")
    private Set<Plane> planes;
	
	public Set<Plane> getPlanes() {
		return planes;
	}

	public void setPlanes(Set<Plane> planes) {
		this.planes = planes;
	}

	public Set<Ship> getShips() {
		return ships;
	}

	public void setShips(Set<Ship> ships) {
		this.ships = ships;
	}

	@OneToMany(mappedBy="port")
    private Set<Ship> ships;


	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	@Override
	public String toString() {
		return "Port [id=" + id + ", code=" + code + ", cap=" + cap + "]";
	}
}
