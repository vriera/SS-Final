package ar.edu.itba;

import ar.edu.itba.models.Car;

import java.util.*;

public class CarPool {

    private Queue<Car> freeCars;
    private Set<Car> activeCars;

    private Set<Car> deactivatedCars;



    public CarPool(int maxCars) {
        this.freeCars = new LinkedList<>();
        this.activeCars = new HashSet<>();
        this.deactivatedCars = new HashSet<>();
        for (int i = 0; i <= maxCars; i++) {
            freeCars.add(new Car(i));
        }
    }

    public synchronized Car getFreeCar(){
        if(freeCars.size() == 0)
            return null;
        Car car = freeCars.poll();
        activeCars.add(car);
        return car;
    }

    public synchronized void removeCar(Car car){
       car.deactivate();
       activeCars.remove(car);
       deactivatedCars.add(car);
    }

    public Set<Car> getDeactivatedCars() {
        return deactivatedCars;
    }

    public synchronized Set<Car> activeCars(){
        return this.activeCars;
    }



}
