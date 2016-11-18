package model;

import java.util.ArrayList;
import java.util.Iterator;
import utilities.SectionType;

/**
 * Class description field
 * 
 * @author
 */

public class Menu {

	private ArrayList<Meal> mealList = new ArrayList<>();

	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * @param ....
	 * 
	 *********************************************************************/
	public Menu() {

		mealList.add(new Meal(1, 95.0, "Meatballs", SectionType.FIRST));
		mealList.add(new Meal(3, 72.0, "Steak", SectionType.FIRST));
		mealList.add(new Meal(5, 56.0, "Hamburger", SectionType.ECONOMY));
		mealList.add(new Meal(9, 34.0, "Sandwich", SectionType.ECONOMY));
	}

	public Iterator<Meal> getIterator() {
		return mealList.iterator();
	}

	public Meal findMeal(int mealNo) {
		for (Meal meal : mealList) {
			if (meal.getMealID() == mealNo)
				return meal;
		}
		return null;
	}

}
