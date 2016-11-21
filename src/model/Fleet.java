package model;

import java.util.ArrayList;
import java.util.Iterator;
import utilities.SectionType;
import utilities.Constants;


public class Fleet {

	private ArrayList<Aircraft> aircraftList = new ArrayList<>();

	public Fleet() {
	}
	public Iterator<Aircraft> getIterator() {
		return aircraftList.iterator();
	}

	public Aircraft findAircraft(int aircraftNo) {
		for (Aircraft aircraft : aircraftList) {
			if (aircraft.getAircraftID() == aircraftNo)
				return aircraft;
		}
		return null;
	}

	public void addAircraft(ArrayList<Aircraft> aircraft) {

		//TODO: update the body
//		while (aircraft.size() < Constants.NO_OF_SEATS) {

//			if (aircraft.size() < Constants.NO_OF_FIRST_CLASS_SEATS) {
//				aircraftList.add(new Aircraft(aircraft.size(), Constants.PRICE_FIRST, SectionType.FIRST));
//			} else {
//				aircraftList.add(new Aircraft(aircraft.size(), Constants.PRICE_ECONOMY, SectionType.ECONOMY));
//			}
//		}

	}
}
