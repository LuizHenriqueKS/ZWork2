package br.zul.zwork2.converter;

import br.zul.zwork2.log.ZLogger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Luiz Henrique
 */
public class ZConverterManager {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final Set<ZConverterLoader> loaderSet;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZConverterManager(){
        this.loaderSet = new HashSet<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA LOADER
    //==========================================================================
    public void addLoader(ZConverterLoader loader){
        loaderSet.add(loader);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public Collection<ZConverter> loadConverters(Class<?> type1,Class<?> type2){
        //PREPARA A LISTA DE CONVERSORES QUE VAI SER RETORNADA
        List<ZConverter> result = new ArrayList<>();
        //PERCORRE OS CARREGADORES DE CONVERSORES
        for (ZConverterLoader loader:loaderSet){
            //CARREGA E ADICINONA OS CONVERSORES NA LISTA
            result.addAll(loader.loadConverters(type1, type2));
        }
        //RETORNA A LISTA
        return result;
    }
    
    /**
     * PROCURA PELO CONVERTER DOS TIPOS.
     * 
     * OBS: O CONVERTER RETORNADO PODE TER OS TYPES TROCADOS (type2 PODE SER O type1 E VICE VERSA)
     * 
     * @param type1
     * @param type2
     * @return RETORNA O CONVERTER CORRETO OU <B>NULL</B> SE NÃO O ENCONTROU.
     */
    public ZConverter getConverter(Class<?> type1,Class<?> type2){
        
        //OBTEM UMA LISTA DE POSSÍVEIS CONVERSORES PARA ESSES TIPOS
        Collection<ZConverter> converterCollection = loadConverters(type1,type2);
        
        //PERCORRE A COLEÇÃO
        for (ZConverter converter: converterCollection){
            if (converter.getType1Class().equals(type1)&&converter.getType2Class().equals(type2)){
                return converter;
            } else if (converter.getType1Class().equals(type2)&&converter.getType2Class().equals(type1)) {
                return converter;
            }
        }
        
        //SE NÃO ACHOU NENHUM CONVERTER RETORNA NULL
        return null;
        
    }
    
    public Object convert(Object type1Obj,Class<?> classTo){
        
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(), "convert(Object type1Obj,Class<?> classTo)");
        
        //VERIFICA SE OBJETO É DO TIPO DA CLASSE
        if (type1Obj.getClass().equals(classTo)){
            //ENTÃO NÃO PRECISA CONVERTER
            return type1Obj;
        }
        
        //OBTEM O CONVERTER PARA OS TIPOS INFORMADOS
        ZConverter converter = getConverter(type1Obj.getClass(),classTo);
        
        //VERIFICA SE ENCONTROU O CONVERTER
        if (converter!=null){
            //CONVERTE OS TIPOS
            if (type1Obj.getClass().equals(converter.getType1Class())){
                return converter.type1ToType2(type1Obj);
            } else {
                return converter.type2ToType1(type1Obj);
            }
        } else {
            //INFORMA QUE NÃO ENCONTROU O CONVERSOR
            throw logger.error.prepareException("Não acho o conversor do tipo '%s' para o tipo '%s'.", type1Obj.getClass().getName(),classTo.getName());
        }
        
    }
    
}
