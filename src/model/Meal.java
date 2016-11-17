package model;

/**
 * Class description field
 * 
 * @author
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
	 * @param ....
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