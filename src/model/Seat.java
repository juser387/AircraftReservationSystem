package model;

import utilities.SectionType;

/**
 * Describes seat reservation with different section types.
 * 
 * @author Kenneth Nilsson
 */

public class Seat extends Object {

	private int seatID;
	private double seatPrice;
	private Passenger passenger = null;
	private SectionType sectionType;

	private static int objCount = 0;

	public Seat() {

		objCount++;
	}

	/**********************************************************************
	 * Constructor description
	 *
	 * @param seatID
	 *            seatID ...
	 * @param seatPrice
	 *            seatPrice ....
	 * 
	 *********************************************************************/
	public Seat(int seatID, double seatPrice, SectionType sectionType) {

		objCount++;

		this.seatID = seatID+1;
		this.seatPrice = seatPrice;
		this.sectionType = sectionType;
	}

	public int getSeatID() {
		return seatID;
	}

	public Passenger getPassenger() {

		return passenger;
	}

	public double getSeatPrice() {
		
		return seatPrice;
	}

	public SectionType getSectionType() {

		return sectionType;
	}

	public void setPassenger(Passenger newPassenger) {
		passenger = newPassenger;
	}

	public int seatCounter() {

		return objCount;
	}

	@Override
	protected void finalize() throws Throwable {

		System.out.println("Finalizing objects on heap in progress...");
		--objCount;

		super.finalize();
	}
}
