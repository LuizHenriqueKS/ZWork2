package br.zul.zwork2.test.basic.entity;

import br.zul.zwork2.annotation.ZAttribute;
import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.test.ZSimpleTest;
import java.lang.annotation.Annotation;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityTest2 extends ZSimpleTest<ZAttribute> {

    @Override
    public String getTestName() {
        return "Obtendo anotação do atributo da entendidade";
    }

    @Override
    public String convertResultToString(ZAttribute result) {
        return result.type();
    }

    @Override
    public ZAttribute getResult() {
        return new ZEntityManager().getAttributeAnnotation(ZTestEntity.class, "value");
    }

    @Override
    public ZAttribute getExpectedResult() {
        return new ZAttribute() {
            @Override
            public Class join() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String format() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String type() {
                return "text";
            }

            @Override
            public int length() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
}
