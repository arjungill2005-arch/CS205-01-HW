package edu.monmouth.vehicle;

public class Plane implements Vehicle {

    private PowerTypes powerSource;

    public Plane(PowerTypes powerSource) {
        this.setPowerSource(powerSource);
    }

    private void setPowerSource(PowerTypes powerSource) {
        this.powerSource = powerSource;
    }

    public PowerTypes getSetPowerSource() {
        return powerSource;
    }

    @Override
    public void go() {
        System.out.println("Plane takes off");
    }

    @Override
    public void stop() {
        System.out.println("Plane lands");
    }

    @Override
    public PowerTypes powerBy() {
        return powerSource;
    }

    @Override
    public String toString() {
        return "Plane - Power Source: " + powerSource;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Plane )) {
            return false;
        }

        Plane otherPlane = (Plane) other;
        return this.powerSource == otherPlane.powerSource;
    }
}