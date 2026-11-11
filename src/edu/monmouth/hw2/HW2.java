package edu.monmouth.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.ListIterator;

import edu.monmouth.vehicle.*;

public class HW2 {
	static final int REQUIREDARGS=3, BADARGS=1, BADREDIRECT=2, BADSTRINGSFILE=3;

	public static void initializeOutStream(String fileName) {

		// redirect STDOUT & STDERR to file name given in args[0]
		try {
			PrintStream redirectStream = new PrintStream(fileName);
			System.setOut(redirectStream);
			System.setErr(redirectStream);
		} catch(FileNotFoundException ioe) {
			System.err.println("Cannot redirect stderr and stdout " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(BADREDIRECT);
		}
	}


	public static List<String> loadStrings(String fileName) {
		List<String> strings = new ArrayList<>();

		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);

			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				strings.add(line);
			}

		} catch (FileNotFoundException ioe) {
			System.err.println("Cannot open Strings file " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(BADSTRINGSFILE);
        } finally {
			// close sc only if it succeeded to open
			if (sc != null) {
				sc.close();
			}
		}

		return strings;
    }


	public static List<Vehicle> loadVehicles(String fileName) {
		List<Vehicle> vehicles = new ArrayList<>();

		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);

			while(sc.hasNextLine()) {
				try {
					String line = sc.nextLine();
					String[] tokens = line.split(",");

					if (tokens.length < 2)  {
						System.err.println("Invalid line - missing vehicle type");
						continue;
					}

					// if 1st if statement is false, check the
					String vehicleType = tokens[0];
					String powerSource = tokens[1];
					// validating powerSource token
					PowerTypes pt = PowerTypes.valueOf(powerSource); // throws IllegalArgumentException when invalid


					// create a specific vehicle
					Vehicle v;

					if (vehicleType.equals("c")) {
						// handle car case
						// validating the right amount of tokens given
						if(tokens.length != 3) {
							System.err.println("Invalid car line parsed");
							continue;
						}
						// validating the engineSize
						int engineSize  = Integer.parseInt(tokens[2]);
						v = new Car(pt, engineSize);
					}

					else if (vehicleType.equals("b")) {
						// handle boat case
						// validating the right amount of tokens given
						if(tokens.length != 3) {
							System.err.println("Invalid boat line parsed");
							continue;
						}
						int boatLength = Integer.parseInt(tokens[2]);
						v = new Boat(pt, boatLength);

//					} else if (vehicleType.equals("p")) {
//						// handle boat case
//						// validating the right amount of tokens given
//						if(tokens.length != 2) {
//							System.err.println("Invalid plane line parsed");
//							continue;
//						}
//						// Create new plane
//						v = new Plane(pt);
					} else {
						// handle bad case
						System.err.println("Invalid vehicle type token");
						continue;

					}
					vehicles.add(v);

				}  catch (NumberFormatException nfe) {
					System.err.println("Invalid Engine Size. Must be integer");
					nfe.printStackTrace();
				} catch (IllegalArgumentException iae) {
					System.err.println("Invalid power source token" + iae.getMessage());
					iae.printStackTrace();
				} catch (CarException ce) {
					System.out.println("Invalid Car Creation");
					ce.printStackTrace();
				} catch (BoatException be) {
					System.out.println("Invalid Boat Creation");
					be.printStackTrace();
				}
			}

		} catch (FileNotFoundException ioe) {
			System.err.println("Cannot open Vehicles file" + ioe.getMessage());
			ioe.printStackTrace();
        } finally {
			// close sc only if it succeeded to open
			if (sc != null) {
				sc.close();
			}
		}
		return vehicles;
	}

	public static void main(String[] args) {
		// check for 1 command line argument
		if(args.length != REQUIREDARGS) {
			System.err.println("Give the name of a log file");
			System.exit(BADARGS);
		}

		// part b
		initializeOutStream(args[0]);

		// part c
		List<String> strings = loadStrings(args[1]);

		// part d
		List<Vehicle> vehicles = loadVehicles(args[2]);

		System.out.println("Is strings empty?");
		System.out.println(strings.isEmpty());

		System.out.println("Is vehicles empty?");
		System.out.println(vehicles.isEmpty());

		strings.remove(3);
		System.out.println("After removing strings at index 3:");
		for(String s: strings) {
			System.out.println("\t" + s);
		}

		vehicles.remove(5);
		System.out.println("After removing vehicles at index 5");
		for (Vehicle v: vehicles) {
			System.out.println("\t" + v);
		}

		try {
			Car car1 = new Car(PowerTypes.FUELOIL, 6);
			Boat boat1 = new Boat(PowerTypes.ELECTRICITY, 80);

			System.out.println("Should contain " + car1);
			System.out.println(vehicles.contains(car1));
			System.out.println("Should not contain " + boat1);
			System.out.println(vehicles.contains(boat1));
		} catch (CarException | BoatException e) {
			// skip exception handling
        }

		System.out.println("Number of strings: " + strings.size());
		System.out.println("Number of Vehicles: " + vehicles.size());

		System.out.println("Forward Navigation - Strings List");
		ListIterator<String> si = strings.listIterator();
		while (si.hasNext()) {
			System.out.println(si.next());
		}

		System.out.println("Forward Navigation - Vehicle List");

		ListIterator<Vehicle> vi = vehicles.listIterator();

		while (vi.hasNext()) {
			System.out.println(vi.next());
		}

		System.out.println("Reverse Navigation - Strings List");
		ListIterator<String> sr = strings.listIterator(strings.size());
		while (sr.hasPrevious()) {
			System.out.println(sr.previous());
		}

		System.out.println("Reverse Navigation - Vehicle List");
		ListIterator<Vehicle> vr = vehicles.listIterator(vehicles.size());
		while (vr.hasPrevious()) {
			System.out.println(vr.previous());
		}

		try {
			Car carRemove = new Car(PowerTypes.FUELOIL, 6);
			System.out.println("Removing: " + carRemove);

			boolean removed = vehicles.remove(carRemove);
			System.out.println("Vehicle removed: " + removed);

			System.out.println("Vehicle list after removing");
			for (Vehicle v : vehicles) {
				System.out.println(v);
			}

		} catch (CarException e) {
			e.printStackTrace();
		}




	}
}
