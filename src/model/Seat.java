package model;

/**
 * Class description field
 * 
 * @author
 */

public class Seat extends Object {

	private int seatID;
	private double seatPrice;
	private Passenger passenger;
	private SectionType sectionType;
	
	private static int objCount = 0;

	public Seat() {
		
		objCount++;
	}
	
	/**********************************************************************
	 * Constructor description
	 *
	 * @param seatID seatID ...
	 * @param seatPrice seatPrice ....
	 * 
	 *********************************************************************/
	public Seat(int seatID, double seatPrice, SectionType sectionType) {
		
		objCount++;
		
		this.seatID = seatID;
		this.seatPrice = seatPrice;
		this.sectionType = sectionType;
		
		
		passenger = new Passenger("Dummy Name", null);  //TODO: create an instance of meal, if we need need it
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
	
	public int getAvailableSeats() {
		
		return 0;
	}
	
	public void addSeat(Passenger newPAssenger) {
		
	}
	
//	public void addSectionType(SectionType section) {
//	
//		sectionType = (section == section.FIRST) ? sectionType.FIRST : sectionType.ECONOMY; 
//	}
	
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
