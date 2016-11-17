package ui;

import java.util.ArrayList;
import java.util.Iterator;

import model.Aircraft;
import model.Passenger;
import model.Seat;
import utilities.SectionType;

public class LogicIF {

	/*
	 * =======================================================================
	 * Temporary variables
	 * =======================================================================
	 */
	private ArrayList<Integer> aircraftSeats = new ArrayList<>();
	private ArrayList<String> menuMeals = new ArrayList<>();

	public LogicIF() {
		aircraftSeats.add(1);
		aircraftSeats.add(4);
		aircraftSeats.add(7);
		menuMeals.add(" 7 Dagens fisk           64:00");
		menuMeals.add("12 Köttbullar med lingon 59:00");
		menuMeals.add("17 Entrecote             79:00");
	}

	/*
	 * =======================================================================
	 * References to the business model
	 * =======================================================================
	 */
	private Aircraft aircraft = new Aircraft(1);

	public boolean areSeatsAvailable(SectionType sectionType) {
		return true;  // TODO:
	}

	public Iterator<Integer> getAvailableSeats(SectionType sectionType) {
		return aircraftSeats.iterator(); // TODO: Requires rethink
	}

	public boolean isSeatAvailable(int seatNo, SectionType sectionType) {
		Seat seat = aircraft.findSeat(seatNo);
		if (seat != null) {
			return seat.getPassenger() == null && seat.getSectionType() == sectionType;
		} else {
			return false;
		}
	}

	public int getSeatPrice(int seatNo) {
		Seat seat = aircraft.findSeat(seatNo);
		return (int) seat.getSeatPrice(); // TODO: revisit cast
	}

	public Iterator<String> getAvailableMeals(SectionType sectionType) {
		return menuMeals.iterator(); // TODO:
	}

	public boolean isMealAvailable(int mealNo, SectionType sectionType) {
		return mealNo != 3; // TODO:
	}

	public int getMealPrice(int mealNo) {
		return 95; // TODO:
	}

	public int getTotalRevenue() {
		return 10_000; // TODO:
	}

	public int getTotalProfit() {
		return (int) (getTotalRevenue() * 0.3); // TODO:
	}

	public void makeReservation(int seatNo, String passengerName, int mealNo) {
		Passenger passenger = new Passenger(passengerName, mealNo);
		Seat seat = aircraft.findSeat(seatNo);
		seat.setPassenger(passenger);
	}

	public void clearAllReservations() {
		aircraft.clearAllSeats();
	}

}
