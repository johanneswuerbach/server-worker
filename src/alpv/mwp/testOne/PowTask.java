package alpv.mwp.testOne;


import alpv.mwp.Task;

public class PowTask implements Task<Integer,Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer exec(Integer number) {
		return new Integer(number*2);
	}
}
