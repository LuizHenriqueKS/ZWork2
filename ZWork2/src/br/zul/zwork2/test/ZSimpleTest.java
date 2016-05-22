package br.zul.zwork2.test;

/**
 * Para testes mais simples.
 * Converte os resultado e o resultado experado em String e tenta comparar os dois.
 * 
 * @author Luiz Henrique
 * @param <R> Tipo de resultado
 */
public abstract class ZSimpleTest<R> extends ZTest<R> {
   
    public abstract String convertResultToString(R result);
    
    @Override
    public boolean validate(R result,R expectedResult){
        String resultStr = convertResultToString(result);
        String expectedResultStr = convertResultToString(result);
        
        boolean ok = resultStr.equals(expectedResultStr);
        
        return ok;
    }
    
}
