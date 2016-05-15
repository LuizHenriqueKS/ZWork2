package br.zul.zwork2.test;

/**
 *
 * @author Luiz Henrique
 * @param <R> A resposta do teste
 */
public class ZIndividualTestResult<R> {
    
    private final ZCustomTest test;
    private final R result;
    private final R resultExpected;
    private final boolean ok;

    public ZIndividualTestResult(ZCustomTest test, R result, R resultExpected, boolean ok) {
        this.test = test;
        this.result = result;
        this.resultExpected = resultExpected;
        this.ok = ok;
    }

    public ZCustomTest getTest() {
        return test;
    }

    public R getResult() {
        return result;
    }

    public R getResultExpected() {
        return resultExpected;
    }

    public boolean isOk() {
        return ok;
    }
    
}
