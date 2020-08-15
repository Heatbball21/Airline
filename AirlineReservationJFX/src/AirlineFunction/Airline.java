package AirlineFunction;

import java.util.Scanner;

public class Airline {

	Scanner s = new Scanner(System.in);
	BoardingPass b = new BoardingPass();

	// variables
	public int TotalSeats;
	public String FlightName;
	public boolean[] seating;
	public boolean found = false; // boolean to check if a seat has been found each run
	public int choice;
	int FirstClassCounter; // counter to iterate through the first class seats
	int EconomyCounter; // counter to iterate through the economy seats
	char move; // y or n to move seats

	public Airline(String FlightName, int seats) {
		if (seats > 1) {
			this.TotalSeats = seats;
			this.FlightName = FlightName;
			seating = new boolean[seats];
			CreateSeats();

		} else {
			System.out.println("ERROR --- not enough seats must be 2 or more");
			System.exit(0);
		}
	}

	public void CreateSeats() {

		for (int j = 0; j < TotalSeats; j++) {
			seating[j] = false;

		}

	}

	public void ReserveSeat(int choice) {

		// prints current seating
		// UpdateSeating();
		this.choice = choice;
		found = false; // set found to false at the start of every run
		FirstClassCounter = 0; // set first class counter to 0
		EconomyCounter = (int) this.TotalSeats / 2; // set economy counter to half of total seats

		// start logic

		if (choice == 1) {

			// while loop to run through each first class seat to check if it is taken or
			// open
			while (found == false && FirstClassCounter < EconomyCounter) {
				found = CheckFirstClass(FirstClassCounter);
				FirstClassCounter++;
			}

			// same as above for choice 1
		} else if (choice == 2) {

			while (found == false && EconomyCounter < TotalSeats) {
				found = CheckEconomy(EconomyCounter);
				EconomyCounter++;
			}

			// if 1 or 2 was not entered then give error
		} else {
			System.out.println("Error not a valid answer");
		}

		UpdateSeating();

	}// end reserve seating

	public void moveSeat(int choice) {
		if (choice == 1) {

			while (found == false && EconomyCounter < TotalSeats) {
				found = CheckEconomy(EconomyCounter);
				EconomyCounter++;
			}

		} else if (choice == 2) {

			while (found == false && FirstClassCounter < EconomyCounter) {
				found = CheckFirstClass(FirstClassCounter);
				FirstClassCounter++;
			}
		} else {
			System.out.println("Not valid choice!");
		}
	}

	// print out seating
	public void UpdateSeating() {
		System.out.println("First Class |" + " Economy");
		for (int i = 0; i < (int) TotalSeats / 2; i++) {

			if (seating[i] == false) {
				if (seating[i + (int) TotalSeats / 2] == false) {
					System.out.println("Open" + "           Open");
				} else {
					System.out.println("Open" + "           Taken");
				}
			} else if (seating[i] == true) {
				if (seating[i + (int) TotalSeats / 2] == false) {
					System.out.println("Taken" + "          Open");
				} else {
					System.out.println("Taken" + "          Taken");
				}
			}
		}

	}
	// if seats are showing up not as they should switch to this to print out true
	// false array instead of taken open
//	public void UpdateSeating() {
//		for(int i = 0; i<seating.length; i++) {
//			System.out.println(seating[i]);
//		}
//	}

	// check first class seating
	public boolean CheckFirstClass(int i) {
		// if the seat at the value of FirstClassCounter is false then change it to true
		// and return true to end while loop by setting found = true
		if (this.seating[i] == false) {
			this.seating[i] = true;
			b.printBoardingPass(i + 1, "First Class");
			return true;
		} else {
			return false;
		}

	}

	// check economy seating
	public boolean CheckEconomy(int i) {
		// if the seat at the value of EconomyCounter is false then change it to true
		// and return true to end while loop by setting found = true
		if (this.seating[i] == false) {
			this.seating[i] = true;
			b.printBoardingPass(i + 1, "Economy");
			return true;
		} else {
			return false;
		}

	}

	// setters
	public void setTotalSeats(int TotalSeats) {
		this.TotalSeats = TotalSeats;
	}

	public void setFlightName(String FlightName) {
		this.FlightName = FlightName;
	}

	public void setFirstClassCounter(int FirstClassCounter) {
		this.FirstClassCounter = FirstClassCounter;
	}

	public void setEconomyCounter(int EconomyCounter) {
		this.EconomyCounter = EconomyCounter;
	}

	// getters
	public int getTotalSeats() {
		return this.TotalSeats;
	}

	public String getFlightName() {
		return this.FlightName;
	}

	public int getFirstClassCounter() {
		return this.FirstClassCounter;
	}

	public int getEconomyCounter() {
		return this.EconomyCounter;
	}

	public char getMove() {
		return this.move;
	}

}
