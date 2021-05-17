package config.conditional;

import cn.hutool.core.lang.Console;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 实现Condition是个接口中的matches方法，返回true则注入bean，false则不注入。
 */
public class ForeignCondition implements Condition {

    /**
     * AnnotatedTypeMetadata:注解所在位置的注释信息
     * ConditionContext:判断条件能使用的上下文环境
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String type = context.getEnvironment().getProperty("car.type");
        Console.log("[type]" + type);
        if ("foreign".equals(type)) {
            Console.log("[type?]" + true);
            return true;
        }
        return false;
    }
}