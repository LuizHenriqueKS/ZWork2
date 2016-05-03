package br.zul.zwork2.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe usada para fazer pesquisas em Strings Ex: source = "1.3..6.8" patterns
 * = "." patternsToVoid = ".."
 *
 * indices resultantes = "1 e 6"
 *
 * @author skynet
 */
public class ZStringSearch {

    ////////////////////////////////////////////////////////////////////////////
    //ENUMS
    ////////////////////////////////////////////////////////////////////////////
    /**
     * RIGHT - Obtem os indices dos primeiros
     * que aparecem do fim ao início do texto Ex: source = "123,.678." patterns
     * = "." e ",."
     *
     * indices resultantes = "4" e "8"
     *
     */
    public enum ZStringSearchType {
        /**
         * CUMULATIVE - Obtem todos os indices sem se importar com quem vem
         * primeiro ou se um padrão faz parte de outro. Ex: source = "123,.678."
         * patterns = "." e ",."
         *
         * indices resultantes = "3","4" e "8"
         *
         */
        CUMULATIVE,
        /**
         * LEFT - Obtem os indices dos primeiros que aparecerem do início do
         * texto ao fim. Ex: source = "123,.678." patterns = "." e ",."
         *
         * indices resultantes = "3" e "8" 
         */
        LEFT,
        /**
        * RIGHT - Obtem os indices dos primeiros
        * que aparecem do fim ao início do texto Ex: source = "123,.678." patterns
        * = "." e ",."
        *
        * indices resultantes = "4" e "8"
        *
        */
        RIGHT
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
     * Construtor
     *
     * @param source O texto do qual deseja realizar a pesquisa
     * @param caseSensitive Se é para considerar letras maiusculas ou minusculas
     * (true), ou não (false)
     * @param patterns Os padrões que deseja procurar no texto
     * @param patternsToAvoid Os padrões que são exceção
     * @param type O tipo de pesquisa, se deseja começar a pesquisar pela
     * esquerda ou direita, ou se quer todos índeces. Essa ultima opção é mais
     * relevante quando quer pesquisar mais de um padrão
     */
    public ZStringSearch(String source,
            boolean caseSensitive,
            String patterns[],
            String patternsToAvoid[],
            ZStringSearchType type) {
        this.source = source;
        this.caseSensitive = caseSensitive;
        this.patterns = patterns;
        this.patternsToAvoid = patternsToAvoid;
        this.type = type;
        
        if (this.patternsToAvoid==null){
            this.patternsToAvoid = new String[0];
        }
        
        search();
    }

    public ZStringSearch(String string,
            boolean caseSensitive,
            String pattern,
            String patternToAvoid,
            ZStringSearchType type) {
        this(string, caseSensitive, new String[]{pattern}, new String[]{patternToAvoid}, type);
    }

    public ZStringSearch(String string,
            boolean caseSensitive,
            String pattern,
            ZStringSearchType type) {
        this(string, caseSensitive, new String[]{pattern}, new String[]{}, type);
    }

    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PÚBLICOS
    ////////////////////////////////////////////////////////////////////////////
    public List<String> listPatternsToVoid(String pattern) {
        List<String> result = new ArrayList<>();
        for (String toVoid : patternsToAvoid) {

            if (caseSensitive) {
                if (toVoid.contains(pattern)) {
                    result.add(toVoid);
                }
            } else if (toVoid.toLowerCase().contains(pattern.toLowerCase())) {
                result.add(toVoid);
            }

        }
        return result;
    }

    public List<ZStringSearchResult> listResults() {
        return results;
    }

    public ZStringSearchResult getFirstResult() {
        return results.get(0);
    }

    public ZStringSearchResult getLastResult() {
        return results.get(results.size() - 1);
    }

    public int countResults() {
        return results.size();
    }

    public boolean first() {
        resultIndex = 0;
        return resultIndex > -1;
    }

    public boolean last() {
        resultIndex = results.size() - 1;
        return resultIndex > -1;
    }

    public boolean next() {
        if (resultIndex < results.size() - 1) {
            resultIndex++;
            return true;
        } else {
            return false;
        }
    }

    public boolean prev() {
        if (resultIndex > 0) {
            resultIndex--;
            return true;
        } else {
            return false;
        }
    }

    public ZStringSearchResult getResult() {
        if (resultIndex < 0) {
            resultIndex = 0;
        }
        return results.get(resultIndex);
    }
    
    public ZStringSearchResult getResult(int index) {
        return results.get(index);
    }

    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private void search() {
        Map<String,List<ZStringSearchResult>> map = new HashMap<>();
        
        for (String pattern:patterns){
            List<ZStringSearchResult> result = search(pattern);
            if (!result.isEmpty()){
                map.put(pattern, result);
            }
        }
        
        List<ZStringSearchResult> result = new ArrayList<>();
        
        switch (type){
            case CUMULATIVE:
                for (List<ZStringSearchResult> list:map.values()){
                    result.addAll(list);
                } 
                break;
            case LEFT:
                result.addAll(filterLeft(map));
                break;
            case RIGHT:
                result.addAll(filterRight(map));
                break;
        }
        
        Collections.sort(result, new Comparator<ZStringSearchResult>(){
            @Override
            public int compare(ZStringSearchResult o1, ZStringSearchResult o2) {
                int index1 = o1.getIndex();
                int index2 = o2.getIndex();
                return index1-index2;
            }
            
        });
        
        results = result; 
    }
    
