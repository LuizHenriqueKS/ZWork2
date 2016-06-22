package br.zul.zwork2.test.basic;

import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import br.zul.zwork2.test.ZCustomTest;
import br.zul.zwork2.test.ZMultipleTests;
import br.zul.zwork2.test.basic.inject.ZInjectTest1;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZInjectTests extends ZMultipleTests {

    @Override
    public void loadListTests(List<ZCustomTest> listTests) {
        
        //OBTEM O PACOTE DA ZInjectTest1
        ZPackage pack = new ZPackage(getClass(),ZInjectTest1.class.getPackage()); //AQUI É ONDE ESTÁ TODOS OS TESTES DE INJEÇÕES
        
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
        return "Injeções de dependência";
    }
    
}
