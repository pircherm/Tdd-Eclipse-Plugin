package at.ac.tuwien.ifs.qse.tddsample;

import at.ac.tuwien.ifs.qse.tddsample.exception.SummandNullExc;

public class Calculator1 {


	public Integer add(Integer i1,Integer i2) throws SummandNullExc{
		System.out.println("Call add function!");
		
		if(i1 == null || i2 == null)
			throw new SummandNullExc();
		
		return  i1+i2;
	}
}