     private List<ZStringSearchResult> filterRight(Map<String,List<ZStringSearchResult>> map){
        ZStringSearchResult major;
        List<ZStringSearchResult> result = new ArrayList<>();
        while (true){
            major = null;
            //BUSCA O MENOR ATUALMENTE
            for (List<ZStringSearchResult> list:map.values()){
                ZStringSearchResult r = getMinor(list);
                if (r==null){
                    continue;
                }
                if (major==null){
                    major = r;
                } else if ((r.getIndex()+r.getPattern().length())>(major.getIndex()+major.getPattern().length())){
                    major = r;
                }
            }
            
            //SE NÃO FOI ENCONTRADO MAIS NENHUM MENOR ENCERRA O LOOP
            if (major==null){
                break;
            }
            
            //ADICIONA O MENOR NA LISTA DE RESULTADO
            result.add(major);
            
            //REMOVE OS MENORES OU IGUAL
            for (List<ZStringSearchResult> list:map.values()){
                for (int i=list.size()-1;i>=0;i--){
                    ZStringSearchResult r = list.get(i);
                    if ((r.getIndex()+r.getPattern().length())<=(major.getIndex()+major.getPattern().length())){ //TODO VERIFICAR SE É MENOR OU MENOR E IGUAL
                        list.remove(i);
                    }
                }
            }
            
        }
        return result;
    }
    
    private List<ZStringSearchResult> filterLeft(Map<String,List<ZStringSearchResult>> map){
        ZStringSearchResult minor;
        List<ZStringSearchResult> result = new ArrayList<>();
        while (true){
            minor = null;
            //BUSCA O MENOR ATUALMENTE
            for (List<ZStringSearchResult> list:map.values()){
                ZStringSearchResult r = getMinor(list);
                if (r==null){
                    continue;
                }
                if (minor==null){
                    minor = r;
                } else if (r.getIndex()<minor.getIndex()){
                    minor = r;
                }
            }
            
            //SE NÃO FOI ENCONTRADO MAIS NENHUM MENOR ENCERRA O LOOP
            if (minor==null){
                break;
            }
            
            //ADICIONA O MENOR NA LISTA DE RESULTADO
            result.add(minor);
            
            //REMOVE OS MENORES OU IGUAL
            for (List<ZStringSearchResult> list:map.values()){
                for (int i=list.size()-1;i>=0;i--){
                    ZStringSearchResult r = list.get(i);
                    if (r.getIndex()<=(minor.getIndex()+minor.getPattern().length())){ //TODO VERIFICAR SE É MENOR OU MENOR E IGUAL
                        list.remove(i);
                    }
                }
            }
            
        }
        return result;
    }
    
       private ZStringSearchResult getMinor(List<ZStringSearchResult> list){
        ZStringSearchResult minor = null;
        for (ZStringSearchResult r:list){
            if (minor==null){
                minor = r;
            } else if (r.getIndex()<minor.getIndex()) {
                minor = r;
            }
        }
        return minor;
    }

    
    private ZStringSearchResult getMajor(List<ZStringSearchResult> list){
        ZStringSearchResult major = null;
        for (ZStringSearchResult r:list){
            if (major==null){
                major = r;
            } else if (r.getIndex()>major.getIndex()) {
                major = r;
            }
        }
        return major;
    }

    private List<ZStringSearchResult> search(String pattern) {
        List<ZStringSearchResult> result = new ArrayList<>();
        List<String> patternsToVoid = listPatternsToVoid(pattern);
        int offset = 0;
        int index;
        while (true) {
            boolean add = true;
            if (caseSensitive) {
                index = source.indexOf(pattern, offset);
            } else {
                index = source.toLowerCase().indexOf(pattern.toLowerCase(), offset);
            }

            if (index == -1) {
                break;
            }

            for (String toVoid : patternsToVoid) {
                int difference;

                if (caseSensitive) {
                    difference = toVoid.indexOf(pattern);
                } else {
                    difference = toVoid.toLowerCase().indexOf(pattern.toLowerCase());
                }

                if (caseSensitive) {
                    add = !(source.startsWith(toVoid, index - difference));
                } else {
                    add = !(source.toLowerCase().startsWith(toVoid.toLowerCase(), index - difference));
                }

                if (!add) {
                    break;
                }

            }

            if (add) {
                ZStringSearchResult r = new ZStringSearchResult(this, pattern, index);
                result.add(r);
            }

            offset += index + 1;

        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
}
