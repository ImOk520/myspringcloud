package fengge.config.componentScan;

import fengge.DTO.CarDTO;
import fengge.DTO.PersonDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Configuration // 相当于原来的xml文件，告诉spring这是个配置类
@ComponentScans(value = {
        @ComponentScan(value = "fengge.dao", includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyFilterType.class})},
                useDefaultFilters = false
        ),
        @ComponentScan(value = "fengge.dao", includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})},
                useDefaultFilters = false
        )
})
public class TestConfig06 {

    @Bean // 给spring注入一个bean，类型是返回值类型，id是默认是方法名
    public CarDTO carDTO(){
        return new CarDTO(1,"BMW");
    }

    @Bean("ppp") // id是默认是方法名,但是若指定了则为指定值
    public PersonDTO personDTO(){
        return new PersonDTO(1,"OK");
    }

}