package br.zul.zwork2.test;

import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestManager {
    
    //==========================================================================
    //VÁRIAVEIS PRIVADAS ESTÁTICAS
    //==========================================================================
    private static ZTestManager instance;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZTestManager(){
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZTestResult executeTests(Class<?> mainClass,Package pack){
        
        //PREPARA A LISTA DE TESTES
        List<ZCustomTest> tests = new ArrayList<>();
        
        //OBTEM UMA LISTA COM AS CLASSES DE TESTES
        List<ZClass> classes = new ZPackage(mainClass,pack).listClasses(false,ZCustomTest.class,true);
        
        //PRECORRE A LISTA DE CLASSES
        for (ZClass c:classes){
            //INSTANCIA O TESTE
            ZCustomTest test = (ZCustomTest) c.newInstance();
            //ADICIONA O TESTE NA LISTA DE TESTES
            tests.add(test);
        }
        
        //EXECUTA OS TESTES
        return executeTests(tests);
        
    }
    
    public ZTestResult executeTests(List<ZCustomTest> tests){
        
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(),"ZTestResult executeTests(List<ZCustomTest> tests)");
        
        //PREPARA O RESULTADO DO TESTE
        ZTestResult testResult = new ZTestResult();
        
        //PERCORRE OS TESTS
        for (ZCustomTest test:tests){
            
            try{
           
                //EXECUTA O TESTE E OBTEM O RESULTADO
                ZIndividualTestResult r = executeTest(test);

                //VERIFICA SE O TESTE PASSOU
                if (r.isOk()){
                    //SE PASSOU ADICIONA NA LISTA DE BEM SUCEDIDOS
                    testResult.addOkTest(test);
                } else {
                    //SE NÃO PASSOU ADICIONA NA LISTA DE TESTES QUE FALHARAM
                    testResult.addFailedTest(test);
                }
            
            } catch (Exception e){
                
                //SE TEVE PROBLEMAS NA EXECUÇÃO DO TESTE REGISTRA NO LOGGER
                logger.error.print(e);
                
                //ADICIONA NA LISTA DE TESTES QUE DERAM ERRO
                tests.add(test);
            }
            
        }
        
        //RETORNA O RESULTADO DOS TESTES
        return testResult;
        
    }
    
    public ZTestResult executeTest(List<? extends ZCustomTest> tests){
        
        //PREPARA A LISTA DE TESTES
        List<ZCustomTest> testList = new ArrayList<>();
        
        
        for (Object obj:tests){
            //CONVERTE O OBJETO EM TESTE CUSTOMIZADO
            ZCustomTest test = (ZCustomTest) obj;
            
            //ADICINA NA LISTA DE TESTES
            testList.add(test);
        }
        
        //EXECUTA OS TESTES
        return executeTests(testList);
        
    }
    
    public ZIndividualTestResult executeTest(ZCustomTest test){
        //INICIA O LOGGER
        ZLogger logger = new ZLogger(getClass(),"executeTest(ZCustomTest test)");
        
        //PREPARANDO PARA OBTER RESULTADO DO TESTE
        ZIndividualTestResult result;
        
        //ESCREVENDO NO LOG O NOME DO TESTE
        logger.info.print("[%s]Testando %s...",test.getClass().getName(), test.getTestName());
        
        try {
            //EXECUTA O TESTE
            result = test.run();
        
            //VERIFICANDO O RESULTADO
            if (result.isOk()){
                
                //PASSOU NO TESTE
                logger.info.print("OK!");
                
                //ESCREVE NO LOG O RESULTADO DE UM TESTE MULTIPLO
                printMultipleTestResult(test);
                
                //PULA A LINHA
                logger.info.println();
                
            } else {
                //FALHOU NO TESTE
                logger.warning.println("FALHOU!");
                
                //ESCREVE NO LOG O RESULTADO DE UM TESTE MULTIPLO
                printMultipleTestResult(test);
                
                //PULA A LINHA
                logger.info.println();
                
                //TENTA ESCREVER O RESULTADO ESPERADO
                printExpectedResult(test,result);
                
            }
            
            return result;
            
        }catch (Exception e){
            logger.error.println(e, "ERRO!");
            return null;
        }
        
    }
    
    
    public void printMultipleTestResult(ZCustomTest test){
       
        //OBTEM O LOGGER
        ZLogger logger = new ZLogger(getClass(),"printMultipleTestResult(ZCustomTest test)");
        
        //VERIFICA SE É UM GRUPO DE TESTES
        if (test instanceof ZMultipleTests){

            ZMultipleTests zmt = (ZMultipleTests) test;

            //OBTEM A QUANTIDADE TOTAL DE TESTES DESSE TESTE MULTIPLO
            int countTests = zmt.countTests();
            //OBTEM A QUANTIDADE DE TESTES BEM SUCESSIDOS
            int countSuccessfulTests = zmt.countSuccessfulTests();

            //REGISTRA NO LOG
            logger.info.print("[%d/%d]",countSuccessfulTests,countTests);

        }
    }

    public void printExpectedResult(ZCustomTest test, ZIndividualTestResult r){
        
        //LOGGER
        ZLogger logger = new ZLogger(getClass(),"printExpectedResult(ZCustomTest test, ZIndividualTestResult r)");
        
        //VERIFICA SE TINHA UM RESULTADO ESPERADO
        if (r.getExpectedResult()!=null){
            
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
            
        } else if (test instanceof ZMultipleTests){ //SE FOR UM TESTE MULTIPLO O RESULTADO ESPERADO É SEMPRE NULL
            
            //CONVERTE O TESTE PARA UM TESTE MULTIPLO
            ZMultipleTests zmt = (ZMultipleTests) test;
            
            //VERIFICA SE TODOS OS TESTES DERAM OKAY
            if (zmt.countFailedTests()<=0){
                //SE NÃO TEVE NENHUM TESTE QUE FALHOU PARA AQUI
                return;
            }
            
            //SE UM OU MAIS TESTE FALHARAM TENTA IMPRIMIR INFORMAÇÕES SOBRE ELE
            for (ZCustomTest t:zmt.getFailedTests()){
                
                logger.warning.println("[%s]%s ... Falhou!", t.getClass().getName(),t.getTestName());
                printExpectedResult(t, t.run());
                
            }
            
        }
        
    }
 
    //==========================================================================
    //MÉTODOS ESTÁTICOS
    //==========================================================================
    public static ZTestManager getInstance() {
        if (instance==null){
            instance = new ZTestManager();
        }
        return instance;
    }
    public static void setInstance(ZTestManager instance) {
        ZTestManager.instance = instance;
    }
    
    
}
