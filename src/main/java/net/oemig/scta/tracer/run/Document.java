package net.oemig.scta.tracer.run;

import java.io.Serializable;

import net.oemig.scta.tracer.configuration.IConfiguration;

import org.apache.commons.lang.RandomStringUtils;

public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9085467738084870743L;

	public static Document getInstance(IConfiguration newConfiguration) {
		return new Document(newConfiguration);
	}

	private StringBuffer sbuffer;

	private Document(IConfiguration configuration) {
		// TODO: use configuration for number of words and letters per word
		// this is the document generator
		this.sbuffer = new StringBuffer();
		for (int i = 0; i < 450; i++) {

			sbuffer.append(RandomStringUtils.random(5, true, false));
			sbuffer.append(" ");
			if (i % 10 == 0) {
				sbuffer.append("\n");
			}
		}

		sbuffer.append(".");

	}

	public String toString() {
		return this.sbuffer.toString();
	}
}
