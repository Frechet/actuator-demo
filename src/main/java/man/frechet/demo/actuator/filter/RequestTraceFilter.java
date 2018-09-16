package man.frechet.demo.actuator.filter;

import man.frechet.demo.actuator.jmxresource.TraceCountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.TraceProperties;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.actuate.trace.WebRequestTraceFilter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@ManagedResource
@Component
public class RequestTraceFilter extends WebRequestTraceFilter {

    private final String[] excludedEndpoints = new String[] {"/css/**", "/js/**", "/traceCounter"};

    /**
     * Custom statistic. Count of requests to urls.
     */
    private final TraceCountResource traceCounter;

    @Autowired
    public RequestTraceFilter(TraceRepository repository, TraceProperties properties, TraceCountResource traceCounter) {
        super(repository, properties);
        this.traceCounter = traceCounter;
    }

    /**
     * Exclude specific resource from trace statistic.
     */
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        return Arrays.stream(excludedEndpoints).anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }

    /**
     * Use for create custom monitoring statistics.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        super.doFilterInternal(request, response, filterChain);

        Map<String, Long> counter = traceCounter.getCount();
        String uri = request.getRequestURI();

        counter.putIfAbsent(uri, 0L);
        counter.computeIfPresent(uri, (k, v) -> v + 1);
    }
}
