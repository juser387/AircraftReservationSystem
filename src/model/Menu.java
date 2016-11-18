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
		mealList.add(new Meal(2, 72.0, "Steak", SectionType.FIRST));
		mealList.add(new Meal(3, 65.0, "Tacos", SectionType.FIRST));
		mealList.add(new Meal(4, 48.0, "Lux Wrap", SectionType.FIRST));
		mealList.add(new Meal(5, 55.0, "Pasta", SectionType.FIRST));

		mealList.add(new Meal(6, 56.0, "Hamburger", SectionType.ECONOMY));
		mealList.add(new Meal(7, 34.0, "Sandwich", SectionType.ECONOMY));
		mealList.add(new Meal(8, 32.0, "Chili Toast", SectionType.ECONOMY));
		mealList.add(new Meal(9, 47.0, "Chicken Nuggets", SectionType.ECONOMY));
		mealList.add(new Meal(10, 42.0, "Light Salad", SectionType.ECONOMY));
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
