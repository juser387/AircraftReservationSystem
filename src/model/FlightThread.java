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
			Thread.sleep(Constants.TAKE_OFF_TIME);
			System.out.println("\n\nTake off - managed by thread ID " + Thread.currentThread().getId());

			Thread.sleep(Constants.FLIGHT_TIME);
			System.out.println("Flight - carried through " + Thread.currentThread().getName());

			Thread.sleep(Constants.REFUEL_TIME);
			System.out.println("Refuel - managed by " + Thread.currentThread().getName());
			
			aircraft.clearAllSeatsUnprotected();
			System.out.println("\nThe Aircraft No " + aircraft.getAircraftID() + " is ready for booking.\n");
			aircraft.setFlying(false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
