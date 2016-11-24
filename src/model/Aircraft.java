package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description field
 * 
 * @author
 */

public abstract class Aircraft {

	protected ArrayList<Seat> seatList;

	private int aircraftID;

	private boolean isFlying = false;

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public Aircraft(int id) {

		this.aircraftID = id;

		seatList = new ArrayList<Seat>();
		addSeats(seatList);
	}

	/**********************************************************************
	 * Method description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public abstract void addSeats(ArrayList<Seat> seats);

	// May only be called when the aircraft is not flying
	public Iterator<Seat> getIterator() {
		if (getFlying()) {
			throw new IllegalStateException();
		}

		return seatList.iterator();
	}

	// May only be called when the aircraft is not flying
	public Seat findSeat(int seatNo) {
		if (getFlying()) {
			throw new IllegalStateException();
		}

		for (Seat seat : seatList) {
			if (seat.getSeatID() == seatNo)
				return seat;
		}
		return null;
	}

	// May be called from a flight thread or during aircraft bookings 
	public void clearAllSeats(boolean isCalledByThread) {

		if (!isCalledByThread) {
			if (getFlying())
				throw new IllegalStateException();
		}

		for (Seat seat : seatList) {
			seat.setPassenger(null);
		}
	}

	// May only be called when the aircraft is not flying
	public void depart() {
		if (getFlying()) {
			throw new IllegalStateException();
		}

		setFlying(true);

		Thread flightThread = new Thread(new FlightThread(this));
		flightThread.start();
	}

	// Does not have to be protected
	public int getAircraftID() {
		return aircraftID;
	}

	public synchronized boolean getFlying() {
		return isFlying;
	}

	public synchronized void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

}
