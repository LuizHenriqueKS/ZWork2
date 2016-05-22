package br.zul.zwork2.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestResult {
    
    private final List<ZCustomTest> okTests;
    private final List<ZCustomTest> failedTests;
    private final List<ZCustomTest> errorTests;
    
    public ZTestResult(){
        okTests = new ArrayList<>();
        failedTests = new ArrayList<>();
        errorTests = new ArrayList<>();
    }
    
    public void addOkTest(ZCustomTest test){
        okTests.add(test);
    }
    
    public void addFailedTest(ZCustomTest test){
        failedTests.add(test);
    }
    
    public void addErrorTests(ZCustomTest test){
        errorTests.add(test);
    }
    
    public List<ZCustomTest> listOkTests(){
        return okTests;
    }
    
    public List<ZCustomTest> listFailedTests(){
        return failedTests;
    }
    
    public List<ZCustomTest> listErrorTests(){
        return errorTests;
    }
    
    public int countOkTests(){
        return okTests.size();
    }
    
    public int countFailedTests(){
        return failedTests.size();
    }
    
    public int countErrorTests(){
        return errorTests.size();
    }
    
}
