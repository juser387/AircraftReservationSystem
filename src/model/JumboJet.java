package model;

import java.util.ArrayList;
import utilities.Constants;
import utilities.SectionType;

/**
 * Implements a specific aircraft model adapted for JUMBO JET with seat configuration details.
 * 
 * @author Kenneth Nilsson
 */

public class JumboJet extends Aircraft {

	/**********************************************************************
	 * Create seat configuration and the aircraft object
	 * 
	 * @param id
	 *            the aircraft ID
	 * 
	 *********************************************************************/

	public JumboJet(int id) {

		super(id);

		seatList = new ArrayList<Seat>();

		addSeats(seatList);
	}

	/**********************************************************************
	 * Add seat configuration to JUMBO JET aircraft model
	 *
	 * @param seats
	 *            the list of seats
	 * 
	 *********************************************************************/

	@Override
	public void addSeats(ArrayList<Seat> seats) {

		while (seats.size() < Constants.JUMBO_JET_NO_OF_SEATS) {

			if (seats.size() < Constants.JUMBO_JET_NO_OF_FIRST_CLASS_SEATS) {
				seatList.add(new Seat(seats.size(), Constants.PRICE_FIRST, SectionType.FIRST));
			} else {
				seatList.add(new Seat(seats.size(), Constants.PRICE_ECONOMY, SectionType.ECONOMY));
			}
		}
	}
}
