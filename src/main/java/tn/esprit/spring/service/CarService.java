package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entity.Car;
import tn.esprit.spring.entity.Company;
import tn.esprit.spring.repository.CarRepository;
import java.util.Date;
import java.util.List;

@Service
public class CarService implements ICar {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> retrieveAllCars() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return cars;
    }

    @Override
    public Car getCarById(int id) {
        return carRepository.findById(id).get();
    }

    @Override
    public Car UpdateCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void DeleteCar(int id) {
        this.carRepository.deleteById(id);
    }

    public List<String> getAllCarModel() {
        return carRepository.carModel();
    }

    @Override
    public List<String> getAscPrice() {
        return carRepository.Ascprice();
    }

    @Override
    public List<String> getDecPrice() {
        return carRepository.Desprices();
    }

    @Override
    public List<String> getAverageRating() {
        return carRepository.getAverageRating();
    }

    @Override
    public List<String> gettotalPrice() {
        return carRepository.getotalprice();
    }

    @Override
    public List<String> getRentCar() {
        return carRepository.getRentCar();
    }

    @Override
    public List<String> getNotForRentCar() {
        return carRepository.getNotForRentCar();
    }

    @Override
    public Car AddCar(Car car) {
        Date s = car.getStartDateLocation();
        Date e = car.getEndDateLocation();
        if ((s == null) || (e == null)) {
            car.setStartDateLocation(null);
            car.setEndDateLocation(null);
            return carRepository.save(car);
        } else if (s.before(e)) {
            float diff = e.getTime() - s.getTime();
            float res = ((diff) / (1000 * 60 * 60 * 24));
            car.setDay(res);
        }
        return carRepository.save(car);
    }

}
