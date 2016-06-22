package br.zul.zwork2.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZMultipleTests extends ZCustomTest<Object> {
    
    private final List<ZCustomTest> listTests;
    private final List<ZCustomTest> failedTests;
    
    public ZMultipleTests(){
        listTests = new ArrayList<>();
        failedTests = new ArrayList<>();
    }
    
    public abstract void loadListTests(List<ZCustomTest> listTests);
    
    @Override
    public ZIndividualTestResult run() {
        listTests.clear();
        failedTests.clear();
        loadListTests(listTests);
        boolean ok = true;
        for (ZCustomTest test:listTests){
            if (!test.run().isOk()){
                ok = false;
                failedTests.add(test);
            }
        }
        return new ZIndividualTestResult<>(this,null,null,ok);
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public int countTests(){
        
        //VERIFICA SE A LISTA DE TESTES ESTÁ VAZIA
        if (listTests.isEmpty()){
           //SE ESTIVER VAZIA, CARREGA ELA
           loadListTests(listTests);
        }
        
        //RETORNA A QUANTIDADE DE TESTES DA LISTA
        return listTests.size();
        
    }
    
    public int countFailedTests(){
        return failedTests.size();
    }
    
    public int countSuccessfulTests(){
        //SUBTRAI A QUANTIDADE TOTAL DE TESTES PELA QUANTIDADE DE TESTES QUE FALHARAM
        return countTests() - countFailedTests();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public List<ZCustomTest> getFailedTests() {
        return failedTests;
    }
    
    
}
