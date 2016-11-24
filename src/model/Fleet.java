package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Fleet {

	private ArrayList<Aircraft> aircraftList = new ArrayList<>();

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

	public void addAircraft(Aircraft aircraft) {
		aircraftList.add(aircraft);
	}

	// May only be called when the aircraft is not flying
	public void removeAircraft(int aircraftNo) {
		Aircraft aircraft = findAircraft(aircraftNo);
		
		if (aircraft.getFlying()) {
			throw new IllegalStateException();
		}
		
		if (aircraft != null) {
			aircraftList.remove(aircraft);
		}
	}
}
