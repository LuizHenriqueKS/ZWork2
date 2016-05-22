package br.zul.zwork2.string;

import br.zul.zwork2.string.ZStringSearch.ZStringSearchType;

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
    private String string;
    private boolean caseSensitive;
 
    //==========================================================================
    //MÉTODOS CONSTRUTORES
    //==========================================================================
    public ZString(String string,boolean caseSensitive){
        this.string = string;
        this.caseSensitive = caseSensitive;
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
        return to(patterns, patterns, direction, ZStringSearchType.LEFT);
    }
    
    
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE "TO"
    //==========================================================================
    public ZString toRight(String pattern){
        return to(new String[]{pattern},null,ZHorizontalDirection.RIGHT);
    }
    
}
