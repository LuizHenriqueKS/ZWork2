package br.zul.zwork2.log;

import br.zul.zwork2.string.ZString;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 */
public class ZLogFileWriter {
    
    //==========================================================================
    //VARIAVEIS PÚBLICAS GLOBAIS
    //==========================================================================
    public static File DEFAULT_DIRECTORY = new File("Log");
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS GLOBAIS 
    //==========================================================================
    private static ZLogFileWriter logFileWriter;
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private File infoLogFile;
    private File warningLogFile;
    private File errorLogFile;
    private File generalLogFile;
    private FileWriter generalLogFileWriter;
    
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    /**
     * 
     * OBS: SE VOCÊ NÃO QUER QUE O LOG SEJA ESCRITE EM ALGUM ARQUIVO, VOCÊ PRECISA APENAS SETAR ELE NULL. EX:
     * 
     *      ZLogFileWriter logWriter = new ZLogFileWriter("Log");
     *      logWriter.setGeneralLogFile(null);
     * 
     * @param name Nome do arquivo do log, sem a extensão (.log).
     */
    public ZLogFileWriter(String name){
        if (name!=null){
            init(new File(DEFAULT_DIRECTORY,name+".log"));
        }
    }
    
    /**
     * 
     * @param directory Diretório onde quer armazenar todos os logs.
     * @param name O nome do arquivo de log, sem a extensão (.log).
     */
    public ZLogFileWriter(File directory,String name){
        this(new File(directory,name+".log"));
    }
    
