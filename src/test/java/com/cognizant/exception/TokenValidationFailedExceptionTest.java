package com.cognizant.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TokenValidationFailedExceptionTest {

	private TokenValidationFailedException e = new TokenValidationFailedException("message");

	@Test
	void testMessageSetter() {
		assertThat(e).isNotNull();
	}

}
