package br.zul.zwork2.test;

/**
 *
 * @author Luiz Henrique
 * @param <R> A resposta do teste
 */
public class ZIndividualTestResult<R> {
    
    private final ZCustomTest test;
    private final R result;
    private final R expectedResult;
    private final boolean ok;

    public ZIndividualTestResult(ZCustomTest test, R result, R expectedResult, boolean ok) {
        this.test = test;
        this.result = result;
        this.expectedResult = expectedResult;
        this.ok = ok;
    }

    public ZCustomTest getTest() {
        return test;
    }

    public R getResult() {
        return result;
    }

    public R getExpectedResult() {
        return expectedResult;
    }

    public boolean isOk() {
        return ok;
    }
    
}
