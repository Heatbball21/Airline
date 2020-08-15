package AirlineFunction;

public class BoardingPass {

	String spacer = "------------------------------";

	// print the boarding pass of each seat after they have been reserved
	public void printBoardingPass(int seat, String type) {
		System.out.println(spacer);
		System.out.println("Class: " + type + " | Seat: " + seat);
		System.out.println(spacer);
	}

}
