package config.beanAnnotation;

import cn.hutool.core.lang.Console;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Feng, Ge 2021/5/12 0012 18:08
 */
public class Person implements BeanPostProcessor {

    public Person() {
        Console.log("构造方法！");
    }

    public void init(){
        Console.log("初始化方法！");
    }

    public void destroy(){
        Console.log("销毁方法！");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Console.log("postProcessBeforeInitialization 在初始化方法前执行" + beanName + "###" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Console.log("postProcessAfterInitialization 在初始化方法后执行" + beanName + "###" + bean);
        return bean;
    }
}
