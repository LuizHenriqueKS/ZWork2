package br.zul.zwork2.iterator;

import br.zul.zwork2.html.ZHtml;
import br.zul.zwork2.html.ZHtmlElement;
import br.zul.zwork2.html.ZHtmlTag;
import br.zul.zwork2.key.ZKeyList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlIterator extends ZIterator<ZKeyList<Integer>,ZHtmlElement> {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private ZKeyList<Integer> key;
    private final List<ZHtmlElement> elementList;
    private IteratorState state;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlIterator(Collection<ZHtmlElement> elementCollection){
        this.elementList = new ArrayList<>(elementCollection);
        this.state = IteratorState.BEFORE_FIRST;
        this.key = new ZKeyList<>();
        this.key.addKey(-1);
    }
    
    public ZHtmlIterator(ZHtml html){
        this(html.listElements());
    }
    
    public ZHtmlIterator(ZHtmlTag htmlTag){
        this(htmlTag.listElements());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
   
    /**
     * <div>
     *  <p>
     *      <b></b>
     *  </p>
     * </div>
     * 
     * Ex:
     *  div -> p -> b
     * 
     * @param asc
     * @return Retorna a lista na ordem escolhida. 
     *         Retorna null 
     */
    public List<ZHtmlElement> listPath(boolean asc){
        List<ZHtmlElement> list = new ArrayList<>();
        ZHtmlElement element = getValue();
        if (element==null){
            return null;
        }
        while (!elementList.contains(element)) {
            list.add(element);
            element = element.getParent();
        }
        list.add(element);
        if (asc){
            Collections.reverse(list);
        }
        return list;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZKeyList<Integer> getKey() {
        return key;
    }

    @Override
    public void setKey(ZKeyList<Integer> key) {
        this.key = key;
    }

    @Override
    public boolean isKeyValid() {
        if (key==null||key.isEmpty()){
            return false;
        } 
        
        int i = 0;
        ZHtmlElement element = null;
        
        do {
        
            int k = key.getKey(i);

            if (k<0){
                return false;
            }
            
            if (element==null){
                if (k>=elementList.size()){
                    return false;
                }
            } else {
                if (!element.isTag()){
                    return key.size()-1==i;
                } else if (k>=element.asTag().countElements()){
                    return false;
                }
            }

            if (element==null){
                element = elementList.get(k);
            } else {
                element = element.asTag().getElement(k);
            }
            
            i++;
                
        } while (i<key.size());
        
        return true;
    }

    @Override
    public ZHtmlElement getValue() {
        if (!isKeyValid()){
            return null;
        }
        ZHtmlElement element = elementList.get(key.getKey(0));
        for (int i=1;i<key.size();i++){
            element = element.asTag().getElement(key.getKey(i));
        }
        return element;
    }

    @Override
    public boolean next() {
       return next(1);
    }

    @Override
    public boolean before() {
        return next(-1);
    }

    @Override
    public void beforeFirst() {
        key = new ZKeyList<>();
        key.addKey(-1);
        state = IteratorState.BEFORE_FIRST;
    }

    @Override
    public void afterLast() {
        key = new ZKeyList<>();
        key.addKey(elementList.size());
        state = IteratorState.AFTER_LAST;
    }

    @Override
    public boolean hasNext() {
        List<ZHtmlElement> list = listPath(true);
        if (list==null){
            return key.getKey(0)<0;
        }
        for (int i=0;i<list.size();i++){
            int index,size;
            ZHtmlElement element = list.get(i);
            if (i==0){
                index = elementList.indexOf(element);
                size = elementList.size();
            } else {
                index = element.getIndex();
                size = element.getParent().countElements();
            }
            if (index<size-1){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasBefore() {
        List<ZHtmlElement> list = listPath(true);
        if (list==null){
            return key.getKey(0)>=elementList.size();
        }
        for (int i=0;i<list.size();i++){
            int index;
            ZHtmlElement element = list.get(i);
            if (i==0){
                index = elementList.indexOf(element);
            } else {
                index = element.getIndex();
            }
            if (index>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public IteratorState getState() {
        return state;
    }
    
    private boolean next(int step){
        int index = key.getLastIndex();
        int p = 1;
        boolean subnivels = true;
        List<ZHtmlElement> path = listPath(false);
        key = key.copy();
        do {
            //ENTRA NO SUB NIVEIS
            if (subnivels){
                if (step>0){
                    if (path!=null&&!path.isEmpty()&&path.get(0).isTag()){
                        if (path.get(0).asTag().hasElements()){
                            key.addKey(path.get(0).asTag().getElement(0).getIndex()-1);
                            index = key.getLastIndex();
                        }
                    }
                }
            }
            
            //AGORA PODE ENTRAR NOS SUBNIVEIS DE NOVO
            subnivels = true;
            
            //AVANÇA UM PASSO (SE ENTROU NUM SUBNÍVEL ELE VAI ESTAR EM -1)
            int k = key.getKey(index)+step;
            key.setKey(index,k);
            
            if (step>0){
                
                //VERIFICA O INDEX DO PRIMERIO NIVEL
                if (index==0&&k>=elementList.size()){
                    //ALCANÇOU O FINAL
                    state = IteratorState.AFTER_LAST;
                    return false;
                //VERIFICA O INDICE DOS PROXIMOS NIVEIS
                } else if (index>0&&path!=null&&path.size()>1&&(!path.get(p).isTag()||k>=path.get(p).asTag().countElements())){
                    //ALCANÇOU O FINAL DO SUBNIVEL
                    key.removeKey(index);
                    index--;
                    p++;
                    //NÃO PODE ENTRAR NO SUBNIVEL (SE ENTRAR VAI SER LOOP INFINITO)
                    subnivels = false;
                } else {
                    break;
                }

            } else {

                if (index==0&&k<0){
                    state = IteratorState.BEFORE_FIRST;
                    return false;
                } else if (index>0&&k<0){
                    key.removeKey(index);
                    index--;
                    p++;
                } else {
                    break;
                }
                
            }
            
            p--;
            path = listPath(false);
            
        } while (p>0);
        if (key.size()==1&&key.getKey(0)<=-1){
            state = IteratorState.BEFORE_FIRST;
        } else if (key.size()==1&&key.getKey(0)>=elementList.size()){
            state = IteratorState.AFTER_LAST;
        } else if (!hasBefore()){
            state = IteratorState.FIRST;
        } else if (!hasNext()){
            state = IteratorState.LAST;
        } else {
            state = IteratorState.MIDDLE;
        }
        return getValue()!=null;
    }
    
}
