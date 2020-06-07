package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {

	//Coda degli eventi
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	//Parametri di simulazione
	private Map <Integer, Integer> tavoli = new TreeMap<>();
	private double tolleranza;
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(23, 00);

	//Modello del mondo
	//Tavoli

	//Valori da calcolare
	private int totClienti;
	private int nSoddisfatti;
	private int nInsoddisfatti;

	public Simulator() {
		this.tavoli.put(10, 2);
		this.tavoli.put(8, 4);
		this.tavoli.put(6, 4);
		this.tavoli.put(4, 5);
	}

	public void setTolleranza(double d) {
		this.tolleranza = d;
	}
	public int getTotClienti() {
		return totClienti;
	}
	public int getnSoddisfatti() {
		return nSoddisfatti;
	}
	public int getnInsoddisfatti() {
		return nInsoddisfatti;
	}

	public void run() {

		this.totClienti=this.nInsoddisfatti=this.nSoddisfatti=0;
		int i =0;
		this.queue.clear();

		//Preparazione mondo + coda degli eventi

		LocalTime oraArrivoCliente = this.oraApertura;

		do {

			int numPersone = ThreadLocalRandom.current().nextInt(1, 11);

			Duration durata = Duration.of(ThreadLocalRandom.current().nextInt(60, 121), ChronoUnit.MINUTES);

			double tolleranza = ThreadLocalRandom.current().nextDouble(0.1, 0.9);

			Event e = new Event(oraArrivoCliente, numPersone, durata, tolleranza, EventType.ARRIVATO);

			oraArrivoCliente = oraArrivoCliente.plus(Duration.of(ThreadLocalRandom.current().nextInt(1, 11), ChronoUnit.MINUTES));
			//			System.out.println(e);

			i++;
			this.queue.add(e);
			if(oraArrivoCliente.isAfter(oraChiusura)) {
				oraArrivoCliente = this.oraApertura;
			}

		} while(i<2000);
		//		System.out.println("Stampa coda:\n");
		//		System.out.println(this.queue);

		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
			//			System.out.println(e);
		}
		System.out.format("Arrived %d clients, %d were dissatisfied, %d satisfied\n", this.totClienti, this.nInsoddisfatti, this.nSoddisfatti);
	}

	private void processEvent(Event e) {

		switch(e.getType()) {

		case ARRIVATO:
			this.totClienti+=e.getNumPersone();
			int tavoloOccupato = isPosto(e.getNumPersone());
			if(tavoloOccupato>0) {
				int disponibili = this.tavoli.get(tavoloOccupato)-1;
				this.tavoli.put(tavoloOccupato, disponibili);
				System.out.println(tavoloOccupato+" "+disponibili);
				Event nuovo = new Event(e.getTime().plus(e.getDurata()), e.getNumPersone(), e.getDurata(), e.getTolleranza(), tavoloOccupato, EventType.SERVITO);
				this.queue.add(nuovo);
			}
			else {
				if(e.getTolleranza()>this.tolleranza) {
					Event nuovo = new Event(e.getTime().plus(e.getDurata()), e.getNumPersone(), e.getDurata(), e.getTolleranza(), EventType.BANCONE);
					this.queue.add(nuovo);
				}
				else
					this.nInsoddisfatti+=e.getNumPersone();
			}
			break;

		case SERVITO:
			this.nSoddisfatti+=e.getNumPersone();
			liberaTavolo(e);
			break;

		case BANCONE:
			this.nSoddisfatti+=e.getNumPersone();
			break;
		}

	}


	private int isPosto(int nPersone){

		for(Integer i:this.tavoli.keySet()) {
			if(nPersone<=i && this.tavoli.get(i)!=0 && nPersone>=i/2) {
				return i;
			}
		}
		return -1;
	}

	private void liberaTavolo(Event e) {
		int disponibili = this.tavoli.get(e.getTavoloOccupato())+1;
		this.tavoli.put(e.getTavoloOccupato(), disponibili);
		
	}

}
