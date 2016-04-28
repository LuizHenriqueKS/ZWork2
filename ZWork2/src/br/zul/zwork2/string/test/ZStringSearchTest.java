package br.zul.zwork2.string.test;

import br.zul.zwork2.string.ZStringSearch;

/**
 *
 * @author skynet
 */
public class ZStringSearchTest {
    
    public static void testCountResults(){
        System.out.print("Testando ZStringSearch.countResults()...");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..");
        int result = instance.countResults();
        
        if (result==2){
            System.out.println("OK!");
        } else {
            System.out.println("FALHOU!");
            System.out.println("Resultado esperado 2 e foi obtido: "+result);
        }
        
    }
    
    
}
