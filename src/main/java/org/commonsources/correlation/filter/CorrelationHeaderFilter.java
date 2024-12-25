package org.commonsources.correlation.filter;

import org.commonsources.correlation.utils.Correlation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.commonsources.correlation.utils.Constants.CO_RELATION_ID_HEADER;
import static org.commonsources.correlation.utils.Constants.REQUEST_MARKER_MDC_KEY;


@Component
@Slf4j
public class CorrelationHeaderFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
        //no need to implement
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            String correlationHeaderValue = httpServletRequest.getHeader(CO_RELATION_ID_HEADER);
            Correlation.setCorrelationId(correlationHeaderValue);
            correlationHeaderValue =Correlation.getCorrelationId();
            httpServletResponse.addHeader(CO_RELATION_ID_HEADER, correlationHeaderValue);
            filterChain.doFilter(httpServletRequest, servletResponse);
        } finally {
            MDC.remove(REQUEST_MARKER_MDC_KEY);
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
