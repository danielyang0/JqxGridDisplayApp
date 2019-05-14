package zy.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import zy.web.dao.PassengerRepository;
import zy.web.entity.Passenger;
import zy.web.service.PassengerService;

public class PassengerServiceTests extends TableApplicationTests{
	@Autowired
	private PassengerService ps;
	
	@Autowired
	private PassengerRepository repo;
	
	private Passenger examplePassenger;
	private int maxId = 0;//original max Id in database
	private Set<Integer> addedIds = new HashSet<>();//the ids added to DB during Unit tests
	@Override
	public void init() {
		super.init();
		//prepare exmaple data
		examplePassenger = new Passenger();
		examplePassenger.setAge("66");
		examplePassenger.setFare("123.22");
		examplePassenger.setName("Nathan");
		
		List<Passenger> passengers = ps.getPassengers();
		
		for (Passenger passenger : passengers) {
			maxId = Math.max(maxId, passenger.getPassengerId());
		}
	}
	
	
	@Test
	public void testAddPassenger() {
		int id = maxId;
		examplePassenger.setPassengerId(id);
		boolean suc = ps.addPassenger(examplePassenger);
		if (suc) {
			addedIds.add(id);
		}
		Assert.assertEquals(false, suc);
	}
	
	
	
	@Test
	public void testAddPassenger2() {
		int id = maxId + 1;
		examplePassenger.setPassengerId(id);
		boolean suc = ps.addPassenger(examplePassenger);
		if (suc) {
			addedIds.add(id);
		}
		Assert.assertEquals(true, suc);
	}

	
	@Test
	public void testGetAllPassengers() {
		List<Passenger> passengers = ps.getPassengers();
		int maxIdInDB = 0;
		for (Passenger passenger : passengers) {
			maxIdInDB = Math.max(maxIdInDB, passenger.getPassengerId());
		}
		Assert.assertEquals(getCalculatedMaxId(), maxIdInDB);
	}
	
	@Test
	public void testUpdatePassenger() {
		int id = getCalculatedMaxId() + 1;
		examplePassenger.setPassengerId(id);
		boolean suc = ps.updatePassenger(examplePassenger);
		Assert.assertEquals(false, suc);
	}
	
	@Test
	public void testUpdatePassenger2() {
		int id = getCalculatedMaxId();
		Passenger originalPassenger = repo.findOne(id);
		examplePassenger.setPassengerId(id);
		boolean suc = ps.updatePassenger(examplePassenger);
		Assert.assertEquals(true, suc);
		repo.save(originalPassenger);
	}
	
	@Test
	public void testUpdatePassengerWithId() {
		int id = maxId;
		Passenger p = repo.findOne(id);
		int oringalId = p.getPassengerId();
		p.setPassengerId(1000);
		boolean suc = ps.updatePassenger(p, oringalId);
		Assert.assertEquals(true, suc);
		//recover
		p.setPassengerId(oringalId);
		ps.updatePassenger(p, 1000);
	}
	
	@Test
	public void testUpdatePassengerWithId2() {
		int id = maxId;
		Passenger p = repo.findOne(id);
		int oringalId = p.getPassengerId();
		p.setPassengerId(1);
		boolean suc = ps.updatePassenger(p, oringalId);
		Assert.assertEquals(false, suc);
	}
	
	@Test
	public void testDeletePassenger() {
		int id = 1;
		Passenger p = repo.findOne(id);
		boolean suc = ps.delete(p.getPassengerId());
		Assert.assertEquals(true, suc);
		//recover
		repo.save(p);
	}
	
	@Test
	public void validityCheck() {
		Passenger p = new Passenger();
		p.setAge("fds");
		p.setPassengerId(12);
		Assert.assertTrue(!ps.validityCheck(p));
		
		p.setAge("123");
		Assert.assertTrue(ps.validityCheck(p));
		
		p.setSex("afdfd");
		Assert.assertTrue(!ps.validityCheck(p));
	}
	
	/**
	 * recover the orginal state of DB
	 */
	@Override
	public void after() {
		super.after();
		for (int id : addedIds) {
			repo.delete(id);
		}
	}
	
	private int getCalculatedMaxId() {
		int calculatedMaxId = maxId;
		for (int id : addedIds) {
			calculatedMaxId = Math.max(calculatedMaxId, id);
		}
		return calculatedMaxId;
	}
}
