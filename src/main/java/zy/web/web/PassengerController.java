package zy.web.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import zy.web.entity.Passenger;
import zy.web.service.PassengerService;
import zy.web.util.PassengerUtil;


@RestController
public class PassengerController {
	private static final Logger LOGGER = Logger.getLogger(PassengerController.class);
	static {
		ClassLoader loader = PassengerController.class.getClassLoader();
		URL resource = loader.getResource("config/log4j.properties");
        PropertyConfigurator.configure(resource);
	}
	@Autowired
	private PassengerService passengerService;
	
    @GetMapping("/refresh")
    public List<Passenger> refresh() {
        return passengerService.getPassengers();
    }
    
    @GetMapping("/deleteRow/{idStr}")
    public ResponseEntity<String> deleteRow(@PathVariable String idStr) {
    	LOGGER.debug("deleteRow" + idStr);
    	int id = 0;
    	try {
    		id = Integer.valueOf(idStr);
    	} catch (NumberFormatException e) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	passengerService.delete(id);
    	return new ResponseEntity<>("success",HttpStatus.OK);
    }
    
    @GetMapping("/deleteAll")
    public String deleteAll() {
    	passengerService.deleteAll();
    	return "delete sucess";
    }
    
    @PostMapping("/updateRow")
    public ResponseEntity<String> updateRow(@RequestBody Passenger passenger) {
    	LOGGER.debug(passenger);
    	if (passengerService.updatePassenger(passenger) ) {
    		return new ResponseEntity<>("success",HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/updateRow/{idStr}")
    public ResponseEntity<String> updateRowWithId(@RequestBody Passenger passenger, @PathVariable String idStr) {
    	int id = 0;
    	try {
    		id = Integer.valueOf(idStr);
    	} catch (NumberFormatException e) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	if (passengerService.updatePassenger(passenger, id) ) {
    		return new ResponseEntity<>("success",HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/addRow")
    public ResponseEntity<String> addRow(@RequestBody Passenger passenger) {
    	LOGGER.debug(passenger);
    	if (passengerService.addPassenger(passenger) ) {
    		return new ResponseEntity<>("success",HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/upload")
    public List<Passenger> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        LOGGER.info("upload success!");
//        String fileName = file.getOriginalFilename();
//        String filePath = "";
//        File dest = new File(filePath + fileName);
        try {
//            file.transferTo(dest);
        	BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<Passenger> passengers = PassengerUtil.parse(br);
            LOGGER.debug(passengers.size());
            passengerService.addPassengers(passengers);
            return passengerService.getPassengers();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return null;
    }
	

	
}
