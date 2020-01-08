package com.target.myretail.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author vsathvik
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Currency {
	USD(0, "USD"), PARSER_FAILED(1, "PARSER_FAILED"), INR(2,
			"INR"), AUD(3, "AUD");

	private int id;

	private String name;

	/**
	 * @param id
	 * @param name
	 */
	private Currency(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the Id
	 */
	@JsonValue
	public int getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return
	 */
}
