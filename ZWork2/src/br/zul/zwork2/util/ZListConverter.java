package br.zul.zwork2.util;

import java.util.List;

/**
 * CLASSE PARA CONVERTER UMA LISTA PARA OUTRA
 * 
 * @author Luiz Henrique
 * @param <T>
 */
public class ZListConverter<T> {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<T> resultList;
    private List<?> originList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZListConverter(){
        
    }
    
    public ZListConverter(List<?> originList,List<T> resultList){
        this.originList = originList;
        this.resultList = resultList;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public List<T> convert(List<?> originList,List<T> resultList){
        
        for (Object obj:originList){
            resultList.add((T) obj);
        }
        
        return resultList;
        
    }
    
    public List<T> convert(){
        
        return convert(originList,resultList);
        
    }
    
}
