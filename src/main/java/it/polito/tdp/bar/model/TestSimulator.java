package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {
		
		Simulator sim = new Simulator();
//		System.out.println(5/2);
		sim.setTolleranza(0.5);
		
		sim.run();

	}

}
