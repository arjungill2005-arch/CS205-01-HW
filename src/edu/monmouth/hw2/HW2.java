package edu.monmouth.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

					// validating the right amount of tokens given
					if(tokens.length != 3) {
						System.err.println("Invalid vehicle line parsed");
						continue;
					}

					String vehicleType = tokens[0];
					String powerSource = tokens[1];
					String number = tokens[2];

					// validating the vehicle type token
					if(!vehicleType.equals("c") && !vehicleType.equals("b")) {
						System.err.println("Invalid vehicle type token");
						continue;
					}

					// validating powerSource token
					PowerTypes pt = PowerTypes.valueOf(powerSource); // throws IllegalArgumentException when invalid

					// create a specific vehicle
					Vehicle v;

					if (vehicleType.equals("c")) {
						// validating the engineSize
						int engineSize  = Integer.parseInt(number);
						v = new Car(pt, engineSize);
					} else { // "b" case
						int boatLength = Integer.parseInt(number);
						v = new Boat(pt, boatLength);
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

    }
}
