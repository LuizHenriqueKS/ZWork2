package br.zul.zwork2.filter;



import br.zul.zwork2.html.ZHtml;
import br.zul.zwork2.html.ZHtmlElement;
import br.zul.zwork2.html.ZHtmlTag;
import br.zul.zwork2.iterator.ZHtmlIterator;
import br.zul.zwork2.key.ZKeyList;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlFilter extends ZFilter<ZKeyList<Integer>, ZHtmlElement>{
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlFilter(ZHtml html){
        super(new ZHtmlIterator(html));
    }
    
    public ZHtmlFilter(ZHtmlTag htmlTag){
        super(new ZHtmlIterator(htmlTag));
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    @Override
    public boolean filter(ZKeyList<Integer> key, ZHtmlElement value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
