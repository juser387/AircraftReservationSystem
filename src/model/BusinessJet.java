package model;

import java.util.ArrayList;
import utilities.Constants;
import utilities.SectionType;

/**
 * Implements a specific aircraft model adapted for BUSINESS JET with seat configuration details.
 * 
 * @author Kenneth Nilsson
 */

public class BusinessJet extends Aircraft {

	/**********************************************************************
	 * Create seat configuration and the aircraft object
	 * 
	 * @param id
	 *            the aircraft ID
	 * 
	 *********************************************************************/

	public BusinessJet(int id) {

		super(id);

		seatList = new ArrayList<Seat>();

		addSeats(seatList);
	}

	/**********************************************************************
	 * Add seat configuration to BUSINESS JET aircraft model
	 *
	 * @param seats
	 *            the list of seats
	 * 
	 *********************************************************************/

	@Override
	public void addSeats(ArrayList<Seat> seats) {

		while (seats.size() < Constants.BUSINESS_JET_NO_OF_SEATS) {

			if (seats.size() < Constants.BUSINESS_JET_NO_OF_FIRST_CLASS_SEATS) {
				seatList.add(new Seat(seats.size(), Constants.PRICE_FIRST, SectionType.FIRST));
			} else {
				seatList.add(new Seat(seats.size(), Constants.PRICE_ECONOMY, SectionType.ECONOMY));
			}
		}
	}
}
