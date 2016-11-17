package model;

/**
 * Class description field
 * 
 * @author
 */

public class Passenger {

	private String name;
	private Meal meal;
	
	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public Passenger(String name, Meal meal) {
		this.name = name;
		this.meal = meal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
}
