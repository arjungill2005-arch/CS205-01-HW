package edu.monmouth.vehicle;

public class Boat implements Vehicle {

    private int boatLength;
    private PowerTypes powerSource;

    public Boat(PowerTypes powerSource, int boatLength) throws BoatException {
        this.setBoatLength(boatLength);
        this.setPowerSource(powerSource);
    }

    public int getBoatLength() {
        return boatLength;
    }

    public void setBoatLength(int boatLength) throws BoatException {
        if (boatLength < VehicleConstants.MINEBOATLENGTH || boatLength > VehicleConstants.MAXBOATLENGTH) {
            throw new BoatException("Invalid boat size");
        }
        this.boatLength = boatLength;
    }

    @Override
    public void go() {
        System.out.println("Boat sails");
    }

    @Override
    public void stop() {
        System.out.println("Boat anchors");
    }

    @Override
    public PowerTypes powerBy() {
        return null;
    }

    public PowerTypes getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerTypes powerSource) {
        this.powerSource = powerSource;
    }

    @Override
    public String toString() {
        return "Boat - Length: " + boatLength + ", Power Source: " + powerSource;
    }

    @Override
    public boolean equals(Object other) {
        // check other is a Boat type
        if (!(other instanceof Boat)) {
           return false;
        }
        // cast other into Boat object
        Boat otherBoat = (Boat) other;
        return this.powerSource == otherBoat.powerSource && this.boatLength == otherBoat.boatLength;
    }

}
