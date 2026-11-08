package edu.monmouth.vehicle;

public class Car implements Vehicle {

    private int engineSize;
    private PowerTypes powerSource;

    public Car(PowerTypes powerSource, int engineSize) throws CarException {
        this.setEngineSize(engineSize);
        this.powerSource = powerSource;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) throws CarException {
        if (engineSize < VehicleConstants.MINENGINESIZE || engineSize > VehicleConstants.MAXENGINESIZE) {
            throw new CarException("Invalid engine size");
        }
        this.engineSize = engineSize;
    }

    @Override
    public void go() {
        System.out.println("Car drives");
    }

    @Override
    public void stop() {
        System.out.println("Car stops");
    }

    @Override
    public PowerTypes powerBy() {
        return powerSource;
    }

    public PowerTypes getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerTypes powerSource) {
        this.powerSource = powerSource;
    }

    @Override
    public String toString() {
        return "Car - Engine Size: " + engineSize + ", Power Source: " + powerSource;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Car) ) {
            return false;
        }
        // cast
        Car otherCar = (Car) other;
        return this.powerSource == otherCar.powerSource && this.engineSize == otherCar.engineSize;
    }

}
