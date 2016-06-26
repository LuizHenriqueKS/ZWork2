package br.zul.zwork2.log;

import java.io.PrintStream;

/**
 * Classe para manipular log's.
 * @author Luiz Henrique
 */
public class ZLogger {
    
    //==========================================================================
    //VARIÁVEIS PÚBLICAS GLOBAIS
    //==========================================================================
    public static boolean DEFAULT_PRINT_CLASS_METHOD_NAME_ON_CONSOLE = true;
    
    //==========================================================================
    //VARIÁVEIS PRIVADOS
    //==========================================================================
    private ZLogFileWriter writer;
    private boolean printOnConsole;
    
    private Class<?> _class;
    private String methodName;
    
    private boolean printClassMethodNameOnConsole;
    
    //==========================================================================
    //VARIÁVEIS PÚBLICAS
    //==========================================================================
    public ZPrintLog info;
    public ZPrintLog warning;
    public ZPrintLog error;
    
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    /**
     * 
     * @param logFileWriter O escritor de log de arquivos. Informe null para não escrever em arquivos.
     * @param _class A classe do método
     * @param methodName O nome do método
     */
    public ZLogger(ZLogFileWriter logFileWriter,Class<?> _class,String methodName){
        this.writer = logFileWriter;
        this._class = _class;
        this.methodName = methodName;
        this.writer = ZLogFileWriter.getDefaultLogFileWriter();
        this.printOnConsole = true;
        this.printClassMethodNameOnConsole = DEFAULT_PRINT_CLASS_METHOD_NAME_ON_CONSOLE;
        
        //INICIA OS PRINTLOG'S
        initPrintLogs();
    }
    
    public ZLogger(Class<?> _class,String methodName){
        this(ZLogFileWriter.getDefaultLogFileWriter(),_class,methodName);
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void initPrintLogs(){
        
        //DEFINE UM LOGGER
        final ZLogger logger = this;
        
        //INICIA O PRINTLOG DO INFO
        info = new ZPrintLog() {
            @Override
            public void print(String message, Object... args) {
                log(ZLogLevel.INFO, message, args);
            }

            @Override
            public void print(Exception exception) {
                log(ZLogLevel.INFO, exception);
            }

            @Override
            public ZLogger getLogger() {
                return logger;
            }
        };
        
        //INICIA O PRINTLOG DO WARNING
        warning = new ZPrintLog() {
           @Override
            public void print(String message, Object... args) {
                log(ZLogLevel.WARNING, message, args);
            }

            @Override
            public void print(Exception exception) {
                log(ZLogLevel.WARNING, exception);
            }

            @Override
            public ZLogger getLogger() {
                return logger;
            }
        };
        
         //INICIA O PRINTLOG DO ERROR
        error = new ZPrintLog() {
           @Override
            public void print(String message, Object... args) {
                log(ZLogLevel.ERROR, message, args);
            }

            @Override
            public void print(Exception exception) {
                log(ZLogLevel.ERROR, exception);
            }

            @Override
            public ZLogger getLogger() {
                return logger;
            }
        };
        
    }
    
    //==========================================================================
    //MÉTODOS
    //==========================================================================
    /**
     * 
     * Ex: 
     *        new ZLogger(getClass(),"test()").log(ZLogLevel.INFO,"Resultado do teste: %s","OK!");
     *      
     *        Output:
     *            Resultado do teste: OK!
     * 
     * ATÊNÇÃO: Esse método não quebra a linha no console. Você deve inserir o quebra linha na mensagem se desejar assim.
     * 
     * @param level
     * @param message
     * @param args 
     */
    public void log(ZLogLevel level,String message,Object... args){
        //FORMATA A MENSAGEM
        StringBuilder formattedMessage = new StringBuilder();
        formattedMessage.append(_class.getName());
        formattedMessage.append(".");
        formattedMessage.append(methodName);
        formattedMessage.append(":");
        formattedMessage.append(String.format(message,args));
        
        //ESCREVE NO ARQUIVO DE LOG, SE TIVER
        if (writer!=null){
            writer.write(level, formattedMessage.toString());
        }
        
        //VERIFICA SE É PARA ESCREVE NO CONSOLE
        if (isPrintOnConsole()){
            //SE FOR VERIFICA ONDE DEVE ESCREVER
            PrintStream ps = null;
            switch (level){
                case INFO:
                    ps = System.out;
                    break;
                case ERROR:
                    ps = System.err;
                    break;
                case WARNING:
                    ps = System.out;
                    break;
            }
            
            //VERIFICA SE O DESTINO DA MENSAGEM NÃO ESTÁ NULL
            if (ps!=null){
            
                //VERIFICA SE DEVE ESCREVER O ENDEREÇO DA CLASSE E NOME DO MÉTODO
                if (isPrintClassMethodNameOnConsole()){
                    //SE SIM IMPRIME A MENSAGEM FORMATADA QUE JÁ TEM ESSAS INFORMAÇÕES
                    ps.print(formattedMessage.toString());
                    //PULA A LINHA SE NÃO TIVER O PULAR
                    if (!formattedMessage.toString().endsWith("\n")){
                        ps.print("\r\n");
                    }
                } else {
                    //CASO CONTRÁRIO, IMPRIME SÓ O QUE INTERESSA
                    ps.print(String.format(message, args));
                }

            }
        }
        
    }
    
    public void log(ZLogLevel level,Exception exception){
        //ESCREVE NO ARQUIVO DE LOG, SE TIVER
        if (writer!=null){
            writer.write(level, exception);
        }
        
        //VERIFICA SE É PARA ESCREVE NO CONSOLE
        if (isPrintOnConsole()){
            //SE FOR VERIFICA ONDE DEVE ESCREVER
            PrintStream ps = null;
            switch (level){
                case INFO:
                    ps = System.out;
                    break;
                case ERROR:
                    ps = System.err;
                    break;
                case WARNING:
                    ps = System.out;
                    break;
            }
            //VERIFICA SE O DESTINO NÃO ESTÁ NULL
            if (ps!=null){
                exception.printStackTrace(ps);
            }
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZLogFileWriter getWriter() {
        return writer;
    }
    public void setWriter(ZLogFileWriter writer) {
        this.writer = writer;
    }

    public boolean isPrintOnConsole() {
        return printOnConsole;
    }
    public void setPrintOnConsole(boolean printOnConsole) {
        this.printOnConsole = printOnConsole;
    }

    public Class<?> get_Class() {
        return _class;
    }
    public void set_Class(Class<?> _class) {
        this._class = _class;
    }

    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isPrintClassMethodNameOnConsole() {
        return printClassMethodNameOnConsole;
    }
    public void setPrintClassMethodNameOnConsole(boolean printClassMethodNameOnConsole) {
        this.printClassMethodNameOnConsole = printClassMethodNameOnConsole;
    }
    
}
