package ui;

import java.util.Iterator;

import model.Aircraft;
import model.BusinessJet;
import model.Fleet;
import model.JumboJet;
import model.Meal;
import model.Menu;
import model.Passenger;
import model.Seat;
import utilities.AircraftModel;
import utilities.SectionType;

public class LogicIF {

	/*
	 * =======================================================================
	 * References to the business model
	 * =======================================================================
	 */

	private Fleet fleet = new Fleet();
	private Menu menu = new Menu();

	// The constructor initialises the aircraft fleet with one initial aircraft.
	LogicIF() {
		fleet.addAircraft(new JumboJet(1));
	}

	public boolean isAircraftAvailable(int aircraftNo) {
		return fleet.findAircraft(aircraftNo) != null;
	}

	public boolean isAircraftFlying(int aircraftNo) {
		return fleet.findAircraft(aircraftNo).getFlying();
	}

	public void addAircraft(int aircraftNo, AircraftModel aircraftModel) {
		Aircraft newAircraft;

		switch (aircraftModel) {
		case JUMBO_JET:
			newAircraft = new JumboJet(aircraftNo);
			break;
		case BUSINESS_JET:
			newAircraft = new BusinessJet(aircraftNo);
			break;
		default:
			return;
		}

		fleet.addAircraft(newAircraft);
	}

	public void removeAircraft(int aircraftNo) {
		fleet.removeAircraft(aircraftNo);
	}

	public void departAircraft(int aircraftNo) {
		fleet.findAircraft(aircraftNo).depart();
	}

	public boolean areSeatsAvailable(int aircraftNo, SectionType sectionType) {
		Iterator<Seat> iter = getSeats(aircraftNo);

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getSectionType() == sectionType && seat.getPassenger() == null) {
				return true;
			}
		}
		return false;
	}

	public Iterator<Seat> getSeats(int aircraftNo) {
		return fleet.findAircraft(aircraftNo).getIterator();
	}

	public boolean isSeatAvailable(int aircraftNo, int seatNo, SectionType sectionType) {
		Seat seat = fleet.findAircraft(aircraftNo).findSeat(seatNo);
		if (seat != null) {
			return seat.getPassenger() == null && seat.getSectionType() == sectionType;
		} else {
			return false;
		}
	}

	public double getSeatPrice(int aircraftNo, int seatNo) {
		Seat seat = fleet.findAircraft(aircraftNo).findSeat(seatNo);
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

	private double getTotalRevenue(Aircraft aircraft) {
		Iterator<Seat> iter = aircraft.getIterator();
		double totalRevenue = 0.0;

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getPassenger() != null) {
				totalRevenue += seat.getSeatPrice();

				Meal meal = menu.findMeal(seat.getPassenger().getMealNo());
				if (meal != null) {
					totalRevenue += meal.getMealPrice();
				}
			}
		}

		return totalRevenue;
	}

	public double getTotalRevenue(int aircraftNo) {
		return getTotalRevenue(fleet.findAircraft(aircraftNo));
	}

	public double getTotalRevenue() {
		Iterator<Aircraft> iter = fleet.getIterator();
		double totalRevenue = 0.0;

		while (iter.hasNext()) {
			totalRevenue += getTotalRevenue(iter.next());
		}
		return totalRevenue;
	}

	private double getTotalProfit(Aircraft aircraft) {
		return getTotalRevenue(aircraft) * 0.3;
	}

	public double getTotalProfit(int aircraftNo) {
		return getTotalProfit(fleet.findAircraft(aircraftNo));
	}

	public double getTotalProfit() {
		Iterator<Aircraft> iter = fleet.getIterator();
		double totalProfit = 0.0;
		
		while (iter.hasNext()) {
			totalProfit += getTotalProfit(iter.next());
		}

		return totalProfit;
	}

	public void makeReservation(int aircraftNo, int seatNo, String passengerName, int mealNo) {
		Seat seat = fleet.findAircraft(aircraftNo).findSeat(seatNo);
		seat.setPassenger(new Passenger(passengerName, mealNo));
	}

	public void clearAllReservations(int aircraftNo) {
		fleet.findAircraft(aircraftNo).clearAllSeats();
	}

}
