package model;

import utilities.SectionType;
import utilities.Constants;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description field
 * 
 * @author
 */

public abstract class Aircraft {

	private ArrayList<Seat> seatList;

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
	
//	private void addSeats(ArrayList<Seat> seats) {
//
//		while (seats.size() < Constants.NO_OF_SEATS) {
//
//			if (seats.size() < Constants.NO_OF_FIRST_CLASS_SEATS) {
//				seatList.add(new Seat(seats.size(), Constants.PRICE_FIRST, SectionType.FIRST));
//			} else {
//				seatList.add(new Seat(seats.size(), Constants.PRICE_ECONOMY, SectionType.ECONOMY));
//			}
//		}
//	}

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
