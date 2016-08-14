package br.zul.zwork2.test.basic;

import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import br.zul.zwork2.test.ZCustomTest;
import br.zul.zwork2.test.ZMultipleTests;
import br.zul.zwork2.test.basic.string.ZStringTest1;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZStringTests extends ZMultipleTests {

    @Override
    public void loadListTests(List<ZCustomTest> listTests) {
        
        //OBTEM O PACOTE DA ZHttpTest1
        ZPackage pack = new ZPackage(getClass(),ZStringTest1.class.getPackage()); //AQUI É ONDE ESTÃO TODOS OS TESTES TEXTO
        
        //LISTA AS CLASSES DO PACOTE
        List<ZClass> classes = pack.listClasses(false,ZCustomTest.class,true);
        
        //PERCORRE AS CLASSES
        for (ZClass c:classes){
            //ADICIONA O TESTE NA LISTA
            listTests.add((ZCustomTest)c.newInstance());
        }
        
    }

    @Override
    public String getTestName() {
        return "funções para texto";
    }
    
}
