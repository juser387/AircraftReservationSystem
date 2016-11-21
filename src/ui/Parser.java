package ui;

import java.util.Iterator;
import java.util.Scanner;

import model.Meal;
import model.Seat;
import utilities.SectionType;
import utilities.Constants;

public class Parser {
	private Scanner scanner = new Scanner(System.in);
	private LogicIF logicIF = new LogicIF();
	private int selectedAircraft = 1;

	public static final String SELECT_CMD = "SELECT";
	public static final String DEPART_CMD = "DEPART";
	public static final String ADD_AIRCRAFT_CMD = "ADD AIRCRAFT";
	public static final String REMOVE_AIRCRAFT_CMD = "REMOVE AIRCRAFT";
	// TODO: move back the other commands here

	// ------------------------------------------------------------------------
	// parse() - main parsing loop
	// ------------------------------------------------------------------------
	public void parse() {
		boolean doContinue = true;

		while (doContinue) {
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
			case SELECT_CMD:
				parseSelect();
				break;
			case DEPART_CMD:
				parseDepart();
				break;
			case ADD_AIRCRAFT_CMD:
				parseAddAircraft();
				break;
			case REMOVE_AIRCRAFT_CMD:
				parseRemoveAircraft();
				break;
			case Constants.EXIT_CMD:
				doContinue = false;
				break;
			default:
				System.out.println("Invalid command " + command);
			}
		}
	}

	// ------------------------------------------------------------------------
	// Methods for parsing the parameters for the commands
	// ------------------------------------------------------------------------
	private void parseHelp() {
		infoMessage("Available commands are:");
		infoMessage("  %s", Constants.BOOK_CMD);
		infoMessage("  %s", Constants.LIST_CMD);
		infoMessage("  %s", Constants.SUM_CMD);
		infoMessage("  %s", Constants.CLEAR_CMD);
		infoMessage("  %s", SELECT_CMD);
		infoMessage("  %s", DEPART_CMD);
		infoMessage("  %s", ADD_AIRCRAFT_CMD);
		infoMessage("  %s", REMOVE_AIRCRAFT_CMD);
		infoMessage("  %s", Constants.EXIT_CMD);
	}

	private void parseBook() {
		boolean isFirstAvailable = logicIF.areSeatsAvailable(SectionType.FIRST);
		boolean isEconomyAvailable = logicIF.areSeatsAvailable(SectionType.ECONOMY);

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
		displayFreeSeatList(selectedSection);

		// Get seat
		selectedSeat = parseSeatNo("Enter seat no (0 = cancel reservation): ", selectedSection);
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
			infoMessage("Seat %d in aircraft %d reserved for %s. Meal %d ordered.", selectedSeat, selectedAircraft,
					passengerName, selectedMeal);
			infoMessage("Total price %.2f", logicIF.getSeatPrice(selectedSeat) + logicIF.getMealPrice(selectedMeal));
		} else {
			infoMessage("Seat %d reserved for %s. No meal ordered.", selectedSeat, selectedAircraft, passengerName);
			infoMessage("Total price %.2f", logicIF.getSeatPrice(selectedSeat));
		}

		// Confirm reservation
		if (parseConfirm("Confirm reservation (y/n): ")) {
			logicIF.makeReservation(selectedSeat, passengerName, selectedMeal);
			infoMessage("Reservation made. Booking number %d.", selectedSeat);
		} else {
			infoMessage("Cancelled. No reservation made.");
		}
	}

	// TODO: Implement REBOOK command

	private void parseList() {
		displaySeats();
	}

	private void parseSum() {
		int forAircraft = parseAircraftNo("Enter aircraft no (0 = all): ");
		if (forAircraft != 0) {
			infoMessage("Total revenue for aircraft %d: %.2f", forAircraft, logicIF.getTotalRevenue());
			infoMessage("Total profit for aircraft %d: %.2f", forAircraft, logicIF.getTotalProfit());
		} else {
			infoMessage("Total revenue for all aircrafts: %.2f", logicIF.getTotalRevenue(forAircraft));
			infoMessage("Total profit for all aircrafts: %.2f", logicIF.getTotalProfit(forAircraft));
		}
	}

	private void parseClear() {
		if (parseConfirm(String.format("Confirm clear for aircraft %d (y/n): ", selectedAircraft))) {
			logicIF.clearAllReservations();
			infoMessage("All reservations cleared");
		} else {
			infoMessage("Cancelled. No changes made.");
		}
	}

	private void parseSelect() {
		int newlySelectedAircraft = parseAircraftNo("Enter aircraft no (0 = cancel): ");
		if (newlySelectedAircraft == 0) {
			return;
		}

		selectedAircraft = newlySelectedAircraft;
	}

	private void parseDepart() {
		if (parseConfirm(String.format("Confirm departure for aircraft %d (y/n): ", selectedAircraft))) {
			logicIF.departAircraft(selectedAircraft);
			infoMessage("Aircraft %d has been scheduled for take off. Bookings are disabled.", selectedAircraft);
		} else {
			infoMessage("Cancelled. No changes made.");
		}
	}

	private void parseAddAircraft() {
		int newAircraft = parseNewAircraftNo("Enter aircraft no (0 = cancel): ");
		if (newAircraft == 0) {
			return;
		} 
		
		//TODO: Add aircraft model
		
		logicIF.addAircraft(newAircraft);
		infoMessage("Aircraft %d added to the fleat", newAircraft);
	}

	private void parseRemoveAircraft() {
		int existingAircraft = parseAircraftNo("Enter aircraft no (0 = cancel): ");
		if (existingAircraft == 0) {
			return;
		}

		logicIF.removeAircraft(existingAircraft);
		infoMessage("Aircraft %d removed from the fleat", existingAircraft);
	}

	// ------------------------------------------------------------------------
	// Methods for parsing parameter values
	// ------------------------------------------------------------------------
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
				if (aircraftNo == 0 || logicIF.isAircraftAvailable(aircraftNo)) {
					return aircraftNo;
				} else {
					errorMessage("Error: aircraft %d is already defined", aircraftNo);
				}

			} catch (NumberFormatException e) {
				errorMessage("Error: %s is not an integer", aircraftString);
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

	private int parseSeatNo(String prompt, SectionType selectedSection) {
		while (true) {
			String seatString = readLine(prompt);

			try {
				int seatNo = Integer.parseInt(seatString);
				if (seatNo == 0 || logicIF.isSeatAvailable(seatNo, selectedSection)) {
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

	// ------------------------------------------------------------------------
	// Utilities for outputting Meal and Seat items
	// ------------------------------------------------------------------------
	private void displayFreeSeatList(SectionType sectionType) {
		Iterator<Seat> iter = logicIF.getSeats();
		infoMessage("Available seats in aircraft %d: ", selectedAircraft);
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

	private void displaySeats() {
		Iterator<Seat> iter = logicIF.getSeats();

		infoMessage("Reservations made in aircraft %d: ", selectedAircraft);
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

	// ------------------------------------------------------------------------
	// Utilities
	// ------------------------------------------------------------------------
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
}
