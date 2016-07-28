package br.zul.zwork2.test.basic.image;

import br.zul.zwork2.io.ZImage;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author luiz.silva
 */
public class ZImageTest2 extends ZSimpleTest<Boolean> {

    @Override
    public String convertResultToString(Boolean result) {
        return result.toString();
    }

    @Override
    public Boolean getResult() {
        ZImage image = ZImage.fromResource("/br/zul/zwork2/img/z16x16.pn");
        return image.isValid();
    }

    @Override
    public Boolean getExpectedResult() {
        return false;
    }

    @Override
    public String getTestName() {
        return "leitura de imagem de recurso interno 2";
    }
    
}
