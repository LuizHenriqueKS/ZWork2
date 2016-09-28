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
    private boolean parameter;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZDBFilterEquals(boolean parameter,Object value,String... name){
        super(name);
        this.value = value;
        this.parameter = parameter;
    }
   
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isParameter() {
        return parameter;
    }
    public void setParameter(boolean parameter) {
        this.parameter = parameter;
    }
    
}
