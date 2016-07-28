package br.zul.zwork2.db.query;

import br.zul.zwork2.db.filter.ZDBFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBQuery {
 
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private final List<ZDBFilter> filterList;
    private String name;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBQuery(String name){
        this.name = name;
        this.filterList = new ArrayList<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public ZDBQueryType getType(){
        return ZDBQueryType.SELECT;
    }
    
    //==========================================================================
    //MÉTODOS PARA FILTROS
    //==========================================================================
    public ZDBQuery addFilter(ZDBFilter filter){
        filterList.add(filter);
        return this;
    }
    
    public ZDBQuery addFilters(List<ZDBFilter> filterList){
        this.filterList.addAll(filterList);
        return this;
    }
    
    /**
     * APENAS COPIA OS FILTROS, MAS NÃO DELETA OS FILTROS QUE JÁ POSSUI
     * @param query
     * @return 
     */
    public ZDBQuery copyFiltersFrom(ZDBQuery query){
        addFilters(query.listFilters());
        return this;
    }
    
    public ZDBQuery removeFilter(ZDBFilter filter){
        filterList.remove(filter);
        return this;
    }
    
    public List<ZDBFilter> listFilters(){
        return new ArrayList<>(filterList);
    }
    
    public ZDBQuery clearFilters(){
        filterList.clear();
        return this;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getName() {
        return name;
    }
    public ZDBQuery setName(String name) {
        this.name = name;
        return this;
    }
    
}
