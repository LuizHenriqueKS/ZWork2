package br.zul.zwork2.db.query;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBOrderBy {
    
    //==========================================================================    
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    public String name;
    private boolean ascending;
    
    //==========================================================================    
    //CONSTRUTORES
    //==========================================================================
    public ZDBOrderBy(String name, boolean ascending) {
        this.name = name;
        this.ascending = ascending;
    }
    
    //==========================================================================    
    //GETTERS E SETTERS
    //==========================================================================
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isAscending() {
        return ascending;
    }
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
    
}
