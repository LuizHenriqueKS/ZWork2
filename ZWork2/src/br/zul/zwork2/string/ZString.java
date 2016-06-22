package br.zul.zwork2.string;

import br.zul.zwork2.string.ZStringSearch.ZStringSearchType;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para manipular strings de maneira mais fácil
 * @author Luiz Henrique
 */
 public class ZString {
    
    //==========================================================================
    //ENUMS
    //==========================================================================
    public enum ZHorizontalDirection{
        LEFT,RIGHT;
    }
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final String string;
    private final boolean caseSensitive;
 
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    public ZString(String string,boolean caseSensitive){
        this.string = string;
        this.caseSensitive = caseSensitive;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public int length(){
        return string.length();
    }
    
    public boolean isEmpty(){
        return string.isEmpty();
    }
    
    public ZString trim(){
        return new ZString(string.trim(),caseSensitive);
    }
    
    @Override
    public String toString(){
        return string;
    }
    
    //==========================================================================
    //MÉTODOS SUBSTRING
    //==========================================================================
      public ZString substring(int start,int end){
        return new ZString(string.substring(start,end),caseSensitive);
    }
    
    public ZString substring(int start){
        return new ZString(string.substring(start),caseSensitive);
    }
    
    //==========================================================================
    //MÉTODOS DE CONTAINS
    //==========================================================================
    public boolean contains(String pattern){
        if (caseSensitive){
            return string.contains(pattern);
        } else {
            return string.toLowerCase().contains(pattern.toLowerCase());
        }
    }
   
    //==========================================================================
    //EQUALS
    //==========================================================================
    public boolean equals(String other){
        if (caseSensitive){
            return string.equals(other);
        } else {
            return string.toLowerCase().equals(other.toLowerCase());
        }
    }
    
    //==========================================================================
    //MÉTODOS DE SPLIT
    //==========================================================================
    public ZString[] split(String pattern){
        List<ZString> result = new ArrayList<>();
        
        ZString source = this;
        
        while (source.contains(pattern)){
            //OBTEM A PARTE DE INICIO DA STRING ATÉ O PADRÃO
            ZString part = source.toLeft(pattern);
            //ADICIONA NA LISTA
            result.add(part);
            //REMOVE ESSA PARTE DO INICIO
            source = source.substring(part.length()+pattern.length());
        }
        
        //SE AINDA SOBROU ALGO
        if (!source.isEmpty()){
            //ADICIONA NA LISTA
            result.add(source);
        }
        
        //CONVERTE A LISTA EM ARRAY E RETORNA ELA
        return result.toArray(new ZString[result.size()]);
    }
    
    //==========================================================================
    //MÉTODOS DE CORTE COMPLETOS
    //==========================================================================
    public ZString to(String[] patterns,String[] patternsToAvoid,ZHorizontalDirection direction,ZStringSearchType searchType){
        
        //PREPARA PARA FAZER A BUSCA
        ZStringSearch search = new ZStringSearch(string, caseSensitive, patterns, patternsToAvoid,searchType);
        
        //VERIFICA SE NÃO FOI ENCONTRADO NENHUM PADRÃO
        if (search.isEmpty()){
            //SE NÃO ACHOU NADA, RETORNA A SIM MESMO
            return this;
        }
        
        //CASO CONTRÁRIO CORTA A STRING
        
        //OBTEM O INDEX DE ONDE CORTAR
        switch (direction){
            case LEFT: //DA ESQUERDA, PEGA O PRIMEIRO INDEX
                search.first();
                break;
            case RIGHT:
                search.last(); //DA DIREITA O ÚLTIMO
                break;
        }
        
        int index = search.getResult().getIndex();
        
        //CORTA A STRING
        String result = string.substring(0,index);
        
        //CONVERTE PARA ZSTRING E RETORNA
        return new ZString(result,caseSensitive);
        
    }
    
    //==========================================================================
    //MÉTODOS DE CORTE SEMI-COMPLETOS
    //==========================================================================
    public ZString to(String [] patterns,String[] patternsToIgnore,ZHorizontalDirection direction){
        return to(patterns, patternsToIgnore, direction, ZStringSearchType.LEFT);
    }
    
    
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE "TO"
    //==========================================================================
    public ZString toRight(String pattern){
        return to(new String[]{pattern},null,ZHorizontalDirection.RIGHT);
    }
    
    public ZString toLeft(String pattern){
        return to(new String[]{pattern}, null, ZHorizontalDirection.LEFT);
    }
    
}
