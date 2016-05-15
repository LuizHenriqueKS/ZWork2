package br.zul.zwork2.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 * @param <R>
 */
public abstract class ZMultipleTests<R> extends ZCustomTest<R> {
    
    private final List<ZCustomTest<R>> listTests;
    
    public ZMultipleTests(){
        listTests = new ArrayList<>();
    }
    
    public abstract void loadListTests(List<ZCustomTest<R>> listTests);
    
    @Override
    public ZIndividualTestResult<R> run() {
        listTests.clear();
        loadListTests(listTests);
        boolean ok = true;
        for (ZCustomTest<R> test:listTests){
            if (!test.run().isOk()){
                ok = false;
                break;
            }
        }
        return new ZIndividualTestResult<>(this,null,null,ok);
    }
    
}
