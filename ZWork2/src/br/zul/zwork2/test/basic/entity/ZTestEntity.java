package br.zul.zwork2.test.basic.entity;

import br.zul.zwork2.annotation.ZAttribute;
import br.zul.zwork2.entity.ZEntity;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestEntity extends ZEntity {
    
    //==========================================================================
    //ATRIBUTOS PRIVADOS
    //==========================================================================
    @ZAttribute
    private int id;
    
    @ZAttribute(type="text")
    private String value;
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
}
