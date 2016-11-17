package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class description field
 * 
 * @author
 */

public class Aircraft {

	final static double PRICE_FIRST = 20_000.0;
	final static double PRICE_ECONOMY = 5_000.0;
	final static int NO_OF_SEATS = 10;
	final static int NO_OF_FIRST_CLASS_SEATS = 5;

	private static ArrayList<Seat> seatList;

	private int id;

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public Aircraft(int id) {

		this.id = id;

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
	public void addSeats(ArrayList<Seat> seats) {

		while (seats.size() <= NO_OF_SEATS) {

			if (seats.size() <= NO_OF_FIRST_CLASS_SEATS) {
				seatList.add(new Seat(seats.size(), PRICE_FIRST, SectionType.FIRST));
			} else {
				seatList.add(new Seat(seats.size(), PRICE_ECONOMY, SectionType.ECONOMY));
			}
		}
	}

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
}
