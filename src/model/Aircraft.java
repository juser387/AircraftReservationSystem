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
	
	public void depart(int aircraftNo) {
		
		//TODO: update the body 
	}
	
	public int getAircraftID() {
		return aircraftID;
	}
}
