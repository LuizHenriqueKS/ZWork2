package br.zul.zwork2.test;

/**
 *
 * @author Luiz Henrique
 * @param <R> Classe do resultado
 */
public abstract class ZTest<R> extends ZCustomTest<R> {
    
    private ZIndividualTestResult<R> testResult;

    public abstract R getResult();
    public abstract R getExpectedResult();
    public abstract boolean validate(R result,R expectedResult);
    
    @Override
    public ZIndividualTestResult<R> run() {
        R result = getResult();
        R expectedResult = getExpectedResult();
        boolean response = validate(result,expectedResult);
        
        testResult = new ZIndividualTestResult(this, result, expectedResult, response);
        
        return testResult;
    }
    
}
