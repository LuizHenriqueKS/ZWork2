package br.zul.zwork2.db.filter;

/**
 *
 * @author Luiz Henrique
 */
public class ZDBFilterEquals extends ZDBFilter {
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    private Object value;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBFilterEquals(Object value,String... name){
        super(name);
    }
   
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZDBFilterType getType() {
        return ZDBFilterType.EQUALS;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    
}
