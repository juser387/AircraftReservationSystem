package model;

/**
 * Describes passengers with meal reservation.
 * 
 * @author Kenneth Nilsson
 */

public class Passenger {

	private String name;
	private int mealNo;

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * 
	 *********************************************************************/
	public Passenger(String name, int mealNo) {
		this.name = name;
		this.mealNo = mealNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMealNo() {
		return mealNo;
	}

	public void setMealNo(int mealNo) {
		this.mealNo = mealNo;
	}
}