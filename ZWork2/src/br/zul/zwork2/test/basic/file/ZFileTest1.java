package br.zul.zwork2.test.basic.file;

import br.zul.zwork2.test.ZSimpleTest;
import br.zul.zwork2.util.ZFileUtils;

/**
 *
 * @author Luiz Henrique
 */
public class ZFileTest1 extends ZSimpleTest<String> {

    @Override
    public String convertResultToString(String result) {
        return result;
    }

    @Override
    public String getResult() {
        return ZFileUtils.getFileExtension("teste.txt");
    }

    @Override
    public String getExpectedResult() {
        return ".txt";
    }

    @Override
    public String getTestName() {
        return "Extração da extensão do arquivo";
    }
    
}
