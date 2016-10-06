package br.zul.zwork2.string;

import br.zul.zwork2.util.ZStringUtils;
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
    
    public boolean isNull(){
        return string==null;
    }
    
    public boolean isNullOrEmpty(){
        return isNull()||isEmpty();
    }
    
    public ZString trim(){
        return new ZString(string.trim(),caseSensitive);
    }
    
    public ZString normalize(){
        return new ZString(ZStringUtils.removeAccents(string),caseSensitive);
    }
    
    /**
     * Alias normalize();
     * @return 
     */
    public ZString removeAccents(){
        return normalize();
    }
    
    @Override
    public String toString(){
        return string;
    }
    
    public ZString remove(String pattern){
        ZString[] part = split(pattern);
        StringBuilder builder = new StringBuilder();
        for (ZString p:part){
            builder.append(p.toString());
        }
        return new ZString(builder.toString(),caseSensitive);
    }
    
    public int count(String pattern){
        return split(pattern).length-1;
    }
    
    //==========================================================================
    //MÉTODOS CLIPBOARD
    //==========================================================================
    public ZString toClipboard(){
        ZStringUtils.toClipboard(string);
        return this;
    }
    
    public static ZString fromClipboard(boolean caseSensitive){
        return ZStringUtils.fromClipboard(caseSensitive);
    }
    
    //==========================================================================
    //MÉTODOS UPPERCASE
    //==========================================================================
    public ZString toUpperCase(){
        return new ZString(string.toUpperCase(), caseSensitive);
    }
    
    public ZString toUpperCase(int index){
        char[] array = string.toCharArray();
        array[index] = (array[index]+"").toUpperCase().toCharArray()[0];
        return new ZString(new String(array),caseSensitive);
    }
    
    public ZString firstToUpperCase(){
        return toUpperCase(0);
    }
    
    //==========================================================================
    //MÉTODOS LOWERCASE
    //==========================================================================
    public ZString toLowerCase(){
        return new ZString(string.toLowerCase(),caseSensitive);
    }
    
    public ZString toLowerCase(int index){
        char[] array = string.toCharArray();
        array[index] = (array[index]+"").toLowerCase().toCharArray()[0];
        return new ZString(new String(array),caseSensitive);
    }
    
    
    public ZString firstToLowerCase(){
        return toLowerCase(0);
    }
    
    //==========================================================================
    //MÉTODOS DE REPLACE
    //==========================================================================
    /**
     * 
     * Exemplo de uso
     * 
     * @param target O que será substituido
     * @param replacement 
     * @param limit
     * @return 
     */
    public ZString replace(String target,String replacement, Integer limit){
        
        int i = 0;
        ZString z = this;
        
        while (z.contains(target)){
            ZString newZ = z.toLeft(target);
            newZ = newZ.appendRight(replacement);
            newZ = newZ.appendRight(z.fromLeft(target));
            z = newZ;
            if (limit!=null&&++i==limit){
                break;
            }
        }
        
        return z;
        
    }
    
    public ZString replace(String target,String replacement){
        return replace(target,replacement,null);
    }
    
    public ZString replaceFirst(String target,String replacement){
        return replace(target,replacement,1);
    }
    
    //==========================================================================
    //MÉTODOS DE CONVERSÃO
    //==========================================================================
    public Integer asInteger(){
        return Integer.valueOf(string);
    }
    
    public Float asFloat(){
        return Float.valueOf(string);
    }
    
    public Long asLong(){
        return Long.valueOf(string);
    }
    
    public String asString(){
        return string;
    }
    
    //==========================================================================
    //MÉTODOS SEARCH
    //==========================================================================
    public ZStringSearch search(String[] patterns,String[] patternsToAvoid,ZStringSearchType type){
        ZStringSearch search = new ZStringSearch(string, caseSensitive, patterns, patternsToAvoid, type);
        return search;
    }
    
    public ZStringSearch search(String pattern,String patternsToAvoid,ZStringSearchType type){
        return search(new String[]{pattern},new String[]{patternsToAvoid},type);
    }
    
    public ZStringSearch search(String... patterns){
        return search(patterns,null,ZStringSearchType.LEFT_UNIQUE);
    }
    
    //==========================================================================
    //MÉTODOS APPEND
    //==========================================================================
    /**
     * 
     *  new ZString("a",true).appendLeft("b"); //SAÍDA: ba
     * 
     * @param str
     * @return 
     */
    public ZString appendLeft(String str){
        return new ZString(str+string,caseSensitive);
    }
    
    public ZString appendLeft(ZString z){
        return new ZString(z.string+string,caseSensitive);
    }
    
    public ZString appendRight(String str){
        return new ZString(string+str,caseSensitive);
    }
    
    public ZString appendRight(ZString z){
        return new ZString(string+z.string,caseSensitive);
    }
    
    //==========================================================================
    //MÉTODOS INDEX OF
    //==========================================================================
    public int indexOf(String pattern,int fromIndex){
        String str = string;
        if (!caseSensitive){
           str = str.toLowerCase();
        }
        return str.indexOf(pattern,fromIndex);
    }
    
    public int indexOf(String pattern){
        String str = string;
        if (!caseSensitive){
           str = str.toLowerCase();
        }
        return str.indexOf(pattern);
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
    //MÉTODOS STARTS WITH
    //==========================================================================
    public boolean startsWith(String pattern){
        return startsWith(pattern,null);
    }
    
    public boolean startsWith(String pattern,Integer offset){
        String s = string;
        if (offset!=null){
            s = s.substring(offset);
        }
        if (caseSensitive){
            return s.startsWith(pattern);
        } else {
            return s.toLowerCase().startsWith(pattern.toLowerCase());
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
    
    public ZString[] split(String patterns[],String patternsToAvoid[]){
        //PREPARA PARA FAZER A BUSCA
        ZStringSearch search = new ZStringSearch(string, caseSensitive, patterns, patternsToAvoid,ZStringSearchType.LEFT_UNIQUE);
        List<ZString> result = new ArrayList<>();
        
        //QUEBRA A STRING
        int startIndex = 0;
        while (search.next()){
            //OBTEM A PARTE DA STRING
            String str = string.substring(startIndex,search.getResult().getIndex()+search.getResult().getPattern().length());
            //CONVERTE A STRING PARA ZSTRING
            ZString z = new ZString(str,caseSensitive);
            //ADICIONA NA LISTA
            result.add(z);
            startIndex = search.getResult().getIndex()+search.getResult().getPattern().length();
        }
        
        //SE AINDA SOBROU ALGO
        if (startIndex<string.length()){
            //OBTEM A PARTE RESTANTE
            String str = string.substring(startIndex);
            //VERIFICA SE NÃO ESTÁ VAZIA
            if (!str.isEmpty()){
                //CONVERTE A STRING PARA ZSTRING
                ZString z = new ZString(str,caseSensitive);
                //ADICIONA NA LISTA
                result.add(z);
            }
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
            //SE NÃO ACHOU NADA, RETORNA A SI MESMO
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
    
    public ZString from(String[] patterns,String[] patternsToAvoid,ZHorizontalDirection direction,ZStringSearchType searchType){
        
        //PREPARA PARA FAZER A BUSCA
        ZStringSearch search = new ZStringSearch(string, caseSensitive, patterns, patternsToAvoid,searchType);
        
        //VERIFICA SE NÃO FOI ENCONTRADO NENHUM PADRÃO
        if (search.isEmpty()){
            //SE NÃO ACHOU NADA, RETORNA A SI MESMO
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
        int length = search.getResult().getPattern().length();
        
        //CORTA A STRING
        String result = string.substring(index+length);
        
        //CONVERTE PARA ZSTRING E RETORNA
        return new ZString(result,caseSensitive);
        
    }
    
    //==========================================================================
    //MÉTODOS DE CORTE SEMI-COMPLETOS
    //==========================================================================
    public ZString to(String [] patterns,String[] patternsToIgnore,ZHorizontalDirection direction){
        return to(patterns, patternsToIgnore, direction, ZStringSearchType.LEFT_UNIQUE);
    }
    
    public ZString from(String [] patterns,String[] patternsToIgnore,ZHorizontalDirection direction){
        return from(patterns, patternsToIgnore, direction, ZStringSearchType.LEFT_UNIQUE);
    }
    
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE FROM RIGHT
    //==========================================================================
    public ZString fromRight(String pattern){
        return from(new String[]{pattern},null,ZHorizontalDirection.RIGHT);
    }
    
      public ZString fromRight(String... patterns){
        return from(patterns,null,ZHorizontalDirection.RIGHT);
    }
      
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE FROM LEFT
    //==========================================================================
    public ZString fromLeft(String pattern){
        return from(new String[]{pattern}, null, ZHorizontalDirection.LEFT);
    }
      
    public ZString fromLeft(String... patterns){
        return from(patterns, null, ZHorizontalDirection.LEFT);
    }
    
    public ZString fromLeft(String[] patterns,String[] patternsToVoid){
        return from(patterns, patternsToVoid, ZHorizontalDirection.LEFT);
    }
    
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE "TO"
    //==========================================================================
    public ZString toRight(String pattern){
        return to(new String[]{pattern},null,ZHorizontalDirection.RIGHT);
    }
    
    public ZString toRight(String... patterns){
        return to(patterns,null,ZHorizontalDirection.RIGHT);
    }
    
    public ZString toRight(String[] patterns,String[] patternsToVoid){
        return to(patterns,patternsToVoid,ZHorizontalDirection.RIGHT);
    }
    
    //==========================================================================
    //MÉTODOS BÁSICOS DE CORTE TO LEFT
    //==========================================================================
    public ZString toLeft(String... patterns){
        return to(patterns, null, ZHorizontalDirection.LEFT);
    }
    
    public ZString toLeft(String pattern){
        return to(new String[]{pattern},null,ZHorizontalDirection.LEFT);
    }
    
    public ZString toLeft(String[] patterns,String[] patternsToVoid){
        return to(patterns,patternsToVoid,ZHorizontalDirection.LEFT);
    }
    
}
