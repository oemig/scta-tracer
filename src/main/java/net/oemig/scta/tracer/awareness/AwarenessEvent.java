package net.oemig.scta.tracer.awareness;

import java.io.Serializable;

import net.oemig.scta.model.data.UserName;

public final class AwarenessEvent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6584316481256561948L;

	public static AwarenessEvent of(UserName aUserName, String aLetter, String aMessage){
		return new AwarenessEvent(aUserName, aLetter, aMessage);
	}

	private final UserName userName;
	private final String letter;
	private final String message;
	
	private AwarenessEvent(UserName aUserName, String aLetter, String aMessage){
		userName=aUserName;
		letter=aLetter;
		message=aMessage;
	}

	public UserName getUserName() {
		return userName;
	}

	public String getLetter() {
		return letter;
	}

	public String getMessage() {
		return message;
	}
	
	

}
