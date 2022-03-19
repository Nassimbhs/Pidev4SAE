package tn.esprit.spring.service;

import tn.esprit.spring.entity.Car;
import tn.esprit.spring.entity.Company;
import tn.esprit.spring.entity.Employee;

import java.util.List;

public interface ICar {

    List<Car> retrieveAllCars();
    Car getCarById(int id);
    Car UpdateCar(Car car);
    void DeleteCar(int id);
    List<String> getAllCarModel();
    List<String> getAscPrice();
    List<String> getDecPrice();
    List<String> getAverageRating();
    List<String> gettotalPrice();
    List<String> getRentCar();
    List<String> getNotForRentCar();
    Car AddCar(Car car);
}
