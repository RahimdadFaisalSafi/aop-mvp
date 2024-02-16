package software.potthoff.aopmvp;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.potthoff.aopmvp.aspect.MethodBuilder;
import software.potthoff.aopmvp.aspect.PerformanceLogging;

@Configuration
public class Config {
//    @Bean
//    public MethodBuilder methodBuilder() {
//
//        return Aspects.aspectOf(MethodBuilder.class);
//    }

    @Bean
    public PerformanceLogging performanceLogging() {

        return Aspects.aspectOf(PerformanceLogging.class);
    }

}
