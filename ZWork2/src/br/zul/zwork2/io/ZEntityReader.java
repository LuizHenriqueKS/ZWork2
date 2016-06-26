package br.zul.zwork2.io;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.entity.ZEntity;
import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.util.ZStringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZEntityReader implements ZInjectInterface{
    
    //==========================================================================
    //MANAGERS
    //==========================================================================
    @ZInject
    protected ZEntityManager entityManager;
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<String> columnList;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract void open();
    public abstract void close();
    public abstract ZEntity readNextEntity(Class<? extends ZEntity> entityClass);
    public abstract Object convertFrom(Object value,ZEntity entity,String attributeName);
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void setColumns(List<String> columns){
        this.columnList = columns;
    }
    
    public ZEntity convertToEntity(Class<? extends ZEntity> entityClass,List<Object> data){
        
        //PREPARA O HASH MAP DE DADOS
        Map<String,Object> result = new HashMap<>();
        
        //PERCORRE AS COLUNAS E OS DADOS
        for (int i=0;i<Math.min(columnList.size(), data.size());i++){
            //ALIMENTA O MAP
            result.put(ZStringUtils.removeEmptyChar(columnList.get(i)), data.get(i));
        }
        
        //RETORNA A ENTIDADE
        return convertToEntity(entityClass,result);
        
    }
    
    public ZEntity convertToEntity(Class<? extends ZEntity> entityClass, Map<String,Object> data){
        
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(),"convertToEntity(Class<? extends ZEntity> entityClass, Map<String,Object> data)");
        
        //PREPARA A CLASSE DA ENTIDADE
        ZClass zClass = null;
        
        //VERIFICA SE POSSUI O ATRIBUTO CLASS
        if (data.containsKey("class")){
        
           //OBTEM O NOME DA CLASSE
           String className = (String)data.get("class");
        
           //OBTEM A CLASSE
           zClass = new ZClass(className);
           
        } else{
            //SE NÃO POSSUI O ATRIBUTO CLASS USA O entityClass
            if (entityClass!=null){
                zClass = new ZClass(entityClass);
            } else {
                //SE NÃO TEM O entityClass NÃO É POSSÍVEL CONVERTER PARA UM ENTIDADE VÁLID
                throw logger.error.prepareException("Não foi encontrado o atributo 'class' e nem foi informado uma classe de entidade! Dados: %s",data.toString());
            }
        }
        
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
    
    public ZEntity readOneEntity(){
        
        return readOneEntity(null);
                
    }
    
    public ZEntity readOneEntity(Class<? extends ZEntity> entityClass) {
        
        //PREPARA O VARIAVEL DE RESULTADO
        ZEntity result;

        //INICIA A LEITURA DO ARQUIVO
        open();

        //LÊ SÓ A PRIMEIRA ENTIDADE
        result = readNextEntity(entityClass);

        //TERMINA A LEITURA DO ARQUIVO
        close();

        //RETORNA O RESULTADO
        return result;

    }
    
    public ZEntity readNextEntity(){
        return readNextEntity(null);
    }
    
    public List<ZEntity> readEntityList(){
        
        return readEntityList(null);
        
    }

    public List<ZEntity> readEntityList(Class<? extends ZEntity> entityClass) {

        //PREPARA A LISTA DE RESULTADO
        List<ZEntity> result = new ArrayList<>();

        //INICIA A LEITURA
        open();

        //PREPARA A ENTIDADE PARA SER LIDA
        ZEntity entity;

        //LÊ AS ENTIDADES
        while ((entity = readNextEntity(entityClass)) != null) {

            //ADICIONA A ENTIDADE NA LISTA DE RESULTADO
            result.add(entity);

        }
    
        //TERMINA A LEITURA 
        close();

        //RETORNA O RESULTADO
        return result;

    }
    
}
