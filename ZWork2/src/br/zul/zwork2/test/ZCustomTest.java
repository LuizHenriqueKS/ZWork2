package br.zul.zwork2.test;

import br.zul.zwork2.util.ZObject;

/**
 *
 * @author Luiz Henrique
 * @param <R>
 */
public abstract class ZCustomTest<R> {
    
    public abstract String getTestName();
    public abstract ZIndividualTestResult<R> run();
    
    public void onAfterTest(){}; 
    
}
