package com.target.retail.security;

/**
 * 
 * @author vsathvik
 *
 */
public final class SecurityConstants {

	private SecurityConstants() {
		
		throw new IllegalStateException("Utility class");

	}
	
	public static final String USER_NAME="user";
	public static final String PASSWORD="{noop}password";
	public static final String PRODUCTS_END_POINT="/products";
	public static final String PRODUCTS_ROLE="USER";
	
}
