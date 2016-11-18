package ui;

import java.util.Iterator;

import model.Aircraft;
import model.Meal;
import model.Menu;
import model.Passenger;
import model.Seat;
import utilities.SectionType;

public class LogicIF {

	/*
	 * =======================================================================
	 * References to the business model
	 * =======================================================================
	 */
	private Aircraft aircraft = new Aircraft(1);
	private Menu menu = new Menu();

	public boolean areSeatsAvailable(SectionType sectionType) {
		Iterator<Seat> iter = getAvailableSeats();

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getSectionType() == sectionType && seat.getPassenger() == null) {
				return true;
			}
		}
		return false;
	}

	public Iterator<Seat> getAvailableSeats() { // TODO: Rename this operation
		return aircraft.getIterator();
	}

	public boolean isSeatAvailable(int seatNo, SectionType sectionType) {
		Seat seat = aircraft.findSeat(seatNo);
		if (seat != null) {
			return seat.getPassenger() == null && seat.getSectionType() == sectionType;
		} else {
			return false;
		}
	}

	// TODO: change return type to double
	public int getSeatPrice(int seatNo) {
		Seat seat = aircraft.findSeat(seatNo);
		return (int) seat.getSeatPrice(); 
	}

	public Iterator<Meal> getAvailableMeals() {  // TODO: Rename this method
		return menu.getIterator();
	}

	public boolean isMealAvailable(int mealNo, SectionType sectionType) {
		Meal meal = menu.findMeal(mealNo);
		if (meal != null) {
			return meal.getSectionType() == sectionType;
		} else {
			return false;
		}
	}

	public int getMealPrice(int mealNo) {
		Meal meal = menu.findMeal(mealNo);
		if (meal != null) {
			return (int) meal.getMealPrice();
		} else {
			return 0;
		}
	}

	// TODO: Retrieve data from the business model
	// TODO: change return type to double
	public int getTotalRevenue() {
		return 10_000; 
	}

	// TODO: Retrieve data from the business model
	// TODO: change return type to double
	public int getTotalProfit() {
		return (int) (getTotalRevenue() * 0.3); 
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
