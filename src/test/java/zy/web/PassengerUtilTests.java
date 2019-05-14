package zy.web;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import zy.web.entity.Passenger;
import zy.web.util.PassengerUtil;

public class PassengerUtilTests extends TableApplicationTests {

	@Test
	public void testParse() {
		String file = "/Users/xx/train.csv";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			List<Passenger> parsed = PassengerUtil.parse(br);
			System.out.println(parsed.size());
			Assert.assertEquals(891, parsed.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
