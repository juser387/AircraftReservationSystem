package model;

import utilities.SectionType;

/**
 * Describes a meal.
 * 
 * @author Kenneth Nilsson
 */

public class Meal {
	
	private int mealID;
	private double mealPrice;
	private String mealDescription;
	private SectionType sectionType;
	
	/**********************************************************************
	 * Constructor description
	 *
	 * @param ...
	 * 
	 *********************************************************************/
	public Meal(int mealID, double mealPrice, String mealDescription, SectionType sectionType) {
		
		this.mealID = mealID;
		this.mealPrice = mealPrice;
		this.mealDescription = mealDescription;
		this.sectionType = sectionType;
	}
	
	public int getMealID() {
		return mealID;
	}
	
	public double getMealPrice() {
		return mealPrice;
	}
	
	public String getMealDescription() {
		return mealDescription;
	}
	
	public SectionType getSectionType() {
		return sectionType;
	}
}