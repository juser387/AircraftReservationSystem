package ui;

import java.util.Iterator;
import java.util.Scanner;

import model.Meal;
import model.Seat;
import utilities.SectionType;

public class Parser {
	private Scanner scanner = new Scanner(System.in);
	private LogicIF logicIF = new LogicIF();

	private static final String HELP_CMD = "HELP";
	private static final String BOOK_CMD = "BOOK";
	private static final String LIST_CMD = "LIST";
	private static final String SUM_CMD = "SUMMARY";
	private static final String CLEAR_CMD = "CLEAR";
	private static final String EXIT_CMD = "EXIT";

	// ------------------------------------------------------------------------
	// parse() - main parsing loop
	// ------------------------------------------------------------------------
	public void parse() {
		boolean doContinue = true;

		while (doContinue) {
			String command = readLine("Enter a command: ");

			switch (command.toUpperCase()) {
			case HELP_CMD:
				parseHelp();
				break;
			case BOOK_CMD:
				parseBook();
				break;
			case LIST_CMD:
				parseList();
				break;
			case SUM_CMD:
				parseSum();
				break;
			case CLEAR_CMD:
				parseClear();
				break;
			case EXIT_CMD:
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
		infoMessage("  %s", BOOK_CMD);
		infoMessage("  %s", SUM_CMD);
		infoMessage("  %s", CLEAR_CMD);
		infoMessage("  %s", EXIT_CMD);
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
			errorMessage("\tNO AVAILABLE SEATS!\n\tThere is another flight later on. Would you like book ?");
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
			infoMessage("Seat %d reserved for %s. Meal %d ordered.", selectedSeat, passengerName, selectedMeal);
			infoMessage("Total price %.2f", logicIF.getSeatPrice(selectedSeat) + logicIF.getMealPrice(selectedMeal));
		} else {
			infoMessage("Seat %d reserved for %s. No meal ordered.", selectedSeat, passengerName);
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
		infoMessage("Total revenue %.2f", logicIF.getTotalRevenue());
		infoMessage("Total profit %.2f", logicIF.getTotalProfit());
	}

	private void parseClear() {
		if (parseConfirm("Confirm clear (y/n): ")) {
			logicIF.clearAllReservations();
			infoMessage("All reservations cleared");
		} else {
			infoMessage("Cancelled. No changes made.");
		}
	}

	// ------------------------------------------------------------------------
	// Methods for parsing parameter values
	// ------------------------------------------------------------------------
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
		System.out.print("Available seats: ");
		boolean isAnySeatsOutput = false;

		while (iter.hasNext()) {
			Seat seat = iter.next();
			if (seat.getSectionType() == sectionType && seat.getPassenger() == null) {
				if (isAnySeatsOutput) {
					System.out.print(", ");
				} else {
					isAnySeatsOutput = true;
				}
				System.out.print(seat.getSeatID());
			}
		}
		System.out.println();
	}

	private void displaySeats() {
		Iterator<Seat> iter = logicIF.getSeats();

		displaySeatHeader();
		while (iter.hasNext()) {
			displaySeat(iter.next());
		}
	}

	private void displaySeatHeader() {
		StringBuffer sb = new StringBuffer();
		appendString(sb, "Seat", 4);
		sb.append(" ");
		appendString(sb, "Section", 8);
		sb.append(" ");
		appendString(sb, "Price", 10);
		sb.append(" ");
		appendString(sb, "Passenger name", 30);
		sb.append(" ");
		appendString(sb, "Meal", 8);
		System.out.println(sb);
	}

	private void displaySeat(Seat seat) {
		StringBuffer sb = new StringBuffer();
		appendString(sb, String.valueOf(seat.getSeatID()), 4);
		// TODO: values are left aligned, should be right-aligned
		sb.append(" ");
		appendString(sb, seat.getSectionType().toString(), 8);
		sb.append(" ");
		appendString(sb, String.format("%.2f", seat.getSeatPrice()), 10);
		// TODO: values are left aligned, should be right-aligned
		if (seat.getPassenger() != null) {
			sb.append(" ");
			appendString(sb, seat.getPassenger().getName(), 30);
			sb.append(" ");
			appendString(sb, String.valueOf(seat.getPassenger().getMealNo()), 4);
			// TODO: Do not write zero if no meal is ordered
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
		appendString(sb, String.valueOf(meal.getMealID()), 4);
		sb.append(" ");
		appendString(sb, meal.getMealDescription(), 30);
		sb.append(" ");
		appendString(sb, String.format("%.2f", meal.getMealPrice()), 10);
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
