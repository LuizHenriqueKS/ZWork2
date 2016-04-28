package br.zul.zwork2.string;

import java.util.ArrayList;
import java.util.List;

/**
 *  Classe usada para fazer pesquisas em Strings
 *   Ex: source = "1.3..6.8"
 *       patterns = "."
 *       patternsToVoid = ".."
 *      
 *       indices resultantes = "1 e 6"
 * @author skynet
 */
public class ZStringSearch {
    
    ////////////////////////////////////////////////////////////////////////////
    //ENUMS
    ////////////////////////////////////////////////////////////////////////////
    /**
     * CUMULATIVE - Obtem todos os indices sem se importar com quem vem primeiro ou se um padrão faz parte de outro.
     *  Ex: source = "123,.678."
     *      patterns = "." e ",."
     *      
     *      indices resultantes = "3","4" e "8"
     * 
     * LEFT - Obtem os indices dos primeiros que aparecerem do início do texto ao fim.
     *  Ex: source = "123,.678."
     *      patterns = "." e ",."
     *      
     *      indices resultantes = "3" e "8"
     * RIGHT - Obtem os indices dos primeiros que aparecem do fim ao início do texto
     *  Ex: source = "123,.678."
     *      patterns = "." e ",."
     *      
     *      indices resultantes = "3" e "8"
     * 
     */
    public enum ZStringSearchType{
        CUMULATIVE, LEFT, RIGHT
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private String source;
    private boolean caseSensitive;
    private String patterns[];
    private String patternsToAvoid[];
    private List<ZStringSearchResult> results;
    private int resultIndex;
    private final ZStringSearchType type;
    
    ////////////////////////////////////////////////////////////////////////////
    //CONSTRUTORES PARA STRINGS
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @param source O texto do qual deseja realizar a pesquisa
     * @param caseSensitive Se é para considerar letras maiusculas ou minusculas (true), ou não (false)
     * @param patterns Os padrões que deseja procurar no texto
     * @param patternsToAvoid Os padrões que são exceção
     * @param type O tipo de pesquisa, se deseja começar a pesquisar pela esquerda ou direita, ou se quer todos índeces.
     *               Essa ultima opção é mais usada quando quer pesquisar mais de um padrão
     */
    private ZStringSearch(String source,
                          boolean caseSensitive,
                          String patterns[],
                          String patternsToAvoid[],
                          ZStringSearchType type){
        this.source = source;
        this.caseSensitive = caseSensitive;
        this.patterns = patterns;
        this.patternsToAvoid = patternsToAvoid;
        this.type = type;
        search();
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern,String patternToAvoid){
        this(string,caseSensitive,new String[]{pattern},new String[]{patternToAvoid});
    }
    
    public ZStringSearch(String string,boolean caseSensitive,String pattern){
        this(string,caseSensitive,new String[]{pattern},new String[]{});
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PÚBLICOS
    ////////////////////////////////////////////////////////////////////////////
    public List<String> listPatternsToVoid(String pattern){
        List<String> result = new ArrayList<>();
        for (String toVoid:patternsToAvoid){
            
            if (caseSensitive){
                if (toVoid.contains(pattern)){
                    result.add(toVoid);
                }
            } else {
                if (toVoid.toLowerCase().contains(pattern.toLowerCase())){
                    result.add(toVoid);
                }
            }
            
        }
        return result;
    }
    
    public List<ZStringSearchResult> listResults(){
        return results;
    }
    
    public ZStringSearchResult getFirstResult(){
        return results.get(0);
    }
    
    public ZStringSearchResult getLastResult(){
        return results.get(results.size()-1);
    }
    
    public int countResults(){
        return results.size();
    }
    
    public boolean first(){
        resultIndex = 0;
        return resultIndex>-1;
    }
    
    public boolean last(){
        resultIndex = results.size()-1;
        return resultIndex>-1;
    }
    
    public boolean next(){
        if (resultIndex<results.size()-1){
            resultIndex++;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean prev(){
        if (resultIndex>0){
            resultIndex--;
            return true;
        } else {
            return false;
        }
    }
    
    public ZStringSearchResult getResult(){
        if (resultIndex<0){
            resultIndex = 0;
        }
        return results.get(resultIndex);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private void search(){
        List<ZStringSearchResult> possible = new ArrayList<>();
        int oldSize;
        int offset = 0;
        do {
            int index;
            oldSize = possible.size();
            for (String pattern:patterns){
                boolean add = true;
                if (caseSensitive){
                    index = source.indexOf(pattern,offset);
                } else {
                    index = source.toLowerCase().indexOf(pattern.toLowerCase(),offset);
                }

                for (String toVoid:listPatternsToVoid(pattern, patternsToAvoid)){
                    int difference;

                    if (caseSensitive){
                        difference = toVoid.indexOf(pattern);
                    } else {
                        difference = toVoid.toLowerCase().indexOf(pattern.toLowerCase());
                    }

                    if (caseSensitive){
                        add = !(source.startsWith(toVoid, index-difference));
                    } else {
                        add = !(source.toLowerCase().startsWith(toVoid.toLowerCase(), index-difference));
                    }
                    
                    if (!add){
                        break;
                    }

                }
                
                if (add&&index>-1){
                    ZStringSearchResult r = new ZStringSearchResult(this,pattern,index);
                    possible.add(r);
                    offset += index+1;
                }
            }
        } while (oldSize!=possible.size());
        results = possible;
        resultIndex = -1;
    }
    
    private List<ZStringSearchResult> search(String pattern){
        List<ZStringSearchResult> result = new ArrayList<>();
        List<String> patternsToVoid = listPatternsToVoid(pattern);
        int offset = 0;
        int index;
        while (true) {
            boolean add = true;
            if (caseSensitive){
                index = source.indexOf(pattern,offset);
            } else {
                index = source.toLowerCase().indexOf(pattern.toLowerCase(),offset);
            }
            
            if (index==-1){
                break;
            }

            for (String toVoid:patternsToVoid){
                int difference;

                if (caseSensitive){
                    difference = toVoid.indexOf(pattern);
                } else {
                    difference = toVoid.toLowerCase().indexOf(pattern.toLowerCase());
                }

                if (caseSensitive){
                    add = !(source.startsWith(toVoid, index-difference));
                } else {
                    add = !(source.toLowerCase().startsWith(toVoid.toLowerCase(), index-difference));
                }
                    
                if (!add){
                    break;
                }

            }

            if (add){
                ZStringSearchResult r = new ZStringSearchResult(this,pattern,index);
                result.add(r);
            }
           
            offset += index+1;
            
        }
        return result;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    
    
}
