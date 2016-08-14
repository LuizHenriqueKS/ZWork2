package br.zul.zwork2.test.basic.string;

import br.zul.zwork2.string.ZString;
import br.zul.zwork2.test.ZSimpleTest;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZStringTest1 extends ZSimpleTest<ZString> {

    private final static ZString SOURCE = new ZString("https://www.google.com/pt/Luiz.txt",true);
    
    @Override
    public String convertResultToString(ZString result) {
        return result.toString();
    }

    @Override
    public ZString getResult() {
        return SOURCE.fromRight("/");
    }

    @Override
    public ZString getExpectedResult() {
        return new ZString("Luiz.txt",true);
    }

    @Override
    public String getTestName() {
        return "Função básica de texto";
    }
    
}
