package ui;

import java.util.Iterator;
import java.util.Scanner;
import model.Meal;
import model.Seat;
import utilities.SectionType;
import utilities.AircraftModel;
import utilities.Constants;

/**
 * Acts as a parser interfacing with the user.
 * 
 * @author Leif Ekeroot
 */

public class Parser {
	private Scanner scanner = new Scanner(System.in);
	private LogicIF logicIF = new LogicIF();
	private int selectedAircraftNo = 1;

	/**********************************************************************
	 * Main parsing loop
	 *
	 *********************************************************************/

	public void parse() {
		boolean doContinue = true;

		while (doContinue) {
			try {
				String command = readLine("Enter a command: ");

				switch (command.toUpperCase()) {
				case Constants.HELP_CMD:
					parseHelp();
					break;
				case Constants.BOOK_CMD:
					parseBook();
					break;
				case Constants.LIST_CMD:
					parseList();
					break;
				case Constants.SUM_CMD:
					parseSum();
					break;
				case Constants.CLEAR_CMD:
					parseClear();
					break;
				case Constants.SELECT_CMD:
					parseSelect();
					break;
				case Constants.DEPART_CMD:
					parseDepart();
					break;
				case Constants.ADD_AIRCRAFT_CMD:
					parseAddAircraft();
					break;
				case Constants.REMOVE_AIRCRAFT_CMD:
					parseRemoveAircraft();
					break;
				case Constants.EXIT_CMD:
					doContinue = false;
					break;
				case "THROW":
					throw new NullPointerException();
					// For testing purposes only
				default:
					errorMessage("Invalid command " + command);
				}
			} catch (RuntimeException e) {
				errorMessage("Internal error - exception %s caught", e);
			}
		}
	}

	/**********************************************************************
	 * Methods for parsing the parameters for the commands
	 * 
	 *********************************************************************/

	private void parseHelp() {
		infoMessage("Available commands are:");
		infoMessage("  %s", Constants.BOOK_CMD);
		infoMessage("  %s", Constants.LIST_CMD);
		infoMessage("  %s", Constants.SUM_CMD);
		infoMessage("  %s", Constants.CLEAR_CMD);
		infoMessage("  %s", Constants.SELECT_CMD);
		infoMessage("  %s", Constants.DEPART_CMD);
		infoMessage("  %s", Constants.ADD_AIRCRAFT_CMD);
		infoMessage("  %s", Constants.REMOVE_AIRCRAFT_CMD);
		infoMessage("  %s", Constants.EXIT_CMD);
	}

