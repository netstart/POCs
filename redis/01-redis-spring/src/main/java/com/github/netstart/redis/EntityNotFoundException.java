package com.github.netstart.redis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5492320609551321691L;

	public EntityNotFoundException(final String message) {
        super(message);
    }
}