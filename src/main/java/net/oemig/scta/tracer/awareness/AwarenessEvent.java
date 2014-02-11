package net.oemig.scta.tracer.awareness;

import java.io.Serializable;

import net.oemig.scta.model.data.UserName;

public final class AwarenessEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6584316481256561948L;

	public static AwarenessEvent of(UserName aUserName, String aLetter, int aCountResult){
		return new AwarenessEvent(aUserName, aLetter, aCountResult);
	}

	private final UserName userName;
	private final String letter;
	private final int countResult;
	
	private AwarenessEvent(UserName aUserName, String aLetter, int aCountResult){
		userName=aUserName;
		letter=aLetter;
		countResult=aCountResult;
	}

	public UserName getUserName() {
		return userName;
	}

	public String getLetter() {
		return letter;
	}

	public int getCountResult() {
		return countResult;
	}
	
	

}
