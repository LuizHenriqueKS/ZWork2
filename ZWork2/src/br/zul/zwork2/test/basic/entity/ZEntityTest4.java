package br.zul.zwork2.test.basic.entity;

import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityTest4 extends ZSimpleTest<String> {

    private static final String EXPECTED_RESULT = "TEST";
    
    @Override
    public String getTestName() {
        return "Alterando o valor do atributo de uma entidade";
    }

    @Override
    public String convertResultToString(String result) {
        return result;
    }

    @Override
    public String getResult() {
        //INSTANCIA A ENTIDADE TESTE
        ZTestEntity testEntity = new ZTestEntity();
        //TENTA ALTERAR O VALOR DO CAMPO COM O ZEntityManager
        new ZEntityManager().setAttributeValue(testEntity, "value", EXPECTED_RESULT);
        //TENTA OBTER O VALOR DO CAMPO COM O ZEntityManager
        return (String) new ZEntityManager().getAttributeValue(testEntity, "value");
    }

    @Override
    public String getExpectedResult() {
        return EXPECTED_RESULT;
    }
    
}
