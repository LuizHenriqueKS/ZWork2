package br.zul.zwork2.db.query;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBJoin {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final String name;
    private final ZDBQuery query;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBJoin(String name, ZDBQuery query) {
        this.name = name;
        this.query = query;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getName() {
        return name;
    }
    public ZDBQuery getQuery() {
        return query;
    }
    
}
