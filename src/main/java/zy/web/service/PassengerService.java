package zy.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zy.web.dao.PassengerRepository;
import zy.web.entity.Passenger;


@Service
public class PassengerService {
	
	@Autowired
	private PassengerRepository passengerRepository;

	/**
	 * add multiple passengers to the table
	 * @param passengers
	 */
	public void addPassengers(List<Passenger> passengers) {
		passengerRepository.save(passengers);
	}
	
	/**
	 * get all passengers from the table
	 * @return
	 */
	public List<Passenger> getPassengers() {
		List<Passenger> res = new ArrayList<>();
		passengerRepository.findAll().forEach(res::add);
		return res;
	}

	/**
	 * update an existing passenger
	 * @param passenger
	 * @return
	 */
	public boolean updatePassenger(Passenger passenger) {
		if (!validityCheck(passenger)) {
			return false;
		}
		if (!passengerRepository.exists(passenger.getPassengerId())) {
			return false;
		}
		passengerRepository.save(passenger);
		return true;
	}
	
	/**
	 * update an existing passenger with its id updated only if the new id doesn't conflict with other rows
	 * @param passenger
	 * @param id
	 * @return
	 */
	public boolean updatePassenger(Passenger passenger, int id) {
		if (!passengerRepository.exists(id)) {
			return false;
		}
		int newId = passenger.getPassengerId();
		if (passengerRepository.exists(newId)) {
			return false;
		}
		if (!validityCheck(passenger)) {
			return false;
		}
		passengerRepository.delete(id);//delete the old one
		passengerRepository.save(passenger);
		return true;
	}
	
	/**
	 * add a new passenger into the table
	 * @param passenger
	 * @return
	 */
	public boolean addPassenger(Passenger passenger) {
		if (!validityCheck(passenger)) {
			return false;
		}
		if (passengerRepository.exists(passenger.getPassengerId())) {
			return false;
		}
		passengerRepository.save(passenger);
		return true;
	}
	

	/**
	 * delete one passenger record with the id
	 * @param id
	 */
	public boolean delete(Integer id) {
		if (id == null) {
			return false;
		}
		passengerRepository.delete(id);
		return true;
	}

	/**
	 * delete all data in the passenger table
	 */
	public void deleteAll() {
		passengerRepository.deleteAll();
	}
	
	
	/**
	 * check if the passenger object is a valid object to insert into passenger table
	 * @param p
	 * @return
	 */
	public boolean validityCheck(Passenger p) {
		if (p == null) {
			return false;
		}
		if (p.getSex() != null && !p.getSex().equals("") && !p.getSex().equals("male") && !p.getSex().equals("female")) {
			return false;
		}
		try {
			if (p.getSurvived() != null && !p.getSurvived().isEmpty()) {
				if (Integer.valueOf(p.getSurvived()) < 0) {
					return false;
				}
			}
			if (p.getPclass() != null && !p.getPclass().isEmpty()) {
				if (Integer.valueOf(p.getPclass()) <= 0) {
					return false;
				}
			}
			if (p.getAge() != null && !p.getAge().isEmpty()) {
				int age = Integer.valueOf(p.getAge());
				if (age > 200 || age < 0) {
					return false;
				}
			}
			if (p.getSibSp() != null && !p.getSibSp().isEmpty()) {
				int sibSp = Integer.valueOf(p.getSibSp());
				if (sibSp < 0) {
					return false;
				}
			}
			if (p.getParch() != null && !p.getParch().isEmpty()) {
				int parch = Integer.valueOf(p.getParch());
				if (parch < 0) {
					return false;
				}
			}
			if (p.getFare() != null && !p.getFare().isEmpty()) {
				double fare = Double.valueOf(p.getFare());
				if (fare < 0) {
					return false;
				}
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}





}
