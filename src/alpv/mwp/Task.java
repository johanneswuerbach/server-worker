package alpv.mwp;

import java.io.Serializable;

public interface Task<Argument, Result> extends Serializable {

	/**
	 * call this on a worker with arguments stripped from the argpool and put
	 * the return value in the respool
	 * 
	 * @param a
	 * @return
	 */
	Result exec(Argument a);

}
