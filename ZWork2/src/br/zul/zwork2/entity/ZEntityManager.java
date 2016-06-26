package br.zul.zwork2.entity;

import br.zul.zwork2.annotation.ZAttribute;
import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.string.ZString;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZEntityManager {
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public List<String> listAttributeNames(Class<? extends ZEntity> entityClass){
        //PREPARA A LISTA DE RESULTADO
        List<String> result = new ArrayList<>();
        
        //OBTEM OS CAMPOS ATRIBUTOS
        ZClass zClass = new ZClass(entityClass);
        List<Field> fieldList = zClass.listFields(ZAttribute.class);
        
        //PERCORRE OS CAMPOS ATRIBUTOS
        for (Field f:fieldList){
            //ADICIONA O NOME DO CAMPO NA LISTA
            result.add(f.getName());
        }
        
        //RETORNA A LISTA RESULTANTE
        return result;
    }
    
    public ZAttribute getAttributeAnnotation(Class<? extends ZEntity> entityClass,String attributeName){
        
        //OBTEM O CAMPO
        ZClass zClass = new ZClass(entityClass);
        Field field = zClass.getField(attributeName);
        
        //OBTEM A ANOTAÇÃO
        ZAttribute annotation = field.getAnnotation(ZAttribute.class);
        
        //RETORNA A ANOTAÇÃO
        return annotation;
        
    }
    
    public Class<?> getAttributeType(Class<? extends ZEntity> entityClass,String attributeName){
        
        //OBTEM O CAMPO
        ZClass zClass = new ZClass(entityClass);
        
        //
        Field field = zClass.getField(attributeName);
        
        //OBTEM A ANOTAÇÃO
        Class<?> type = field.getType();
        
        //RETORNA A ANOTAÇÃO
        return type;
        
    }
    
    public Object getAttributeValue(ZEntity entity,String attributeName){
        
        //OBTEM A CLASSE
        ZClass zClass = new ZClass(entity.getClass());
        //OBTEM O NOME DO MÉTODO GET PARA OBTER O VALOR
        ZString methodName = new ZString("get"+attributeName, false);
        //OBTEM O VALOR PELO MÉTODO GET
        Object valueField = zClass.invokeMethod(entity,methodName);
        //RETORNA O VALOR DO CAMPO
        return valueField;
        
    }
    
    public void setAttributeValue(ZEntity entity,String attributeName,Object value){
        
        //OBTEM A CLASSE
        ZClass zClass = new ZClass(entity.getClass());
        //OBTEM O NOME DO MÉTODO SET PARA ALTERAR O VALOR
        ZString methodName = new ZString("set"+attributeName, false);
        //ALTERA O VALOR DO CAMPO
        zClass.invokeMethod(entity, methodName, value);
        
    }
    
    
}
