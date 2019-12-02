/*
package Practica4Unnoba.Entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
public class Invite {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Event evento;
	
	@Version
	protected int version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Usuario getUser() {
		return usuario;
	}
	public void setUser(Usuario usuario) {
		this.usuario = usuario;
	}
	public Event getEvent() {
		return evento;
	}
	public void setEvent(Event evento) {
		this.evento = evento;
	}
		
}
*/