package alpv.mwp.testJohni;

import java.util.List;

import alpv.mwp.Task;

public class TaskAdd implements Task<List<Integer>,Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer exec(List<Integer> numbers) {
		Integer i = new Integer(0);
		for (Integer number : numbers){
			i = Integer.valueOf(i.intValue() + number.intValue());
		}
		return i;
	}
}
