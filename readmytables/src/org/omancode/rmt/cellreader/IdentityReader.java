package org.omancode.rmt.cellreader;

/**
 * Returns the value read without any processing.
 * 
 * @author Oliver Mannion
 * @version $Revision$
 */
public class IdentityReader implements CellReader<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518453269224584967L;

	@Override
	public Class<Object> getResultType() {
		return Object.class;
	}

	@Override
	public Object call(Object value) {
		return value;
	}

}