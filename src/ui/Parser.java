package ui;

import java.util.Iterator;
import java.util.Scanner;

import model.Meal;
import model.SectionType;

public class Parser {
	Scanner scanner = new Scanner(System.in);
	LogicIF logicIF = new LogicIF();

	final String helpCmd = "HELP";
	final String bookCmd = "BOOK";
	final String sumCmd = "SUMMARY";
	final String clearCmd = "CLEAR";
	final String exitCmd = "EXIT";

	// ------------------------------------------------------------------------
	// parse() - main parsing loop
	// ------------------------------------------------------------------------
	public void parse() {
		boolean doContinue = true;

		while (doContinue) {
			String command = readLine("Enter a command: ");

			switch (command.toUpperCase()) {
			case helpCmd:
				parseHelp();
				break;
			case bookCmd:
				parseBook();
				break;
			case sumCmd:
				parseSum();
				break;
			case clearCmd:
				parseClear();
				break;
			case exitCmd:
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
		infoMessage("  %s", bookCmd);
		infoMessage("  %s", sumCmd);
		infoMessage("  %s", clearCmd);
		infoMessage("  %s", exitCmd);
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
			selectedSection = SectionType.FIRST;
		} else if (isEconomyAvailable) {
			selectedSection = SectionType.ECONOMY;
		} else {
			errorMessage("No seats available");
			return;
		}

		// List available seats
		Iterator<Integer> iter1 = logicIF.getAvailableSeats(selectedSection);
		System.out.print("Available seats: ");

		while (iter1.hasNext()) {
			System.out.print(iter1.next());
			System.out.print(", ");
		}
		System.out.println();
		// TODO: Clean up the print-out

		// Get seat
		selectedSeat = parseSeatNo("Enter seat no (0 = cancel reservation): ", selectedSection);
		if (selectedSeat == 0) {
			return;
		}

		// Get passenger name
		passengerName = readLine("Enter passenger name: ");

		// List meals
		Iterator<Meal> iter2 = logicIF.getAvailableMeals(selectedSection);  // TODO: review
		infoMessage("Available meals:");
		while (iter2.hasNext()) {
			Meal meal = iter2.next();
			if (meal.getSectionType() == selectedSection) {
				infoMessage(String.valueOf(meal.getMealID()) + " " + meal.getMealDescription() + " price: "
						+ String.valueOf(meal.getMealPrice()));
			}
		}

		// Get meal and compute price
		selectedMeal = parseMealNo("Enter meal no (0 = no meal): ", selectedSection);

		if (selectedMeal != 0) {
			infoMessage("Seat %d reserved for %s. Meal %d ordered.", selectedSeat, passengerName, selectedMeal);
			infoMessage("Total price %d", logicIF.getSeatPrice(selectedSeat) + logicIF.getMealPrice(selectedMeal));
		} else {
			infoMessage("Seat %d reserved for %s. No meal ordered.", selectedSeat, passengerName);
			infoMessage("Total price %d", logicIF.getSeatPrice(selectedSeat));
		}

		// Confirm reservation
		if (parseConfirm("Confirm reservation (y/n): ")) {
			logicIF.makeReservation(selectedSeat, passengerName, selectedMeal);
			infoMessage("Reservation made. Booking number %d.", selectedSeat);
		} else {
			infoMessage("Cancelled. No reservation made.");
		}
	}

	private void parseSum() {
		infoMessage("Total revenue %d", logicIF.getTotalRevenue());
		infoMessage("Total profit %d", logicIF.getTotalProfit());
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
	// Utilities
	// ------------------------------------------------------------------------
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
