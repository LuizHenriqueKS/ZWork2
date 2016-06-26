package br.zul.zwork2.io;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.converter.ZConversionObject;
import br.zul.zwork2.entity.ZEntity;
import br.zul.zwork2.entity.ZEntityManager;
import br.zul.zwork2.inject.ZInjectInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public abstract class ZEntityWriter<T> implements ZInjectInterface {
    
    //==========================================================================
    //MANAGERS
    //==========================================================================
    @ZInject
    ZEntityManager entityManager;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract void open();
    public abstract void writeHead(List<String> columns);
    public abstract void writeEntity(List<ZConversionObject> attributes);
    public abstract void writeFooter(List<String> columns);
    public abstract void close();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void writeOne(ZEntity entity){
        
        //ABRE PARA EDIÇÃO
        open();
        
        //OBTEM OS NOMES DOS ATRIBUTOS
        List<String> attributeNameList = entityManager.listAttributeNames(entity.getClass());
        
        //ESCREVE O CABEÇALHO
        writeHead(attributeNameList);
        
        //ESCREVE O CONTEÚDO
        write(entity);
        
        //ESCREVE O RODAPÉ
        writeFooter(attributeNameList);
        
        //FECHA PARA EDITAÇÃO
        close();
        
    }
    
    public void writeCollection(Collection<ZEntity> collection){
        
        //ABRE PARA EDIÇÃO
        open();
        
        //OBTEM O PRIMEIRO REGISTRO
        ZEntity first = collection.iterator().next();
        
        //OBTEM OS NOMES DOS ATRIBUTOS
        List<String> attributeNameList = entityManager.listAttributeNames(first.getClass());
        
        //ESCREVE O CABEÇALHO
        writeHead(attributeNameList);
        
        //PERCORRE AS ENTIDADE
        for (ZEntity entity:collection){
            //ESCREVE O CONTEÚDO
            write(entity);
        }
        
        //ESCREVE O RODAPÉ
        writeFooter(attributeNameList);
        
        //FECHA PARA EDITAÇÃO
        close();
        
    }
    
    public void write(ZEntity entity){
        //PREPARA LISTA COM O QUE DEVE SER CONVERTIDO E ESCRITO
        List<ZConversionObject> list = new ArrayList<>();
        
        //OBTEM UMA LISTA COM OS NOMES DOS ATRIBUTOS DA ENTIDADE
        List<String> attributeNameList = entityManager.listAttributeNames(entity.getClass());
        
        //PERCORRE OS ATRIBUTOS DA ENTIDADE
        for (String atributeName:attributeNameList){
            //INSTANCIA O OBJETO DE CONVERSÃO
            ZConversionObject obj = new ZConversionObject(entity, atributeName);
            //ADICIONA NA LISTA
            list.add(obj);
        }
        
        //ADICIONA UM PARA A CLASSE
        ZConversionObject obj = new ZConversionObject();
        obj.setName("class");
        obj.setValue(entity.getClass().getName());
        list.add(obj);
        
        //ESCREVE A LISTA
        writeEntity(list);
    }

}
