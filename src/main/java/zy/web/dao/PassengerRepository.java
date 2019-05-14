package zy.web.dao;

import org.springframework.data.repository.CrudRepository;

import zy.web.entity.Passenger;


public interface PassengerRepository extends CrudRepository<Passenger, Integer> {

}