	private void parseBook() {
		if (!logicIF.isAircraftAvailable(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is not available", selectedAircraftNo);
			return;
		}

		if (logicIF.isAircraftFlying(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is currently flying. Bookings are disabled.", selectedAircraftNo);
			return;
		}

		boolean isFirstAvailable = logicIF.areSeatsAvailable(selectedAircraftNo, SectionType.FIRST);
		boolean isEconomyAvailable = logicIF.areSeatsAvailable(selectedAircraftNo, SectionType.ECONOMY);

		String passengerName;
		SectionType selectedSection;
		int selectedSeat;
		int selectedMeal;

		// Enter section type (first or economy)
		if (isFirstAvailable && isEconomyAvailable) {
			selectedSection = parseSectionType("Enter first or economy class (f/e): ");
		} else if (isFirstAvailable) {
			infoMessage("Only seats in first class are available");
			selectedSection = SectionType.FIRST;
		} else if (isEconomyAvailable) {
			infoMessage("Only seats in economy class are available");
			selectedSection = SectionType.ECONOMY;
		} else {
			errorMessage("\tNO AVAILABLE SEATS!\n\tThere is another flight later on. Would you like rebook ?");
			return;
		}

		// List available seats
		displayFreeSeatList(selectedAircraftNo, selectedSection);

		// Get seat
		selectedSeat = parseSeatNo("Enter seat no (0 = cancel reservation): ", selectedAircraftNo, selectedSection);
		if (selectedSeat == 0) {
			return;
		}

		// Get passenger name
		passengerName = readLine("Enter passenger name: ");

		// List meals and get meal no
		if (displayMeals(selectedSection)) {
			selectedMeal = parseMealNo("Enter meal no (0 = no meal): ", selectedSection);
		} else {
			infoMessage("No meals available");
			selectedMeal = 0;
		}

		// Output reservation information
		if (selectedMeal != 0) {
			infoMessage("Seat %d in aircraft %d reserved for %s. Meal %d ordered.", //
					selectedSeat, //
					selectedAircraftNo, //
					passengerName, //
					selectedMeal);
			infoMessage("Total price %.2f", //
					logicIF.getSeatPrice(selectedAircraftNo, selectedSeat) + logicIF.getMealPrice(selectedMeal));
		} else {
			infoMessage("Seat %d reserved for %s. No meal ordered.", //
					selectedSeat, //
					selectedAircraftNo, //
					passengerName);
			infoMessage("Total price %.2f", //
					logicIF.getSeatPrice(selectedAircraftNo, selectedSeat));
		}

		// Confirm reservation
		if (parseConfirm("Confirm reservation (y/n): ")) {
			logicIF.makeReservation(selectedAircraftNo, selectedSeat, passengerName, selectedMeal);
			infoMessage("Reservation made. Booking number %d-%d.", selectedAircraftNo, selectedSeat);
		} else {
			infoMessage("Cancelled. No reservation made.");
		}
	}

	// TODO: Implement REBOOK command

	private void parseList() {
		if (!logicIF.isAircraftAvailable(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is not available", selectedAircraftNo);
			return;
		}

		if (logicIF.isAircraftFlying(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is currently flying.", selectedAircraftNo);
			return;
		}

		displaySeats(selectedAircraftNo);
	}

	private void parseSum() {
		int forAircraft = parseAircraftNo("Enter aircraft no (0 = all): ");

		if (forAircraft != 0) {
			if (logicIF.isAircraftFlying(forAircraft)) {
				errorMessage("Aircraft %d is currently flying.", forAircraft);
				return;
			}

			infoMessage("Total revenue for aircraft %d: %.2f", forAircraft, logicIF.getTotalRevenue(forAircraft));
			infoMessage("Total profit for aircraft %d: %.2f", forAircraft, logicIF.getTotalProfit(forAircraft));
		} else {
			infoMessage("Total revenue for all aircrafts: %.2f", logicIF.getTotalRevenue());
			infoMessage("Total profit for all aircrafts: %.2f", logicIF.getTotalProfit());
		}
	}

	private void parseClear() {
		if (!logicIF.isAircraftAvailable(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is not available", selectedAircraftNo);
			return;
		}

		if (parseConfirm(String.format("Confirm clear for aircraft %d (y/n): ", selectedAircraftNo))) {
			if (logicIF.isAircraftFlying(selectedAircraftNo)) {
				errorMessage("Selected aircraft %d is currently flying.", selectedAircraftNo);
				return;
			}

			logicIF.clearAllReservations(selectedAircraftNo);
			infoMessage("All reservations cancelled");
		} else {
			infoMessage("Cancelled. No changes made.");
		}
	}

	private void parseSelect() {
		int newAircraftNo = parseAircraftNo("Enter aircraft no (0 = cancel): ");
		if (newAircraftNo == 0) {
			return;
		}

		selectedAircraftNo = newAircraftNo;
	}

	private void parseDepart() {
		if (!logicIF.isAircraftAvailable(selectedAircraftNo)) {
			errorMessage("Selected aircraft %d is not available", selectedAircraftNo);
			return;
		}

		if (parseConfirm(String.format("Confirm departure for aircraft %d (y/n): ", selectedAircraftNo))) {
			if (logicIF.isAircraftFlying(selectedAircraftNo)) {
				errorMessage("Selected aircraft %d is currently flying.", selectedAircraftNo);
				return;
			}

			logicIF.departAircraft(selectedAircraftNo);
			infoMessage("Aircraft %d has been scheduled for take off. Bookings are disabled.", selectedAircraftNo);
			promptRuntime();
		} else {
			infoMessage("Cancelled. No changes made.");
		}
	}

	private void parseAddAircraft() {
		int newAircraftNo = parseNewAircraftNo("Enter aircraft no (0 = cancel): ");
		if (newAircraftNo == 0) {
			return;
		}
		AircraftModel aircraftModel = parseAircraftModel(
				"Enter aircraft model (business jet / jumbo jet, exit = cancel): ");

		logicIF.addAircraft(newAircraftNo, aircraftModel);
		infoMessage("Aircraft %d added to the fleet", newAircraftNo);
	}

	private void parseRemoveAircraft() {
		int existingAircraft = parseAircraftNo("Enter aircraft no (0 = cancel): ");
		if (existingAircraft == 0) {
			return;
		}

		if (!(logicIF.isAircraftFlying(existingAircraft))) {
			logicIF.removeAircraft(existingAircraft);
			infoMessage("Aircraft %d removed from the fleet", existingAircraft);
		}
	}

	/**********************************************************************
	 * Methods for parsing parameter values
	 * 
	 *********************************************************************/

	private int parseAircraftNo(String prompt) {
		while (true) {
			String aircraftString = readLine(prompt);

			try {
				int aircraftNo = Integer.parseInt(aircraftString);
				if (aircraftNo == 0 || logicIF.isAircraftAvailable(aircraftNo)) {
					return aircraftNo;
				} else {
					errorMessage("Error: aircraft %d is not defined", aircraftNo);
				}

			} catch (NumberFormatException e) {
				errorMessage("Error: %s is not an integer", aircraftString);
			}
		}
	}

	private int parseNewAircraftNo(String prompt) {
		while (true) {
			String aircraftString = readLine(prompt);

			try {
				int aircraftNo = Integer.parseInt(aircraftString);
				if (aircraftNo == 0 || !logicIF.isAircraftAvailable(aircraftNo)) {
					return aircraftNo;
				} else {
					errorMessage("Error: aircraft %d is already defined", aircraftNo);
				}

			} catch (NumberFormatException e) {
				errorMessage("Error: %s is not an integer", aircraftString);
			}
		}
	}

	private AircraftModel parseAircraftModel(String prompt) {
		while (true) {
			String modelString = readLine(prompt);

			switch (modelString.toUpperCase()) {
			case Constants.JUMBO_JET:
				return AircraftModel.JUMBO_JET;
			case Constants.BUSINESS_JET:
				return AircraftModel.BUSINESS_JET;
			default:
				errorMessage("Error: invalid input %s", modelString);
			}
		}
	}

	private SectionType parseSectionType(String prompt) {
		while (true) {
			String sectionString = readLine(prompt);

			if (sectionString.equalsIgnoreCase("F")) {
				return SectionType.FIRST;
			} else if (sectionString.equalsIgnoreCase("E")) {
				return SectionType.ECONOMY;
			} else {
				errorMessage("Error: invalid input %s", sectionString);
			}
		}
	}

	private int parseSeatNo(String prompt, int AircraftNo, SectionType selectedSection) {
		while (true) {
			String seatString = readLine(prompt);

			try {
				int seatNo = Integer.parseInt(seatString);
				if (seatNo == 0 || logicIF.isSeatAvailable(AircraftNo, seatNo, selectedSection)) {
					return seatNo;
				} else {
					errorMessage("Error: %d is not an available seat", seatNo);
				}

			} catch (NumberFormatException e) {
				errorMessage("Error: %s is not an integer", seatString);
			}
		}
	}

	private int parseMealNo(String prompt, SectionType selectedSection) {
		while (true) {
			String mealString = readLine(prompt);

			try {
				int mealNo = Integer.parseInt(mealString);
				if (mealNo == 0 || logicIF.isMealAvailable(mealNo, selectedSection)) {
					return mealNo;
				} else {
					errorMessage("Error: %d is not an available meal", mealNo);
				}

			} catch (NumberFormatException e) {
				errorMessage("Error: %s is not an integer", mealString);
			}
		}
	}

	private boolean parseConfirm(String prompt) {
		while (true) {
			String sectionString = readLine(prompt);

			if (sectionString.equalsIgnoreCase("Y")) {
				return true;
			} else if (sectionString.equalsIgnoreCase("N")) {
				return false;
			} else {
				errorMessage("Error: invalid input %s", sectionString);
			}
		}
	}

	/**********************************************************************
	 * Utilities for outputting Meal and Seat items
	 * 
	 *********************************************************************/

	private void displayFreeSeatList(int aircraftNo, SectionType sectionType) {
		Iterator<Seat> iter = logicIF.getSeats(aircraftNo);
		infoMessage("Available seats in aircraft %d: ", aircraftNo);
		boolean areAnySeatsOutput = false;

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getSectionType() == sectionType && seat.getPassenger() == null) {
				if (areAnySeatsOutput) {
					System.out.print(", ");
				} else {
					areAnySeatsOutput = true;
				}
				System.out.print(seat.getSeatID());
			}
		}
		System.out.println();
	}

	private void displaySeats(int aircraftNo) {
		Iterator<Seat> iter = logicIF.getSeats(aircraftNo);

		infoMessage("Reservations made in aircraft %d: ", aircraftNo);
		displaySeatHeader();
		while (iter.hasNext()) {
			displaySeat(iter.next());
		}
	}

	private void displaySeatHeader() {
		StringBuffer sb = new StringBuffer();
		appendString(sb, "Seat", 8);
		sb.append(" ");
		appendString(sb, "Section", 10);
		sb.append(" ");
		appendString(sb, "Price", 15);
		sb.append(" ");
		appendString(sb, "Passenger name", 30);
		sb.append(" ");
		appendString(sb, "Meal no", 8);
		System.out.println(sb);
	}

	private void displaySeat(Seat seat) {
		StringBuffer sb = new StringBuffer();
		appendString(sb, String.format("%4d", seat.getSeatID()), 8);

		sb.append(" ");
		appendString(sb, seat.getSectionType().toString(), 10);
		sb.append(" ");
		appendString(sb, String.format("%8.2f", seat.getSeatPrice()), 15);

		if (seat.getPassenger() != null) {
			sb.append(" ");
			appendString(sb, seat.getPassenger().getName(), 30);
			sb.append(" ");

			if (seat.getPassenger().getMealNo() != 0)
				appendString(sb, String.format("%7d", seat.getPassenger().getMealNo()), 8);
		}
		System.out.println(sb);
	}

	private boolean displayMeals(SectionType sectionType) {
		Iterator<Meal> iter = logicIF.getMeals();
		boolean anyMealsFound = false;

		while (iter.hasNext()) {
			Meal meal = iter.next();
			if (meal.getSectionType() == sectionType) {
				if (!anyMealsFound) {
					displayMealHeader();
					anyMealsFound = true;
				}
				displayMeal(meal);
			}
		}

		return anyMealsFound;
	}

	private void displayMealHeader() {
		StringBuffer sb = new StringBuffer();
		appendString(sb, "No", 4);
		sb.append(" ");
		appendString(sb, "Description", 30);
		sb.append(" ");
		appendString(sb, "Price", 10);
		System.out.println(sb);
	}

	private void displayMeal(Meal meal) {
		StringBuffer sb = new StringBuffer();
		appendString(sb, String.format("%2d", meal.getMealID()), 4);
		sb.append(" ");
		appendString(sb, meal.getMealDescription(), 30);
		sb.append(" ");
		appendString(sb, String.format("%5.2f", meal.getMealPrice()), 10);
		System.out.println(sb);
	}

	/**********************************************************************
	 * Misc. utilities
	 * 
	 *********************************************************************/

	private void appendString(StringBuffer sb, String s, int length) {
		for (int i = 0; i < length; i++) {
			if (i < s.length()) {
				sb.append(s.charAt(i));
			} else {
				sb.append(' ');
			}
		}
	}

	private void infoMessage(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	private void errorMessage(String format, Object... args) {
		System.out.println(String.format(format, args));
	}

	private String readLine(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();
	}

	private void promptRuntime() {

		paus(Constants.DELAY_TIME);
	}

	private void paus(int sleep) {

		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}