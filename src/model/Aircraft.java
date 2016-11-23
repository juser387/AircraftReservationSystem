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

	public Iterator<Seat> getIterator() {
		return seatList.iterator();
	}

	public Seat findSeat(int seatNo) {
		for (Seat seat : seatList) {
			if (seat.getSeatID() == seatNo)
				return seat;
		}
		return null;
	}

	public void clearAllSeats() {
		for (Seat seat : seatList) {
			seat.setPassenger(null);
		}
	}
	
	public void depart() {

		setFlying(true);
		
		Thread flightThread = new Thread(new FlightThread(this));
		flightThread.start();
	}
	
	public int getAircraftID() {
		return aircraftID;
	}


	public synchronized boolean isFlying() {
		return isFlying;
	}


	public synchronized void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}
	
	
}
