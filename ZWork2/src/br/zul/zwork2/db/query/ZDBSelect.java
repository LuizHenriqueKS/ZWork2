package br.zul.zwork2.db.query;

import br.zul.zwork2.db.selection.ZDBSelection;
import br.zul.zwork2.db.selection.ZDBSelectionNormal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBSelect extends ZDBQuery {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final List<ZDBSelection> selectionList;
    private final List<ZDBJoin> joinList;
    private final List<String> groupByList;
    private final List<ZDBOrderBy> orderByList;
    private Long limit;
    private Long offset;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBSelect(String name,String alias) {
        super(name,alias);
        this.selectionList = new ArrayList<>();
        this.joinList = new ArrayList<>();
        this.orderByList = new ArrayList<>();
        this.groupByList = new ArrayList<>();
    }
    
    //==========================================================================
    //MÉTODOS PARA SELEÇÃO
    //==========================================================================
    public ZDBSelect addSelection(ZDBSelection selection){
        this.selectionList.add(selection);
        return this;
    }
    
    public ZDBSelect addSelection(String selection){
        return addSelection(new ZDBSelectionNormal(selection));
    }
    
    public ZDBSelect removeSelection(String selection){
        for (ZDBSelection s:selectionList){
            if (s instanceof ZDBSelectionNormal&&s.getName().equals(selection)){
                selectionList.remove(s);
                break;
            }
        }
        return this;
    }
    
    public List<ZDBSelection> listSelections(){
        return new ArrayList<>(selectionList);
    }
    
    public ZDBSelect clearSelections(){
        selectionList.clear();
        return this;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZDBQueryType getType(){
        return ZDBQueryType.SELECT;
    }
    
    //==========================================================================
    //MÉTODOS PARA JUNÇÕES
    //==========================================================================
    /**
     * Ex:
     *  addJoin("Person",query);
     * 
     * @param query
     * @return 
     */
    public ZDBSelect addJoin(ZDBQuery query){
        ZDBJoin join = new ZDBJoin(query);
        this.joinList.add(join);
        return this;
    }
    
    public ZDBSelect addJoin(ZDBJoin join){
        this.joinList.add(join);
        return this;
    }
    
    public ZDBSelect addLeftJoin(ZDBQuery query){
        return addJoin(new ZDBLeftJoin(query));
    }
   
    public ZDBSelect removeJoin(ZDBJoin join){
        this.joinList.remove(join);
        return this;
    }
    
    public List<ZDBJoin> listJoins(){
        return new ArrayList<>(this.joinList);
    }
    
    public ZDBSelect clearJoins(){
        joinList.clear();
        return this;
    }
    
    //==========================================================================
    //MÉTODOS PARA AGRUPAR
    //==========================================================================
    public ZDBSelect addGroupBy(String groupBy){
        this.groupByList.add(groupBy);
        return this;
    }
    
    public ZDBSelect removeGroupBy(String groupBy){
        this.groupByList.remove(groupBy);
        return this;
    }
    
    public List<String> listGroupBys(){
        return this.groupByList;
    }
    
    //==========================================================================
    //MÉTODOS PARA ORDER BY
    //==========================================================================
    public ZDBSelect addOrderBy(ZDBOrderBy orderBy){
        this.orderByList.add(orderBy);
        return this;
    }
    
    public ZDBSelect removeOrderBy(ZDBOrderBy orderBy){
        this.orderByList.remove(orderBy);
        return this;
    }
    
    public List<ZDBOrderBy> listOrderBys(){
        return new ArrayList<>(orderByList);
    }
    
    public ZDBSelect clearOrderBys(){
        orderByList.clear();
        return this;
    }
    
    //==========================================================================
    //GETTERS E STTERS
    //==========================================================================
    public Long getLimit() {
        return limit;
    }
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }
    public void setOffset(Long offset) {
        this.offset = offset;
    }
    
}
