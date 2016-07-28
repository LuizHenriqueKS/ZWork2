package br.zul.zwork2.db;

import br.zul.zwork2.log.ZLogger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Luiz Henrique
 */
public class ZSqlDBResult extends ZDBResult {
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private final PreparedStatement ps;
    private final ResultSet rs;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZSqlDBResult(PreparedStatement ps,ResultSet rs){
        this.ps = ps;
        this.rs = rs;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void close(){
        ZLogger logger = new ZLogger(getClass(),"close()");
        try{
            rs.close();
            ps.close();
        }catch(Exception e){
            throw logger.error.prepareException(e);
        }
    }
    
}
