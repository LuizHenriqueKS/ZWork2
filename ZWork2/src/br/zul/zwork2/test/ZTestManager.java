package br.zul.zwork2.test;

import br.zul.zwork2.log.ZLogger;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestManager {

    public ZTestResult executeTests(List<ZCustomTest> tests){
        return null;
    }
    
    public ZTestResult executeTest(List<? extends ZCustomTest> tests){
        return null;
    }
    
    public ZIndividualTestResult executeTest(ZCustomTest test){
        //INICIA O LOGGER
        ZLogger logger = new ZLogger(getClass(),"executeTest(ZCustomTest test)");
        
        //PREPARANDO PARA OBTER RESULTADO DO TESTE
        ZIndividualTestResult result;
        
        //ESCREVENDO NO LOG O NOME DO TESTE
        logger.info.print("Testando %s...", test.getTestName());
        
        try {
            //EXECUTA O TESTE
            result = test.run();
        
            //VERIFICANDO O RESULTADO
            if (result.isOk()){
                //PASSOU NO TESTE
                logger.info.println("OK!");
            } else {
                //FALHOU NO TESTE
                logger.warning.println("FALHOU!");
                printExpectedResult(test,result);
            }
            
            return result;
            
        }catch (Exception e){
            logger.error.println(e, "ERRO!");
            return null;
        }
        
    }

    public void printExpectedResult(ZCustomTest test, ZIndividualTestResult r){
        
        //VERIFICA SE TINHA UM RESULTADO ESPERADO
        if (r.getExpectedResult()!=null){
            
            //INICIA O LOGGER
            ZLogger logger = new ZLogger(getClass(),"printExpectedResult(ZCustomTest test, ZIndividualTestResult r)");
            
            //PREPARA PARA OBTER OS RESULTADOS
            String result = "null";
            String expectedResult = "null";
            
            //TENTA OBTER OS RESULTADOS
            if (test instanceof ZTest){
                //CONVERTE O TESTE PARA ZTest
                ZTest t = ((ZTest) test);
                //VERIFICA SE O RESULTADO NÃO É NULL
                if (t.getResult()!=null){
                    //SE NÃO É NULL, OBTEM O RESULTADO E CONVERTE EM STRING
                    result = t.getResult().toString();
                }
                //NÃO VERIFICA SE O RESULTADO ESPERADO PORQUE JÁ FOI VERIFICADO MAIS A CIMAS
                expectedResult = t.getExpectedResult().toString();
            } else if (test instanceof ZSimpleTest){
                //CONVERTE O TESTE PARA ZSimpleTest
                ZSimpleTest t = ((ZSimpleTest) test);
                //VERIFICA SE O RESULTADO NÃO É NULL
                if (t.getResult()!=null){
                    result = t.convertResultToString(t.getResult());
                }
                //NÃO VERIFICA SE O RESULTADO ESPERADO PORQUE JÁ FOI VERIFICADO MAIS A CIMAS
                expectedResult = t.convertResultToString(t.getExpectedResult());
            }
            
            //PREPARA MENSAGEM PARA ESCREVER NO LOG
            StringBuilder msg = new StringBuilder();
            msg.append("Resultado esperado: ");
            msg.append(expectedResult);
            msg.append("\r\n");
            msg.append("Resultado obtido: ");
            msg.append(result);
            
            //ESCREVE MENSAGEM NO LOG
            logger.warning.println(msg.toString());
            
        }
        
    }
    
}
