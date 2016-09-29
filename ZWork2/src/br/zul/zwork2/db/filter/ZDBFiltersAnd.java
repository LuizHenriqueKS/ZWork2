package br.zul.zwork2.db.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBFiltersAnd extends ZDBFilter {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final List<ZDBFilter> filterList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBFiltersAnd(){
        super("AND");
        this.filterList = new ArrayList<>();
    }
   
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZDBFiltersAnd addFilter(ZDBFilter filter){
        this.filterList.add(filter);
        return this;
    }
    
    public ZDBFiltersAnd removeFilter(ZDBFilter filter){
        this.filterList.remove(filter);
        return this;
    }
    
    public List<ZDBFilter> listFilters(){
        return Collections.unmodifiableList(filterList);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    
}
