package br.zul.zwork2.main;

import br.zul.zwork2.log.ZLogFileWriter;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.test.ZTestManager;
import br.zul.zwork2.test.ZTestResult;
import br.zul.zwork2.test.basic.ZInjectTests;

/**
 *
 * @author Luiz Henrique
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //CONFIGURA
        ZLogFileWriter.setDefaultLogFileWriter(new ZLogFileWriter("Log"));
        ZLogger.DEFAULT_PRINT_CLASS_METHOD_NAME_ON_CONSOLE = false;
        ZLogger logger = new ZLogger(Main.class,"main(String[] args)");
        
        //EXECUTA OS TESTES
        if (true){
            ZTestResult result = ZTestManager.getInstance().executeTests(Main.class,ZInjectTests.class.getPackage());
            //EXIBE OS RESULTADOS
            logger.info.println("Quantidade de testes bem sucedidos: %d", result.countOkTests());
            if (result.countFailedTests()>0){
                logger.error.println("Quantidade de testes que falharam: %d",result.countFailedTests());
            } else {
                logger.info.println("Quantidade de testes que falharam: %d",result.countFailedTests());
            }
            if (result.countErrorTests()>0){
                logger.error.println("Quantidade de testes que apresentaram erro: %d",result.countErrorTests());
            } else {
                logger.info.println("Quantidade de testes que apresentaram erro: %d",result.countErrorTests());
            }
        }
        
    }
    
}
