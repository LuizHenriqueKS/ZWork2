package br.zul.zwork2.test.basic.entity;

import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.test.ZSimpleTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityTest1 extends ZSimpleTest<List<String>> {

    @Override
    public String convertResultToString(List<String> result) {
        //CRIA UMA NOVA INSTÂNCIA DA LISTA (PARA ORDENA-LA)
        result = new ArrayList<>(result);
        
        //ORDENA A LISTA
        Collections.sort(result);
        
        //CONCATENA TODAS AS STRINGS (SEPARADOS POR QUEBRA LINHA) E RETORNA O RESULTADO;
        StringBuilder str = new StringBuilder();
        for (String line:result){
            str.append(line);
            str.append("\r\n");
        }
        return str.toString();
        
    }

    @Override
    public List<String> getResult() {
        return new ZEntityManager().listAttributeNames(ZTestEntity.class);
    }

    @Override
    public List<String> getExpectedResult() {
        return Arrays.asList("id","value");
    }

    @Override
    public String getTestName() {
        return "Listagem de nomes de atributos";
    }
    
}
