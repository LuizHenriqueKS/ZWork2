package br.zul.zwork2.iterator;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author luiz.silva
 * @param <Value>
 */
public class ZCollectionIterator<Value> extends ZListIterator<Value> {

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZCollectionIterator(Collection<Value> collection){
        super(new ArrayList<>(collection));
    }

}
