package ui;

import java.util.ArrayList;
import java.util.Iterator;

import model.SectionType;

public class LogicIF {

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

	public boolean areSeatsAvailable(SectionType sectionType) {
		return true;
	}

	public Iterator<Integer> getAvailableSeats(SectionType sectionType) {
		return aircraftSeats.iterator();
	}

	public boolean isSeatAvailable(int seatNo, SectionType sectionType) {
		Iterator<Integer> iter = aircraftSeats.iterator();
		while (iter.hasNext()) {
			if (seatNo == iter.next()) {
				return true;
			}
		}
		return false;
	}

	public int getSeatPrice(int seatNo) {
		return 2_000;
	}

	public Iterator<String> getAvailableMeals(SectionType sectionType) {
		return menuMeals.iterator();
	}

	public boolean isMealAvailable(int mealNo, SectionType sectionType) {
		return mealNo != 3;
	}

	public int getMealPrice(int mealNo) {
		return 95;
	}

	public int getTotalRevenue() {
		return 10_000;
	}

	public int getTotalProfit() {
		return (int) (getTotalRevenue() * 0.3);
	}

	public void makeReservation(int seatNo, String passengerName, int mealNo) {
		System.out.println("---- Reservation made in the business logic ----");
	}

	public void clearAllReservations() {
		System.out.println("---- All reservations cleared in the business logic ----");
	}

}
