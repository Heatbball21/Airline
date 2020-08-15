package AirlineInterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
//import java.io.IOException;

import AirlineFunction.*;

public class AirlineInterfaceMain extends Application {

	File FileName = new File("Flights.txt");
	

	boolean read = true;
	String line = null;
	String[] splitNameAndArray;
	
	FileReader fileReader;
	BufferedReader reader;

	FileWriter fileWriter;
	BufferedWriter writer;

	ArrayList<String> listName = new ArrayList<String>();
	ArrayList<String> listSeats = new ArrayList<String>();
	ArrayList<String> listTaken = new ArrayList<String>();
	boolean nameTaken = false;

	Label FlightNameLabel = new Label("Name of flight ");
	TextField FlightNameField = new TextField();

	Label NumberOfSeatsLabel = new Label("Number of seats ");
	TextField NumberOfSeatsField = new TextField("2");
	
	TextField FindFlightName = new TextField();

	Button submit = new Button("Click to create flight");
	Button finished = new Button("Finished");

	Label FirstClassPlaceholder = new Label("First Class");
	Label EconomyPlaceholder = new Label("Economy");

	// Create new Airline for demo
	Airline air;// = new Airline("Test", 10);
	error error = new error();

	// Main main pane view
	GridPane main = new GridPane();
	GridPane reserve = new GridPane();
	GridPane add = new GridPane();
	GridPane delete = new GridPane();
	GridPane pass = new GridPane();

	boolean clicked = false;

	Button yes = new Button("Yes");
	Button no = new Button("No");
	Label move = new Label("Move seat?");
	/*
	 * 
	 * 
	 * START METHOD
	 * 
	 * 
	 */

