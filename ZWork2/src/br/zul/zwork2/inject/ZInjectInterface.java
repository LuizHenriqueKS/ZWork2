package br.zul.zwork2.inject;

import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 */
public interface ZInjectInterface {

    void setValueField(Object object,Field objectField,Object fieldValue) throws IllegalArgumentException,IllegalAccessException;
    
}
