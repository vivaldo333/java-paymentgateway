package com.finaro.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.finaro.constant.Constants.Http.NON_TRACE_PATHS;
import static com.finaro.constant.Constants.Http.TRACE_HEADER_ID;

@Component
@Order(2)
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var traceId = getOrGenerateHeader(TRACE_HEADER_ID, request);
            MDC.put(TRACE_HEADER_ID, traceId);
            response.setHeader(TRACE_HEADER_ID, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(TRACE_HEADER_ID);
        }
    }

    private String getOrGenerateHeader(String headerName, HttpServletRequest request) {
        var headerValue = request.getHeader(headerName);
        return StringUtils.isNotEmpty(headerName)
                ? headerValue
                : UUID.randomUUID().toString();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return NON_TRACE_PATHS.contains(request.getServletPath());
    }

    /*
      @deprecated (2022.08.15, used for trow exception if mandatory headers (X-Trace-Id) are not present, for removal)
     */
    /*private void respondWithProblem(HttpServletResponse response, String errorMessage)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(APPLICATION_PROBLEM_JSON_VALUE);
        response
                .getWriter()
                .write(objectMapper.writeValueAsString(new RunTimeException(errorMessage)));
        response.getWriter().close();
    }*/
}
