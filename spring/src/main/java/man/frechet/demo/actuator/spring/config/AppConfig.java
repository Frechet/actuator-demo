package man.frechet.demo.actuator.spring.config;

import man.frechet.demo.actuator.spring.filter.RequestTraceFilter;
import man.frechet.demo.actuator.spring.jmxresource.TraceCountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.endpoint.TraceEndpoint;
import org.springframework.boot.actuate.endpoint.jmx.EndpointMBeanExporter;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.management.MBeanServer;

@ComponentScan(basePackages = {"man.frechet.demo.actuator.spring.controller"})
//@Import(value = {EndpointAutoConfiguration.class})
@EnableWebMvc
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private static TraceRepository traceRepository = new InMemoryTraceRepository();

    @Autowired
    private static TraceCountResource traceCountResource = new TraceCountResource();

    @Bean
    public EndpointMBeanExporter endpointMBeanExporter(MBeanServer server) {
        EndpointMBeanExporter mbeanExporter = new EndpointMBeanExporter();

        String domain = "man.frechet.demo.actuator";
        if (StringUtils.hasText(domain)) {
            mbeanExporter.setDomain(domain);
        }
        mbeanExporter.setServer(server);
        mbeanExporter.setEnsureUniqueRuntimeObjectNames(false);
//        mbeanExporter.addExcludedBean();

        return mbeanExporter;
    }

    @Bean
    public MBeanServer mbeanServer() {
        return new JmxAutoConfiguration().mbeanServer();
    }

//    @Bean
//    public static TraceRepository traceRepository() {
//        return new InMemoryTraceRepository();
//    }
//
//    @Bean
//    public static TraceCountResource traceCountResource() {
//        return new TraceCountResource();
//    }

    @Bean
    public static RequestTraceFilter requestTraceFilter() {
        return new RequestTraceFilter(traceRepository, traceCountResource);
    }

    @Bean
    public TraceEndpoint traceEndpoint() {
        return new TraceEndpoint(traceRepository);
    }
}
