package man.frechet.demo.actuator.jmxresource;


import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ManagedResource
@Component
public class TraceCountResource {

    private Map<String, Long> count = new HashMap<>();

    @ManagedOperation
    public Map<String, Long> getCount() {
        return count;
    }
}
