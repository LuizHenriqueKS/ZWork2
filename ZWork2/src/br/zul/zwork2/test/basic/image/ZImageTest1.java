package br.zul.zwork2.test.basic.image;

import br.zul.zwork2.io.ZImage;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author luiz.silva
 */
public class ZImageTest1 extends ZSimpleTest<Boolean> {

    private ZImage image;
    private boolean result;
    
    @Override
    public String convertResultToString(Boolean result) {
        return result.toString();
    }

    @Override
    public Boolean getResult() {
        if (image==null){
            image = ZImage.fromResource("/br/zul/zwork2/img/z16x16.png");
            result = image.isValid();
        }
        return result;
    }

    @Override
    public Boolean getExpectedResult() {
        return true;
    }

    @Override
    public String getTestName() {
        return "leitura de imagem de recurso interno";
    }
    
}
