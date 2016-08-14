package br.zul.zwork2.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * @param patternsToAvoid Os padrões que são exceção (pode ser informado como null)
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
    
    /**
     * Verifica se não foi encontrado nenhum padrão.
     * @return TRUE = NENHUM PADRÃO ENCONTRADO,
     *         FALSE = PELO MENOS UM DOS PADRÕES FOI ENCONTRADO
     */
    public boolean isEmpty(){
        return results.isEmpty();
    }

    ////////////////////////////////////////////////////////////////////////////
    //MÉTODOS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
    private void search() {
        //PREPARA UM CONJUNTO QUE NÃO VAI REPETIR OS RESULTADOS
        Set<ZStringSearchResult> resultSet = new HashSet<>();
        
        //BUSCA CADA PADRÃO (EVITANDO AQUELES QUE DEVE EVITAR)
        for (String pattern:patterns){
            List<ZStringSearchResult> result = search(pattern);
            if (!result.isEmpty()){
                resultSet.addAll(result);
            }
        }
        //TRANSFORMA O CONJUNTO EM UMA LISTA
        List<ZStringSearchResult> result = new ArrayList<>(resultSet);
        
        //SE FOI INFORMADO UM TIPO, TRATA OS RESULTADOS
        if (type!=null){
            //ORDENA OS RESULTADOS PELO PADRÕES MAIORES
            Collections.sort(result,new Comparator<ZStringSearchResult>(){
                @Override
                public int compare(ZStringSearchResult t, ZStringSearchResult t1) {
                    return t1.length()-t.length();
                } 
            });
            //TRATA OS RESULTADOS QUE COLIDEM
            int i;
            for (i=result.size()-1;i>=0;i--){
                ZStringSearchResult firstResult = result.get(i);
                for (int j=i-1;j>=0;j--){
                    ZStringSearchResult secondResult = result.get(j);
                    if (firstResult.collidesWith(secondResult)){
                        ZStringSearchTypeArgs args = new ZStringSearchTypeArgs(firstResult, secondResult);
                        type.filter(args);
                        if (args.isRemoveFirst()&&args.isRemoveSecond()){
                            result.remove(i);
                            result.remove(j);
                            i--;
                            break;
                        } else if (args.isRemoveFirst()){
                            result.remove(i);
                            break;
                        } else if (args.isRemoveSecond()){
                            result.remove(j);
                            i--;
                        }
                    }
                }
            }
        }
        
        //ORDENA PELO ÍNDICE E PADRÕES MENORES
        Collections.sort(result, new Comparator<ZStringSearchResult>(){
            @Override
            public int compare(ZStringSearchResult o1, ZStringSearchResult o2) {
                int index1 = o1.getIndex();
                int index2 = o2.getIndex();
                int result = index1-index2;
                if (result==0){
                    return o1.length()-o2.length();
                } else {
                    return result;
                }
            }
            
        });
        
        results = result; 
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

            offset = index + 1;

        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////
    //VARIÁVEIS PRIVADOS
    ////////////////////////////////////////////////////////////////////////////
}
