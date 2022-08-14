package com.finaro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import java.io.Serial;
import java.net.URI;
import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractThrowableProblem {
    @Serial
    private static final long serialVersionUID = 382036532390212360L;

    public static final URI NOT_FOUND_EXCEPTION =
            URI.create("http://support.finaro.com/problem/object-not-found");

    public NotFoundException(String title) {
        this(title, null, null, null);
    }

    public NotFoundException(String title, String detail) {
        this(title, detail, null, null);
    }

    public NotFoundException(
            String title,
            String detail,
            ThrowableProblem cause,
            Map<String, Object> params
    ) {
        super(NOT_FOUND_EXCEPTION, title, Status.NOT_FOUND, detail, null, cause, params);
    }
}
