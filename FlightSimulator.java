import java.util.Scanner;
import java.util.Random;
import java.util.*;

public class FlightSimulator {

	private double probability;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		// flight array
		Flight[] flightTerminals = new Flight[20];
		int flightCapacity = 0;

		System.out.println("Welcome to RFK Private International Airport!\n");
		System.out.println("Enter a seed for this simulation: ");

		// set a seed
		long seed = scan.nextLong();
		scan.nextLine();

		Random randomNumberGenerator = new Random();
		randomNumberGenerator.setSeed(seed);

		// menu:
		System.out.println("Enter the probability of a passenger arrival: ");
		double arrivalProb = scan.nextDouble();
		scan.nextLine();
		System.out.println("Enter the probability that a passenger is dequeued: ");
		double dequeueProb = scan.nextDouble();
		scan.nextLine();
		System.out.println("Enter the probability that there is a new flight at RFK: ");
		double newFlightProb = scan.nextDouble();
		scan.nextLine();
		System.out.println("Enter how many minutes this simulation should take: ");
		int totalMin = scan.nextInt();
		scan.nextLine();
		System.out.println("Starting simulation… \n");

		Boolean boardingaf1 = false;
		Boolean generalBoarding = false;

		// simulation:
		for (int currentMinute = 0; currentMinute <= totalMin; currentMinute++) {

			// set strings for each category for print statements
			String events = "";
			String currentlyBoarding = "";
			String departing = "";
			String finalDepatures = "";

			// Get new random number
			float newRand = randomNumberGenerator.nextFloat();

			// loop through all the flights
			for (int j = 0; j < flightTerminals.length; j++) {

				Flight f = flightTerminals[j];

				if (f != null) {
					if (boardingaf1 && !f.getIsAF1()) { // continue boarding if not AF1
						continue;
					}

					// check if flight is ready to depart
					if (f.getMinutesLeftBoarding() == 0) {
						if (!f.getIsAF1()) {
							events += ("Flight to " + f.getDestination() + " is now preparing for depature "
									+ f.getFlightPassengerCount()+ " passengers\n");
						} else {
							events += ("Air Force 1 Flight to " + f.getDestination() + " is now preparing for depature "
									+ f.getFlightPassengerCount() + " passengers\n");
						}

					}

					// flight is departing
					if (f.getMinutesLeftDeparting() <= 0) {
						if (f.getIsAF1()) { // check if flight is AF1
							boardingaf1 = false;
						}

						// remove flight
						flightTerminals[j] = null;
						for (int n = j; n < flightTerminals.length - 1; n++) { // shift all flights over
							flightTerminals[n] = flightTerminals[n + 1];
						}

						flightTerminals[flightTerminals.length - 1] = null; // set last flight to null

						j--; // refreshing f to make sure the right value

						// print table from toString method
						finalDepatures += f.toString();
						continue;

					}

					BoardingQueue bq = f.getBoardingQueue(); // set bq to boarding q

					// Event 1: a passenger is dequeued from a flight’s BoardingQueue
					if (newRand < dequeueProb) {

						if (f.getMinutesLeftBoarding() > 0) {
							try {

								Passenger passenger = bq.dequeuePassenger();

								f.addToFlight(passenger);

								events += (passenger.getOriginalClass() + " passenger " + 
										 " ID " + passenger.getPassengerID() +" on flight to " + f.getDestination() + " has boarded on "
										+ passenger.getPassClass() + " seat \n");

								currentlyBoarding += (passenger.getOriginalClass() + " passenger "
										+ passenger.getPassengerID() + " ID on flight to " + f.getDestination()
										+ " has boarded on " + passenger.getPassClass() + " seat \n");

							} catch (NoPassengerException e) {

							}
						}

					}

					// Event 2: For each boarding flight, a passenger arrives at a flight’s BQ
					if (newRand < arrivalProb) {

						if (f.getMinutesLeftBoarding() > 0) {

							TravelClass passClass = TravelClass.ECONOMY;
							generalBoarding = true; // flight is now boarding

							boolean canAdd = true; // can only add the passenger if they don't have COVID

							String str = "";

							newRand = randomNumberGenerator.nextFloat();

							// a. This passenger is first class (P = 0.1)
							if (newRand < 0.12) {
								passClass = (TravelClass.FIRSTCLASS);
								str = ("first class ");

							}
							// b. This passenger is business class (P = 0.1)
							else if (newRand < 0.24) {
								passClass = (TravelClass.BUSINESSCLASS);
								str = ("business class ");

							}
							// c. This passenger is premium economy (P = 0.3)
							else if (newRand < 0.56) {
								passClass = (TravelClass.PREMIUMECONOMY);
								str = ("premium economy ");

							}

							// d. This passenger is economy (P = 0.4)
							else if (newRand < 0.98) {
								passClass = (TravelClass.ECONOMY);
								str = ("economy ");

							}
							// e. This passenger has COVID-19
							else {

								for (int m = 0; m < flightTerminals.length; m++) {

									Flight ff = flightTerminals[m];
									events += ("COVID positive passenger found attempting to board flight to "
											+ f.getDestination() + ". All current departures and "
											+ "boarding extended by 10 minutes!\n");

									if (ff != null) {
										ff.setMinutesLeftBoarding(ff.getMinutesLeftBoarding() + 10);
										ff.setMinutesLeftDeparting(ff.getMinutesLeftDeparting() + 10);
									}

								}

								canAdd = false;
							}

							if (canAdd) { // passenger does not have COVID -> add to BQ
								try {

									Passenger passenger = new Passenger(bq.getId(), currentMinute, passClass);

									bq.enqueuePassenger(passenger);

									events += str + "  passenger ID " + passenger.getPassengerID() + " on flight to "
											+ f.getDestination() + " has entered the flights boarding queue\n";

								} catch (NoRoomException e) {
									System.out.println("No room in boarding queue");
								}
							}
						}
					}
					
				

					if (f != null && generalBoarding && f.getMinutesLeftBoarding() > 0 ) { // if boarding print currentlyBoarding statements
						currentlyBoarding += ("Flight to " + f.getDestination() + " has " + f.getMinutesLeftBoarding()
								+ " minutes to board, " + f.getFlightPassengerCount() + " passenger(s), "
								+ bq.getQPassengerCount() + " person(s) waiting to board. \n");
					}

					// same for departing
					if (f != null && f.getMinutesLeftDeparting() <= 0) { // if departing print departing statements
						departing += ("Flight " + f.getDestination() + " has " + f.getFlightPassengerCount()
								+ " passengers and " + f.getMinutesLeftDeparting() + " minutes before departure");
					}
				}

				// Decrement minute counters

				// decrement time left boarding
				if (f != null && f.getMinutesLeftBoarding() > 0) {
					f.setMinutesLeftBoarding(f.getMinutesLeftBoarding() - 1);
				}
				// decrement time left departing
				else {

					if (f != null) {
						f.setMinutesLeftDeparting(f.getMinutesLeftDeparting() - 1);
						// decrement boarding time so for loop for preparing for departure is not always
						// 0
						f.setMinutesLeftBoarding(f.getMinutesLeftBoarding() - 1);
					}

				}

			}

			// Event 3: A new flight begins boarding at RFK
			if (newRand < newFlightProb) {

				newRand = randomNumberGenerator.nextFloat();

				if (flightTerminals[flightTerminals.length - 1] == null) {

					// ask user for destination
					System.out.print("Enter the destination:  ");
					String destination = scan.nextLine();
//					scan.nextLine();

					Flight toAdd = new Flight(destination);

					events += ("A new flight to " + destination + " has begun boarding!\n");

					// a. This flight is normal

					if (newRand < 0.95) {

						toAdd.setIsAF1(false);

						for (int s = 0; s < flightTerminals.length; s++) {
							if (flightTerminals[s] == null) {

								flightTerminals[s] = toAdd;

								break;
							}

						}

					}
					// b. This flight is Air Force 1
					else {
						if (!boardingaf1) {

							// ask user for destination
							System.out.println("Enter the destination");
							String destinationAF1 = scan.nextLine();
							toAdd.setIsAF1(true);

							events += ("A new flight to " + destinationAF1 + " has begun boarding!\n");

							boardingaf1 = true;

							for (int s = 0; s < flightTerminals.length; s++) {
								if (flightTerminals[s] == null) {

									flightTerminals[s] = toAdd;

									break;
								}

							}

						}
					}

				}

			}
			// Strings for print statements
			System.out.println();
			System.out.println("Minute " + currentMinute + "\n");

			System.out.println("Events: ");
			if (events == "") {
				System.out.println("Nothing to note. \n");
			} else {
				System.out.println(events);
			}
			System.out.println("Currently Boarding: ");
			if (currentlyBoarding == "") {
				System.out.println("Nothing to note. \n");
			} else {
				System.out.println(currentlyBoarding);
			}
			System.out.println("Departing: ");
			if (departing == "") {
				System.out.println("Nothing to note. \n");
			} else {
				System.out.println(departing);
			}
			System.out.println("Final Depatures: ");
			if (finalDepatures == "") {
				System.out.println("Nothing to note. \n");
			} else {
				System.out.println(finalDepatures);
			}

		}

	}

}
