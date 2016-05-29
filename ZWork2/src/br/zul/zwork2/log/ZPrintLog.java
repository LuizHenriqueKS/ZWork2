package br.zul.zwork2.log;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZPrintLog {
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract void print(String message,Object... args);
    public abstract void print(Exception exception);
    public abstract ZLogger getLogger();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void println(String message,Object... args){
        print(message+"\r\n",args);
    }
    
    public void println(Exception exception,String message,Object... args){
        println(message,args);
        print(exception);
    }
    
    public RuntimeException prepareException(Exception exception) {
        
        //AO LANÇAR O EXCEPTION A MENSAGEM JÁ SERÁ ESCRITA NO CONSOLE
        //ASSIM NÃO HÁ A NECESSIDADE DE ESCREVER ELA DE NOVO
        
        //OBTEM SE O LOGGER ESCREVE NO CONSOLE
        boolean printOnConsole = getLogger().isPrintOnConsole();
        
        //MUDA NÃO ESCREVER NO LOG
        getLogger().setPrintOnConsole(false);
        
        //ESCREVE SÓ NO ARQUIVO
        print(exception);
        
        //VOLTA PARA COMO ESTAVA DEFINIDO, SE ERA PARA ESCREVER NO CONSOLE OU NÃO
        getLogger().setPrintOnConsole(printOnConsole);
        
        //LANÇA O EXCEPTION
        return new RuntimeException(exception);
        
    }
        
    public RuntimeException prepareException(Exception exception,String message,Object... args) {
        println(message,args);
        return prepareException(exception);
    }
    
    public RuntimeException prepareException(String message,Object... args){
        println(message,args);
        return new RuntimeException(String.format(message,args));
    }

}
