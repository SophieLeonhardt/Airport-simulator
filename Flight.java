
public class Flight {

	// data fields
	private String destination;
	private BoardingQueue boardQueue;
	private Passenger[] firstClass;
	private Passenger[] businessClass;
	private Passenger[] premiumEconomy;
	private Passenger[] economy;
	private int minutesLeftBoarding, minutesLeftDeparting, firstClassInd, businessClassInd, premiumEconomyInd,
			economyInd;
	private boolean boarding;

	private boolean isAF1;

	private int flightPassengerCount;

	// Constructors
//	public Flight() {
//		firstClass = new Passenger[2];
//		businessClass = new Passenger[3];
//		premiumEconomy = new Passenger[4];
//		economy = new Passenger[6];
//
//	}

	public Flight(String destination) throws IllegalArgumentException {
		firstClass = new Passenger[2];
		businessClass = new Passenger[3];
		premiumEconomy = new Passenger[4];
		economy = new Passenger[6];
		
		boardQueue = new BoardingQueue();
		
		this.destination = destination;
		this.minutesLeftBoarding = 25;
		this.minutesLeftDeparting = 5;
		this.firstClassInd = 0;
		this.businessClassInd = 0;
		this.premiumEconomyInd = 0;
		this.economyInd = 0;
		this.flightPassengerCount = 0;
		this.boarding = false;

	}

	public BoardingQueue getBoardingQueue() {
		return boardQueue;
	}

	public int getMinutesLeftBoarding() {
		return minutesLeftBoarding;
	}

	public int getMinutesLeftDeparting() {
		return minutesLeftDeparting;
	}

	public boolean getIsAF1() {
		return isAF1;
	}

	public void setMinutesLeftBoarding(int newMinutesLeftBoarding) {
		minutesLeftBoarding = newMinutesLeftBoarding;
	}

	public void setMinutesLeftDeparting(int newMinutesLeftDeparting) {
		minutesLeftDeparting = newMinutesLeftDeparting;
	}

	public void setDestination(String newDestination) {
		destination = newDestination;
	}

	public String getDestination() {
		return destination;
	}

	public void setIsAF1(boolean newIsAF1) {
		isAF1 = newIsAF1;
	}

	// method to get flight passenger count
	public int getFlightPassengerCount() {
		return flightPassengerCount;
	}

	// method to add Passenger to correct passenger array so they can be put onto
	// flight
	public void addToFlight(Passenger boardedPass) {
		// dequeue boardedPass

		// add boardedPass to correct PassengerArray
		if ( boardedPass.getPassClass().equals(TravelClass.FIRSTCLASS)) { // add to first class array

			if (firstClassInd == 2) { // not enough space in first class
				boardedPass.setPassClass(TravelClass.BUSINESSCLASS); // move passenger to business class
			} else { // there is space in first class
				firstClass[firstClassInd] = boardedPass;
				firstClassInd++;
			}

			flightPassengerCount++; // increment amount of people on flight
		}

		if (boardedPass.getPassClass().equals(TravelClass.BUSINESSCLASS)) { // add to business class
			if (businessClassInd == 3) {
				boardedPass.setPassClass(TravelClass.PREMIUMECONOMY);
			} else {
				businessClass[businessClassInd] = boardedPass;
				businessClassInd++;
			}

			flightPassengerCount++;
		}

		if (boardedPass.getPassClass().equals(TravelClass.PREMIUMECONOMY)) { // add to premium economy

			if (premiumEconomyInd == 4) {
				boardedPass.setPassClass(TravelClass.ECONOMY);
			} else {
				premiumEconomy[premiumEconomyInd] = boardedPass;
				premiumEconomyInd++;
			}

			flightPassengerCount++;
		}

		if (economyInd == 6) {
			// full

		} else {
			economy[economyInd] = boardedPass;
			economyInd++;

			flightPassengerCount++;
		}

	}

	public String toString() {

		String s = "";
		s += (" Seat Type " + " | " + " ID " + " | " + " Arrival Time \n");
		s += ("===========================================\n");

		for (int i = 0; i < 2; i++) {
			if (firstClass[i]!= null) {
				s+=(firstClass[i].getPassClass() + " | " + firstClass[i].getPassengerID() + " | "
						+ firstClass[i].getArrivalTime() + "\n");
			}
			
		}
		for (int i = 0; i < 3; i++) {
			if(businessClass[i] != null) {
				s+=(businessClass[i].getPassClass() + " | " + businessClass[i].getPassengerID() + " | "
						+ businessClass[i].getArrivalTime()+ "\n");
			}
			
		}
		for (int i = 0; i < 4; i++) {
			if(premiumEconomy[i] != null) {
				s+=(premiumEconomy[i].getPassClass() + " | " + premiumEconomy[i].getPassengerID() + " | "
						+ premiumEconomy[i].getArrivalTime()+ "\n");
			}
			
		}
		for (int i = 0; i < 6; i++) {
			if(economy[i]!= null) {
				s+=(economy[i].getPassClass() + " | " + economy[i].getPassengerID() + " | "
						+ economy[i].getArrivalTime()+ "\n");
			}
			
		}
		return s;
	}
}
