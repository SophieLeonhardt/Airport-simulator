
public class Passenger {

	//data fields
	private int passengerID;
	private int arrivalTime; //in minutes
	TravelClass passClass;
	TravelClass originalClass;
	
	//Constructors
	public Passenger() {
		
	}
	public Passenger(int passengerID, int arrivalTime, TravelClass passClass) {
		this.passengerID = passengerID;
		this.arrivalTime = arrivalTime;
		this.passClass = passClass;
		this.originalClass = passClass;
	}
	
	//getters
	public int getPassengerID() {
		return passengerID;
	}
	public int getArrivalTime() {
		return arrivalTime; 
	}
	public TravelClass getPassClass() {
		return passClass;
	}
	public TravelClass getOriginalClass() {
		return originalClass;
	}
	
	//setters
	public void setPassengerID(int newPassengerID) {
		passengerID = newPassengerID;
	}
	public void setArrivalTime(int newArrivalTime) {
		arrivalTime = newArrivalTime;
	}
	public void setPassClass(TravelClass newPassClass) {
		passClass = newPassClass;
	}
	
	
}
