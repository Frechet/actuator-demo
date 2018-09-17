package man.frechet.demo.actuator.spring.jmxresource;


import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.HashMap;
import java.util.Map;

@ManagedResource
public class TraceCountResource {

    private Map<String, Long> count = new HashMap<>();

    @ManagedOperation
    public Map<String, Long> getCount() {
        return count;
    }
}
