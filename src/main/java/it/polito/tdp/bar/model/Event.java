package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		ARRIVATO,
		SERVITO,
		BANCONE,
//		ANDATO_VIA
	}
	
	private LocalTime time;
	private int numPersone;
	private Duration durata;
	private double tolleranza;
	private int tavoloOccupato;
	private EventType type;
	
	
	public int getTavoloOccupato() {
		return tavoloOccupato;
	}
	public void setTavoloOccupato(int tavoloOccupato) {
		this.tavoloOccupato = tavoloOccupato;
	}
	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}
	public Event(LocalTime time, int numPersone, Duration durata, double tolleranza, int tavoloOccupato,
			EventType type) {
		super();
		this.time = time;
		this.numPersone = numPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavoloOccupato = tavoloOccupato;
		this.type = type;
	}
	public Event(LocalTime time, int numPersone, Duration durata, double tolleranza, EventType type) {
		super();
		this.time = time;
		this.numPersone = numPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.type = type;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public int getNumPersone() {
		return numPersone;
	}
	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}
	public Duration getDurata() {
		return durata;
	}
	public void setDurata(Duration durata) {
		this.durata = durata;
	}
	public double getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	@Override
	public int compareTo(Event other) {
		// TODO Auto-generated method stub
		return this.time.compareTo(other.time);
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", numPersone=" + numPersone + ", durata=" + durata + ", tolleranza="
				+ tolleranza + ", tavoloOccupato=" + tavoloOccupato + ", type=" + type + "]";
	}
	
	
	

}
