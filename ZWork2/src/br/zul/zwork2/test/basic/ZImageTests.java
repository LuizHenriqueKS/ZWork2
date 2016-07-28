package br.zul.zwork2.test.basic;

import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import br.zul.zwork2.test.ZCustomTest;
import br.zul.zwork2.test.ZMultipleTests;
import br.zul.zwork2.test.basic.file.ZFileTest1;
import br.zul.zwork2.test.basic.image.ZImageTest1;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZImageTests extends ZMultipleTests {

    @Override
    public void loadListTests(List<ZCustomTest> listTests) {
        
        //OBTEM O PACOTE DA ZImageTests
        ZPackage pack = new ZPackage(getClass(),ZImageTest1.class.getPackage()); //AQUI É ONDE ESTÁ TODOS OS TESTES DE IMAGENS
        
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
        return "funções para imagens";
    }
    
}
