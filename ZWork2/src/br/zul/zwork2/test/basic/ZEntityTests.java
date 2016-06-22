package br.zul.zwork2.test.basic;

import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import br.zul.zwork2.test.ZCustomTest;
import br.zul.zwork2.test.ZMultipleTests;
import br.zul.zwork2.test.basic.entity.ZEntityTest1;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityTests extends ZMultipleTests {

    @Override
    public void loadListTests(List<ZCustomTest> listTests) {
        
        //OBTEM O PACOTE DA ZInjectTest1
        ZPackage pack = new ZPackage(getClass(),ZEntityTest1.class.getPackage()); 
        
        //LISTA AS CLASSES DO PACOTE
        List<ZClass> classes = pack.listClasses(true,ZCustomTest.class,true);
        
        //PERCORRE AS CLASSES
        for (ZClass c:classes){
            //ADICIONA O TESTE NA LISTA
            listTests.add((ZCustomTest)c.newInstance());
        }
        
    }

    @Override
    public String getTestName() {
        return "Entidades";
    }
    
}
