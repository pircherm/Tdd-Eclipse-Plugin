package at.ac.tuwien.ifs.qse.tddsample;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.ifs.qse.tddsample.exception.SummandNullExc;

public class TestCalculator1 {

	@Test
	public void testCalculator(){
		Calculator1 calc = new Calculator1();
		try {
			Assert.assertTrue(calc.add(2, 4) == 6);
		} catch (SummandNullExc e) {
			Assert.fail("Illegal Exception was thrown:" + e.getMessage());
		}
	}
	
	
}
