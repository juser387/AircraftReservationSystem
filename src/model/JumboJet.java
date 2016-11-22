package model;

import java.util.ArrayList;
import utilities.Constants;
import utilities.SectionType;

public class JumboJet extends Aircraft {

	public JumboJet(int id) {

		super(id);

		seatList = new ArrayList<Seat>();

		addSeats(seatList);
	}

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
