package br.zul.zwork2.db;

import br.zul.zwork2.log.ZLogger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Override
    public boolean first() {
        try {
            return rs.first();
        } catch (SQLException ex) {
            ZLogger logger = new ZLogger(getClass(),"first()");
            throw logger.error.prepareException(ex);
        }
    }

    @Override
    public boolean next() {
        try {
            return rs.next();
        } catch (SQLException ex) {
            ZLogger logger = new ZLogger(getClass(),"next()");
            throw logger.error.prepareException(ex);
        }
    }

    @Override
    public List<Object> asList() {
        try {
            if (rs.isBeforeFirst()){
                first();
            }
            int count = listNames().size();
            List<Object> result = new ArrayList<>();
            for (int i=1;i<=count;i++){
                Object obj = rs.getObject(i);
                if (rs.wasNull()){
                    result.add(null);
                } else {
                    result.add(obj);
                }
            }
            return result;
        } catch (SQLException ex) {
            ZLogger logger = new ZLogger(getClass(),"asList()");
            throw logger.error.prepareException(ex);
        }
    }

    @Override
    public List<String> listNames() {
        try {
            List<String> result = new ArrayList<>();
            for (int i=0;i<rs.getMetaData().getColumnCount();i++){
                result.add(rs.getMetaData().getColumnName(i));
            }
            return result;
        } catch (SQLException ex) {
            ZLogger logger = new ZLogger(getClass(),"listNames()");
            throw logger.error.prepareException(ex);
        }
    }
    
}
