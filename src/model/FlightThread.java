package model;

import utilities.Constants;

public class FlightThread implements Runnable {

	private Aircraft aircraft;

	public FlightThread(Aircraft aircraft) {
		
		this.aircraft = aircraft;
	}

	@Override
	public void run() {
		boolean isCalledByMe = true;
		try {
			Thread.sleep(Constants.TAKE_OFF_TIME);
			System.out.println("\n\nTake off - managed by thread ID " + Thread.currentThread().getId());

			Thread.sleep(Constants.FLIGHT_TIME);
			System.out.println("Flight - carried through " + Thread.currentThread().getName());

			Thread.sleep(Constants.REFUEL_TIME);
			System.out.println("Refuel - managed by " + Thread.currentThread().getName());

			aircraft.clearAllSeats(isCalledByMe);
			System.out.println("\nThe Aircraft No " + aircraft.getAircraftID() + " is ready for booking.\n");
			aircraft.setFlying(false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
