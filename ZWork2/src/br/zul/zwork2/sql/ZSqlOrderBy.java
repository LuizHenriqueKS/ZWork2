package br.zul.zwork2.sql;

import br.zul.zwork2.db.query.*;

/**
 *
 * @author Luiz Henrique
 */
public class ZSqlOrderBy {
    
    //==========================================================================    
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    public String column;
    private boolean ascending;
    
    //==========================================================================    
    //CONSTRUTORES
    //==========================================================================
    public ZSqlOrderBy(String column, boolean ascending) {
        this.column = column;
        this.ascending = ascending;
    }
    
    //==========================================================================    
    //GETTERS E SETTERS
    //==========================================================================
    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isAscending() {
        return ascending;
    }
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
    
}
