package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entity.Car;
import tn.esprit.spring.service.ICar;
import java.io.Serializable;
import java.util.List;

@RestController
public class CarController implements Serializable {

    @Autowired
    ICar iCar;

    // http://localhost:8080/retrieve-all-cars
    @GetMapping("/retrieve-all-cars")
    @ResponseBody
    public List<Car> getClients() {
        List<Car> list = iCar.retrieveAllCars();
        return list;
    }

    // http://localhost:8080/getCarById/1
    @GetMapping(value = "getCarById/{car-id}")
    @ResponseBody
    public Car getCarById(@PathVariable("car-id") int carId) {

        return iCar.getCarById(carId);
    }

    // http://localhost:8080/update-car/
    @PutMapping("/update-car")
    @ResponseBody
    public Car modifyClient(@RequestBody Car c) {
        return iCar.UpdateCar(c);
    }

    // http://localhost:8080/remove-client/{car-id}
    @DeleteMapping("/remove-car/{car-id}")
    @ResponseBody
    public void removeCar(@PathVariable("car-id") int idCar) {
        iCar.DeleteCar(idCar);
    }

    // http://localhost:8080/getAllCarModel
    @GetMapping(value = "getAllCarModel")
    @ResponseBody
    public List<String> getAllCarModel() {

        return iCar.getAllCarModel();
    }
    // http://localhost:8080/getAscPrice
    @GetMapping(value = "getAscPrice")
    @ResponseBody
    public List<String> getAscPrice() {
        return iCar.getAscPrice();
    }

    // http://localhost:8080/getDecPrice
    @GetMapping(value = "getDecPrice")
    @ResponseBody
    public List<String> getDecPrice() {
        return iCar.getDecPrice();
    }

    // http://localhost:8080/getCarByModel
    @GetMapping("/getAverageRating")
    public List<String> getAverageRating() {
        return iCar.getAverageRating();
    }

    // http://localhost:8080/getotalprice
    @GetMapping("/getotalprice")
    public List<String> getotalprice() {
        return iCar.gettotalPrice();
    }

    // http://localhost:8080/getRentCar
    @GetMapping("/getRentCar")
    public List<String> getRentCar() {
        return iCar.getRentCar();
    }

    // http://localhost:8080/getNotForRentCar
    @GetMapping("/getNotForRentCar")
    public List<String> getNotForRentCar() {
        return iCar.getNotForRentCar();
    }

    // http://localhost:8080/add-car
    @PostMapping("/add-car")
    @ResponseBody
    public Car addCar(@RequestBody Car car) {
        return iCar.AddCar(car);
    }

}
