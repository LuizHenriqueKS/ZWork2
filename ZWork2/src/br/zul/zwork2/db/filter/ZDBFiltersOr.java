package br.zul.zwork2.db.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBFiltersOr extends ZDBFilter {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final List<ZDBFilter> filterList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBFiltersOr(){
        super("OR");
        this.filterList = new ArrayList<>();
    }
   
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZDBFiltersOr addFilter(ZDBFilter filter){
        this.filterList.add(filter);
        return this;
    }
    
    public ZDBFiltersOr removeFilter(ZDBFilter filter){
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
