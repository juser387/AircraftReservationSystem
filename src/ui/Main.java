package ui;

import ui.Parser;

/**
 * This application simulates a minimal set of commands applied for aircraft
 * reservation system.
 * 
 * User can add two kind of aircraft models with different seat configurations,
 * book passengers, list bookings, cancel bookings, depart aircraft and remove
 * aircraft from the system.
 * 
 * @author Leif Ekeroot
 * @author Kenneth Nilsson
 * 
 * @version 2016.11.18
 */

public class Main {

	public static void main(String[] args) {
		Parser parser = new Parser();

		parser.parse();
	}
}
