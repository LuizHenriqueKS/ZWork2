package br.zul.zwork2.test;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZCustomTest<R> {
    
    public abstract String getTestName();
    public abstract ZIndividualTestResult<R> run();
    
    public void onAfterTest(){}; 
    
}
