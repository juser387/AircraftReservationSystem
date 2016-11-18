package model;

/**
 * Class description field
 * 
 * @author
 */

public class Passenger {

	private String name;
	private int mealNo;

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
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