	@Override
	public void start(Stage PrimaryStage) throws Exception {

		// make the main grid pane look better
		main.setPadding(new Insets(10, 10, 10, 10));
		main.setHgap(10);
		main.setVgap(10);

		FlightNameField.setMinWidth(50);
		NumberOfSeatsField.setMinWidth(50);

		try {
			fileReader = new FileReader(FileName);
			reader = new BufferedReader(fileReader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		printFlights();

		// submit button event handler
		EventHandler<ActionEvent> submitAirline = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//check if the name is already taken
				if (listName.contains(FlightNameField.getText())) {
					nameTaken = true;
					error.nameTaken();
					System.out.println("\n\nName Taken\n\n");
				}

				if (nameTaken == false) {
					//if there are already labels on screen clear them
					if (clicked) {
						clearLabels();
					}

					clicked = true;
					air = new Airline(FlightNameField.getText(), Integer.parseInt(NumberOfSeatsField.getText()));
					//

					try {
						
						createWriter();

						writer.append(FlightNameField.getText());
						writer.append(" ");
						for(int i = 0; i < air.seating.length; i++) {
							if(air.seating[i] == true) {
								writer.append("t");
							}else {
								writer.append("f");
							}
						}
						writer.newLine();
						
						
						writer.append(NumberOfSeatsField.getText());
						
						//writer.append(NumberOfSeatsField.getText());
						writer.newLine();

						//writer.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					printFlights();
					//
					createLabels();
					createName();

					// create and add all new seats to the scene
					try {
						for (int i = 0; i < air.seating.length; i++) {

							if (i == 0) {
								if (clicked) {
									main.getChildren().remove(FirstClassPlaceholder);
								}
								main.add(FirstClassPlaceholder, 0, 1);
							} else if (i == (int) air.seating.length / 2) {
								if (clicked) {
									main.getChildren().remove(EconomyPlaceholder);
								}
								main.add(EconomyPlaceholder, 0, i + 2);
							}

							if (i < (int) air.seating.length / 2) {
								main.add(seatsToLabel(i), 0, i + 2);
							} else {
								main.add(seatsToLabel(i), 0, i + 3);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				System.out.println("end");
				nameTaken = false;
			}
		};

		EventHandler<ActionEvent> moveYes = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (air.choice == 1) {
					air.moveSeat(1);
				} else if (air.choice == 2) {
					air.moveSeat(2);
				} else {
					System.out.println("Not a valid choice!");
				}
				updateSeats();
				main.getChildren().remove(yes);
				main.getChildren().remove(no);
				main.getChildren().remove(move);
			}
		};

		EventHandler<ActionEvent> moveNo = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				main.getChildren().remove(yes);
				main.getChildren().remove(no);
				main.getChildren().remove(move);
			}
		};

		// create different scene controls

		Button ReserveFirst = new Button("Reserve First Class");
		Button ReserveEconomy = new Button("Reserve Economy");
		Button close = new Button("Close Program");

		// Event Handlers for clicking a reserve button
		EventHandler<ActionEvent> FirstClassReserve = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (main.getChildren().contains(move)) {
				} else {
					if (air.seating[air.TotalSeats / 2 - 1] == false || air.seating[air.TotalSeats - 1] == false) {
						air.ReserveSeat(1);

						if (air.found == false) {
							if (main.getChildren().contains(move)) {

							} else {
								main.add(move, 5, 8);
								main.add(yes, 5, 9);
								main.add(no, 5, 10);
							}
						}
						updateSeats();
					} else {

					}
				}
			}
		};
		EventHandler<ActionEvent> EconomyReserve = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (main.getChildren().contains(move)) {
				} else {
					if (air.seating[air.TotalSeats / 2 - 1] == false || air.seating[air.TotalSeats - 1] == false) {
						air.ReserveSeat(2);
						if (air.found == false) {
							if (main.getChildren().contains(move)) {

							} else {
								main.add(move, 5, 8);
								main.add(yes, 5, 9);
								main.add(no, 5, 10);
							}
						}
						updateSeats();
					} else {

					}
				}
			}
		};

		// Event Handler to exit program
		EventHandler<ActionEvent> exitProgram = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		};
		
		ArrayList<String> fileAsArray = new ArrayList<String>();
		
		EventHandler<ActionEvent> findFlight = new EventHandler<ActionEvent>() {

			
			/*
			 * when button is clicked 
			 * convert document into an array
			 * compare the name in box to the names in the array
			 * if the name matches
			 * make a new flight object with name, amount if seats
			 * convert seats array string into an array and change objects seating array to correspond
			 */
			
			@Override
			public void handle(ActionEvent arg0) {
				try {
					while((line = reader.readLine()) != null) {
						fileAsArray.add(line);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				
				
			}
			
		};
		
		
		
		EventHandler<ActionEvent> finishOperation = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				
				printFlights();
				
				
				ArrayList<String> FileCopy = new ArrayList<String>();
				String ArrayHolder = "";
				
				//search and replace the flight names taken seats array (fffff)
				/*Copy every line into a string array with each line being its own index
				 * split each index and compare index 0 to flightnamefield
				 * if the names match, read seating[]
				 * change true to t and false to f
				 * change index[1] to new string of t's and f's 
				 * update file
				 */
				
//				for(int i = 0; i < air.seating.length; i++) {
//					if(air.seating[i] == true) {
//						writer.append("t");
//					}else {
//						writer.append("f");
//					}
//				}
				
				try {
					createReader();
					
					while ((line = reader.readLine()) != null) {
						FileCopy.add(line);
						
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println(FileCopy.size());
				for(int i = 0; i < FileCopy.size(); i++) {
					System.out.println("starting for loop");
					if(i % 2 == 0) {
						System.out.println("Splitting " + FileCopy.get(i));
					splitNameAndArray = FileCopy.get(i).split(" ");
					
					
					
						if(splitNameAndArray[0].equals(FlightNameField.getText())) {
							System.out.println("Names are equal");
							System.out.println(air.seating.length);
							for(int l = 0; l < air.seating.length; l++) {
								System.out.println("Building array holder");
								System.out.println(l + " of " + (air.seating.length - 1));
									if(air.seating[l] == true) {
										System.out.println("t");
										ArrayHolder += "t";
									}else {
										System.out.println("f");
										ArrayHolder += "f";
									}
							}
							System.out.println("Swapping elements");
							System.out.println(splitNameAndArray[1] + " --> " + ArrayHolder);
							splitNameAndArray[1] = ArrayHolder;
							
							FileCopy.set(i, splitNameAndArray[0] + " " + splitNameAndArray[1]);
						}
					}
				}
				
				
				
				FileName.delete();
				System.out.println("File deleted");
				try {
					FileName.createNewFile();
					System.out.println("File created");
					
					
				
				
					
					clearWriter();
					createWriter();
					System.out.println("Writer created");
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				try {
					
					
					for(int j = 0; j < FileCopy.size(); j++) {
						System.out.println("Starting to write");
						writer.append(FileCopy.get(j));
						writer.newLine();
					}
					System.out.println("Writing finished");
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				for(String s : FileCopy) {
					System.out.println("---" + s);
				}
				
				
				clearLabels();
				main.getChildren().remove(FirstClassPlaceholder);
				main.getChildren().remove(EconomyPlaceholder);
				printFlights();
			}
		};

		// set each buttons on click to its event handler
		ReserveFirst.setOnAction(FirstClassReserve);
		ReserveEconomy.setOnAction(EconomyReserve);
		close.setOnAction(exitProgram);
		yes.setOnAction(moveYes);
		no.setOnAction(moveNo);
		submit.setOnAction(submitAirline);
		finished.setOnAction(finishOperation);
		NumberOfSeatsField.setOnMouseClicked(e -> {
			NumberOfSeatsField.selectAll();
		});

		// add scene controls to the grid

		main.add(ReserveFirst, 5, 5);
		main.add(ReserveEconomy, 5, 6);
		main.add(close, 15, 15);
		main.add(FlightNameLabel, 10, 7);
		main.add(FlightNameField, 10, 8);
		main.add(NumberOfSeatsLabel, 10, 9);
		main.add(NumberOfSeatsField, 10, 10);
		main.add(submit, 10, 11);
		main.add(finished, 15, 14);

		// Create a new scene and show primary stage
		Scene scene = new Scene(main, 600, 500);

		PrimaryStage.setResizable(true);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}// end

	String seat;
	boolean airline;
	Label[] labels;
	Label FlightName;

	// used to allocate labels array when object air is created
	public void createLabels() {
		labels = new Label[air.TotalSeats];
	}

	public void clearLabels() {
		for (int l = 0; l < labels.length; l++) {
			labels[l].setText("");
		}
		FlightName.setText("");
	}

	// used to create the name of the flight label and add it to the grid when air
	// is constructed
	public void createName() {
		FlightName = new Label(air.getFlightName());
		main.add(FlightName, 0, 0);
	}
	
	public void createWriter() {
		try {
			fileWriter = new FileWriter(FileName, true);
			writer = new BufferedWriter(fileWriter);
		}catch(Exception e) {
			e.printStackTrace();
			}
	}
	
		public void createReader() {
			try {
				fileReader = new FileReader(FileName);
				reader = new BufferedReader(fileReader);
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
		
		public void clearReader() {
			try {
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void clearWriter() {
			try {
				writer.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	// get all seats and print them out as labels
	public Label seatsToLabel(int i) {

		try {
			airline = air.seating[i];
			if (airline == false) {
				seat = "Open";
			} else {
				seat = "Taken";
			}
			labels[i] = new Label(seat);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return labels[i];
	}// end seatsToLabel

	// reset every labels text to corresponding availability
	public void updateSeats() {
		for (int j = 0; j < air.seating.length; j++) {
			airline = air.seating[j];
			if (airline == false) {
				seat = "Open";
			} else {
				seat = "Taken";
			}
			labels[j].setText(seat);

		}
	}// end updateSeats

	
	
	public void printFlights() {
		try {

			while ((line = reader.readLine()) != null) {

				if (read) {
					
					splitNameAndArray = line.split(" ");
//					for(String s : splitNameAndSeats) {
//						System.out.println(" ..." + s);
//					}
						listName.add(splitNameAndArray[0]);
						listTaken.add(splitNameAndArray[1]);
					
					//listName.add(line);
					// System.out.println(line);
					read = false;
				} else {
					listSeats.add(line);
					// System.out.println(line);
					read = true;
				}

			}
			for (int i = 0; i < listName.size(); i++) {
				System.out.println(listName.get(i) + " : " + listTaken.get(i) + " : " + listSeats.get(i));
			}

			System.out.println("------------------\n\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * MAIN METHOD
	 * 
	 */

	public static void main(String[] args) {
		launch(args);
	}

}