    public ZLogFileWriter(File generalLogFile){
        
        init(generalLogFile);
        
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(File generalLogFile){
        //OBTEM OS DADOS NECESSARIOS PARA CRIAS OS ARQUIVOS DE LOG
        String filename = new ZString(generalLogFile.getName(),false).toRight(".").toString();
        File directory = generalLogFile.getParentFile();
        
        //GUARDA O ARQUIVO DE LOG GERAL
        this.generalLogFile = generalLogFile;
        
        //INICIALIZA OS ARQUIVOS DE LOG
        this.infoLogFile = new File(directory,filename+".info.log");
        this.warningLogFile = new File(directory,filename+".warning.log");
        this.errorLogFile = new File(directory,filename+".error.log");
    }
    
    //==========================================================================
    //MÉTODOS DE ESCRITA
    //==========================================================================
    public void write(FileWriter fileWriter,ZLogLevel level,String message){
        try {
            //FORMA A MENSAGEM
            String formattedMessage = formatMessage(level,message);
            //ESCREVE A MENSAGEM FORMATADA
            fileWriter.write(formattedMessage);
            //FORÇA A ESCRITA
            fileWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(ZLogFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(File file,ZLogLevel level,String message){
        try {
            //SE TIVER O ARQUIVO DE LOG
            if (file!=null){
                //VERIFICA SE NÃO EXISTE A PASTA DO LOG
                if (!file.getParentFile().exists()){
                    //SE NÃO EXISTE CRIA ELA
                    file.getParentFile().mkdirs();
                }
                
                //ABRE O WRITER
                try (FileWriter writer = new FileWriter(file,true)) {
                    //ESCREVE ELE ENTÃO
                    write(writer,level,message);
                }
                //FECHA O WRITER AUTOMATICAMENTE
            }
        } catch (IOException ex) {
            Logger.getLogger(ZLogFileWriter.class.getName()).log(Level.SEVERE, "Erro em tentar iniciar o FileWriter em cima do arquivo de log.", ex);
        }
    }  
    
    public void write(ZLogLevel level,String message){
        
        //TENTA ESCREVER NO ARQUIVO DE LOG CERTO
        switch (level){
            case INFO:
                write(infoLogFile,level,message);
                break;
            case WARNING:
                write(warningLogFile,level,message);
                break;
            case ERROR:
                write(errorLogFile,level, message);
                break;
        }
        
        //SE TIVER O LOG GERAL
        if (generalLogFile!=null){
            //ESCREVE NELE A MENSAGEM TBM
            write(getGeneralLogFileWriter(),level,message);
        }
        
    }
    
      public void write(ZLogLevel level,Exception exception){
     
        //CONVERTE O EXCEPTION EM STRING
        StringWriter buffer = new StringWriter();
        try (PrintWriter pw = new PrintWriter(buffer)) {
            exception.printStackTrace(pw);
        }   
        String message = buffer.getBuffer().toString();
          
        //TENTA ESCREVER NO ARQUIVO DE LOG CERTO
        switch (level){
            case INFO:
                write(infoLogFile,level,message);
                break;
            case WARNING:
                write(warningLogFile,level,message);
                break;
            case ERROR:
                write(errorLogFile,level, message);
                break;
        }
        
        //SE TIVER O LOG GERAL
        if (generalLogFile!=null){
            //ESCREVE NELE A MENSAGEM TBM
            write(getGeneralLogFileWriter(),level,message);
        }
        
    }
    
    //==========================================================================
    //MÉTODOS PARA LIMPAR OS ARQUIVOS DE LOG
    //==========================================================================
    /**
     * Deleta o arquivo de log do level referente.
     * 
     *  Obs: se você quiser deletar o arquivo de log geral, use o comando deleteGeneralLogFile();
     * 
     * @param level O level do log que deseja deletar. 
     * @return TRUE = ARQUIVO DE LOG DELETADO
     *          FALSE = HOUVE ALGUMA FALHA AO TENTAR DELETAR
     */
    public boolean deleteLogFile(ZLogLevel level){
        //PREPARA PARA OBTER O ARQUIVO DE LOG
        File file = null;
        
        //OBTEM O ARQUIVO DE LOG PELO LEVEL
        switch (level){
            case INFO:
                file = infoLogFile;
                break;
            case WARNING:
                file = warningLogFile;
                break;
            case ERROR:
                file = errorLogFile;
                break;
        }
        
        //VERIFICA SE O ARQUIVO NÃO ESTÁ NULL
        if (file!=null){
            //SE NÃO TIVER TENTA DELETAR
            return file.delete();
        } else {
            //SE TIVER NULL RETORNA QUE TEVE ALGUM PROBLEMA
            return false;
        }
    }
    
    /**
     * Deleta todos os arquivos de log.
     * @return TRUE = ARQUIVOs DE LOG DELETADO
     *          FALSE = NÃO FOI POSSÍVEL DELETAR UM DOS ARQUIVOS
     */
    public boolean deleteAllLogFiles(){
        
        boolean result = true;
        
        //DELETA TODOS OS ARQUIVOS DE LOG POR LEVEL
        for (ZLogLevel level:ZLogLevel.values()){
           result &= deleteLogFile(level);
        }
        
        //DELETA O ARQUIVO DE LOG GERAL
        result &= deleteGeneralLogFile();
        
        return result;
        
    }
    
    /**
     * Deleta o arquivo de log geral.
     * @return TRUE = ARQUIVO DE LOG DELETADO
     *          FALSE = HOUVE ALGUMA FALHA AO TENTAR DELETAR
     */
    public boolean deleteGeneralLogFile(){
        
        //VERIFICA SE ELE FOI INFORMADO
        if (generalLogFile!=null){
            //VERIFICA SE O FileWriter DELE NÃO ESTÁ ABERTO
            if (generalLogFileWriter!=null){
                try {
                    //SE TIVER TENTA FECHAR
                    generalLogFileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(ZLogFileWriter.class.getName()).log(Level.SEVERE, "Não foi possível fechar o FileWriter do arquivo de log geral", ex);
                }
                //E SETA NULL PARA SABER SE ESTÁ ABERTO OU FECHADO
                generalLogFileWriter = null;
            }
            //E POR FIM TENTA DELETAR ELE
            return generalLogFile.delete();
        } else {
            //SE O ARQUIVO DE LOG GERAL FOR NULL, RETORNA FALSE
            return false;
        }
        
    }
    
    //==========================================================================
    //MÉTODOS PROTEGIDOS
    //==========================================================================
    protected String formatMessage(ZLogLevel level,String message){
        
        //PREPARA PARA FORMATAR A MENSAGEM
        StringBuilder sb = new StringBuilder();
        
        //PREPARA PARA FORMATAR A DATA E HORA
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //OBTEM A DATA E HORA ATUAL 
        String dateTime = sdf.format(Calendar.getInstance().getTime()); 
        
        //ESCREVE A DATA E HORA
        sb.append(dateTime);
        sb.append(" - ");
        
        //ESCREVE O LEVEL DO LOG
        sb.append(level.name());
        sb.append(" - ");
        
        //ESCREVE A MENSAGEM
        sb.append(message);
        
        //RETORNA O RESULTADO
        return sb.toString();
        
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public FileWriter getGeneralLogFileWriter() {
        
        if (generalLogFileWriter==null){
            if (generalLogFile!=null){
                try {
                    generalLogFileWriter = new FileWriter(generalLogFile,true);
                } catch (IOException ex) {
                    Logger.getLogger(ZLogFileWriter.class.getName()).log(Level.SEVERE, "Não foi possível iniciar o fileWriter do log geral!", ex);
                }
            }
        }
        
        return generalLogFileWriter;
    }
    public void setGeneralLogFileWriter(FileWriter generalLogFileWriter) {
        this.generalLogFileWriter = generalLogFileWriter;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS GLOBAIS 
    //==========================================================================
    public static ZLogFileWriter getDefaultLogFileWriter() {
        if (logFileWriter==null){
            System.err.println("Use ZLogFileWriter.setDefaultLogFileWriter para definir o escritor de log padrão!");
        }
        return logFileWriter;
    }
    public static void setDefaultLogFileWriter(ZLogFileWriter logFileWriter) {
        ZLogFileWriter.logFileWriter = logFileWriter;
    }
    
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public File getInfoLogFile() {
        return infoLogFile;
    }
    public void setInfoLogFile(File infoLogFile) {
        this.infoLogFile = infoLogFile;
    }

    public File getWarningLogFile() {
        return warningLogFile;
    }
    public void setWarningLogFile(File warningLogFile) {
        this.warningLogFile = warningLogFile;
    }

    public File getErrorLogFile() {
        return errorLogFile;
    }
    public void setErrorLogFile(File errorLogFile) {
        this.errorLogFile = errorLogFile;
    }

    public File getGeneralLogFile() {
        return generalLogFile;
    }
    public void setGeneralLogFile(File generalLogFile) {
        this.generalLogFile = generalLogFile;
    }
    
}
