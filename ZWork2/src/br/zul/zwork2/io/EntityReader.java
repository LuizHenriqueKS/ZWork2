package br.zul.zwork2.io;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.converter.ZConverterManager;
import br.zul.zwork2.entity.ZEntity;
import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.reflection.ZClass;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Luiz Henrique
 */
public abstract class EntityReader implements ZInjectInterface<EntityReader>{
    
    //==========================================================================
    //MANAGERS
    //==========================================================================
    @ZInject
    ZEntityManager entityManager;
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<String> columnList;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZEntity readEntityOne();
    public abstract List<ZEntity> readEntityList();
    public abstract Object convertFrom(Object value,ZEntity entity,String attributeName);
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void setColumns(List<String> columns){
        this.columnList = columns;
    }
    
    public ZEntity readEntity(List<Object> data){
        
        //PREPARA O HASH MAP DE DADOS
        Map<String,Object> result = new HashMap<>();
        
        //PERCORRE AS COLUNAS E OS DADOS
        for (int i=0;i<Math.min(columnList.size(), data.size());i++){
            //ALIMENTA O MAP
            result.put(columnList.get(i), data.get(i));
        }
        
        //RETORNA A ENTIDADE
        return readEntity(result);
        
    }
    
    public ZEntity readEntity(Map<String,Object> data){
        
        //OBTEM O NOME DA CLASSE
        String className = (String)data.get("class");
        
        //OBTEM A CLASSE
        ZClass zClass = new ZClass(className);
        
        //INSTANCIA A CLASSE
        ZEntity entity = (ZEntity)zClass.newInstance();
        
        //PERCORRE OS DADOS
        for (Entry<String,Object> e:data.entrySet()){
            
            //VERIFICA SE NÃO É A COLUNA CLASS
            if (!e.getKey().equals("class")){ 
                
                //CONVERTE O VALOR
                Object convertedValue = convertFrom(e.getValue(), entity, e.getKey());
                
                //ALTERA O VALOR NA ENTIDADE
                entityManager.setAttributeValue(entity, e.getKey(), convertedValue);
                
            }
            
        }
        
        //RETORNA A ENTIDADE
        return entity;
        
    }

    @Override
    public void setValueField(EntityReader object, Field objectField, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
        objectField.set(object, fieldValue);
    }
    
    
}
