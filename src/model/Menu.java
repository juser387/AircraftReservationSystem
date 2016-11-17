package model;

/**
 * Class description field
 * 
 * @author
 */

public class Menu {

	private Meal meal;

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public Menu() {
		
		meal = new Meal(0, 0.0, "DummyMealDescription", SectionType.FIRST);
		
		this.meal = meal;
	}
}
