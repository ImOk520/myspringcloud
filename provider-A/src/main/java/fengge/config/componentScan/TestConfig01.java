package fengge.config.componentScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = "fengge.dao")
public class TestConfig01 {

}
