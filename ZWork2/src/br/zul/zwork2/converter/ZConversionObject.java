package br.zul.zwork2.converter;

import br.zul.zwork2.annotation.ZAttribute;
import br.zul.zwork2.entity.ZEntity;
import br.zul.zwork2.entity.ZEntityManager;

/**
 *
 * @author Luiz Henrique
 */
public class ZConversionObject {
    
    //==========================================================================
    //VARIAVEIS PRIVADAS
    //==========================================================================
    private Object value;
    private String name;
    private ZAttribute attribute;
    private Class<?> type;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConversionObject(){
        
    }
    
    public ZConversionObject(ZEntity entity,String attributeName){
        ZEntityManager entityManager = new ZEntityManager();
        this.type = entity.getClass();
        this.value = entityManager.getAttributeValue(entity, attributeName);
        this.name = attributeName;
        this.attribute = entityManager.getAttributeAnnotation(entity.getClass(), attributeName);
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

    public ZAttribute getAttribute() {
        return attribute;
    }
    public void setAttribute(ZAttribute attribute) {
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }
    public void setType(Class<?> type) {
        this.type = type;
    }
    
}
