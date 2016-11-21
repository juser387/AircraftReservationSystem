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
	// TODO: Replace with fleet
	private Menu menu = new Menu();

	public boolean isAircraftAvailable(int aircraftNo) {
		// TODO: Add support for multiple aircrafts (optional bonus assignment
		// 1)
		return true;
	}

	public void departAircraft(int aircraftNo) {
		// TODO: Implement depart aircraft
	}

	public void addAircraft(int aircraftNo) {
		// TODO: Implement add aircraft
	}

	public void removeAircraft(int aircraftNo) {
		// TODO: Implement remove aircraft
	}

	public boolean areSeatsAvailable(SectionType sectionType) {
		Iterator<Seat> iter = getSeats();

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getSectionType() == sectionType && seat.getPassenger() == null) {
				return true;
			}
		}
		return false;
	}

	public Iterator<Seat> getSeats() {
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

	public double getSeatPrice(int seatNo) {
		Seat seat = aircraft.findSeat(seatNo);
		return seat.getSeatPrice();
	}

	public Iterator<Meal> getMeals() {
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

	public double getTotalRevenue(int aircraftNo) {
		//TODO: implement getTotalRevenue(int aircraftNo)
		return 0.0;
	}

	public double getTotalRevenue() {
		//TODO: implement getTotalRevenue for all aircraft (move code below to method above)
		Iterator<Seat> iter = aircraft.getIterator();
		double sum = 0.0;

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getPassenger() != null) {
				sum += seat.getSeatPrice();

				Meal meal = menu.findMeal(seat.getPassenger().getMealNo());
				if (meal != null) {
					sum += meal.getMealPrice();
				}
			}
		}

		return sum;
	}

	public double getTotalProfit(int aircraftNo) {
		//TODO: implement getTotalProfit(int aircraftNo)
		return 0.0;
	}

	public double getTotalProfit() {
		//TODO: implement getTotalRevenue for all aircraft (move code below to method above)
		return getTotalRevenue() * 0.3;
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
