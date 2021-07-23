package GOF.chain.stop;

import cn.hutool.core.lang.Console;

import java.util.List;

/**
 * 责任链模式
 */
public class EmptyValidHandler implements ValidHandler {
    @Override
    public boolean verify(List<Object> allObjectList) {
        Console.log("非空验证处理");
        Console.log("模拟检验非空过程");
        return true;
    }
}