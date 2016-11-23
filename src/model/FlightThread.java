package model;

import utilities.Constants;

public class FlightThread implements Runnable {

	Aircraft aircraft;

	public FlightThread(Aircraft aircraft) {
		super();
		this.aircraft = aircraft;
	}

	@Override
	public void run() {
		try {
			System.out.println("aircraft is " + aircraft.getAircraftID());

			Thread.sleep(Constants.TAKE_OFF_TIME);
			System.out.println("Take off" + Thread.currentThread().getName());

			Thread.sleep(Constants.FLIGHT_TIME);
			System.out.println("Flight" + Thread.currentThread().getName());

			Thread.sleep(Constants.REFUEL_TIME);
			System.out.println("Refuel" + Thread.currentThread().getName());
			
			aircraft.clearAllSeats();
			System.out.println("The Aircraft No " + Thread.currentThread().getName() + " is ready for booking.");
			aircraft.setFlying(false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
