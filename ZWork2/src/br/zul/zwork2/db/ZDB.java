package br.zul.zwork2.db;

import br.zul.zwork2.db.query.ZDBQuery;
import br.zul.zwork2.log.ZLogger;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZDB {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS ESTÁTICAS
    //==========================================================================
    private static ZDB instance;
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String username;
    private String password;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract void connect();
    public abstract void disconnect();
    public abstract boolean isConnected();
    public abstract ZDBDefinition getDefinition();
    public abstract ZDBType getType();
   
    public abstract ZDBResult execute(ZDBQuery query);
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    //==========================================================================
    //GETTERS E SETTERS ESTÁTICOS
    //==========================================================================
    public static void setDefault(ZDBDefinition definition){
        instance = definition.getDb();
    }
    public static ZDB getDefault(){
        //VERIFICA SE NÃO POSSUI UMA INSTÂNCIA DEFAULT PARA CONEXÃO
        if (instance==null){
            //PREPARA A MENSAGEM
            StringBuilder msg = new StringBuilder();
            msg.append("Não foi definido a conexão default com ");
            msg.append("o banco de dados.\r\n");
            msg.append("Use o comando ZDB.setDefault(definition) para isso.");
            //EXIBE A MENSAGEM QUE NÃO FOI CONFIGURADO
            ZLogger logger = new ZLogger(ZDB.class,"getDefault()");
            throw logger.error.prepareException(msg.toString());
        }
        return instance;
    }
    
}
