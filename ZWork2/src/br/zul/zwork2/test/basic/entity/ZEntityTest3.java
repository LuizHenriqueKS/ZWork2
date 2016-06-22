package br.zul.zwork2.test.basic.entity;

import br.zul.zwork2.annotation.ZAttribute;
import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.test.ZSimpleTest;
import java.lang.annotation.Annotation;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityTest3 extends ZSimpleTest<String> {

    private static final String EXPECTED_RESULT = "TEST";
    
    @Override
    public String getTestName() {
        return "Obtendo anotação do atributo da entendidade";
    }

    @Override
    public String convertResultToString(String result) {
        return result;
    }

    @Override
    public String getResult() {
        //INSTANCIA A ENTIDADE TESTE
        ZTestEntity testEntity = new ZTestEntity();
        //ALTERA O VALOR DO CAMPO VALUE
        testEntity.setValue(EXPECTED_RESULT);
        //TENTA OBTER O VALOR DO CAMPO COM O ZEntityManager
        return (String) new ZEntityManager().getAttributeValue(testEntity, "value");
    }

    @Override
    public String getExpectedResult() {
        return EXPECTED_RESULT;
    }
    
}
