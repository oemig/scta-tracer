package net.oemig.scta.tracer.data;

import java.io.Serializable;

/**
 * {@link UserName} class added for type safety reasons.
 * 
 * @author oemig
 *
 */
public final class UserName implements Serializable {
	
	private static final long serialVersionUID = 2785306846644253244L;

	public static UserName of(final String aName){
		return new UserName(aName);
	}

	private String userName;
	
	private UserName(final String aName){
		userName=aName;
	}
	
	public String toString(){
		return userName;
	}

}
