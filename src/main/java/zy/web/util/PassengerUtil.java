package zy.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import zy.web.entity.Passenger;
import zy.web.web.PassengerController;

public class PassengerUtil {
	
	private static final Logger LOGGER = Logger.getLogger(PassengerController.class);
	static {
		ClassLoader loader = PassengerController.class.getClassLoader();
		URL resource = loader.getResource("config/log4j.properties");
        PropertyConfigurator.configure(resource);
	}
	
	/**
	 * parse the uploaded file to get a list of passenger objects.
	 * @param br
	 * @return
	 */
	public static List<Passenger> parse(BufferedReader br) {
		boolean isFirstLine = true;
		List<Passenger> passengers = new ArrayList<>();
		try {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if (isFirstLine) {
		    		isFirstLine = false;
		    		continue;
		    	}
		        int firstQuote = -1, lastQuote = -1;
		        for (int i = 0; i < line.length(); ++i) {
		        	if (line.charAt(i) == '\"') {
		        		if (firstQuote == -1) {
		        			firstQuote = i;
		        		}
		        		lastQuote = i;
		        	}
		        }
		        
		        String[] firstPart = line.substring(0, firstQuote).split(",");
		        String middlePart = line.substring(firstQuote + 1, lastQuote);
		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < middlePart.length(); ++i) {
		        	if (i == 0 ||  middlePart.charAt(i) != '\"' || middlePart.charAt(i-1) != '\"') {
		        		sb.append(middlePart.charAt(i));
		        	}
		        }
		        String name = sb.toString();
		        String[] lastPart = line.substring(lastQuote + 1).split(",");
		        if (firstPart.length < 3) {
		        	continue;
		        }
		        Passenger psg = new Passenger();
		        try {
		        	psg.setPassengerId(Integer.valueOf(firstPart[0]));
		        } catch (NumberFormatException e) {
		        	LOGGER.error("skip one line");
		        	continue;
		        }
		        psg.setSurvived(firstPart[1]);
		        psg.setPclass(firstPart[2]);
		        psg.setName(name);
		        psg.setSex(lastPart.length < 2? "":lastPart[1]);
		        psg.setAge(lastPart.length < 3? "":lastPart[2]);
		        psg.setSibSp(lastPart.length < 4? "":lastPart[3]);
		        psg.setParch(lastPart.length < 5? "":lastPart[4]);
		        psg.setTicket(lastPart.length < 6? "":lastPart[5]);
		        psg.setFare(lastPart.length < 7? "":lastPart[6]);
		        psg.setCabin(lastPart.length < 8? "":lastPart[7]);
		        psg.setEmbarked(lastPart.length < 9? "":lastPart[8]);
		        passengers.add(psg);
		    }
		} catch (IOException e) {
			LOGGER.error("parse Error");
			return new ArrayList<>();
		}
		return passengers;
	}
}
