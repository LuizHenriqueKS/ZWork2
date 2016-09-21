package br.zul.zwork2.html;

import br.zul.zwork2.xml.*;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlValue extends ZHtmlElement {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String content;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlValue(){
        this.content = "";
    }
    
    public ZHtmlValue(String content){
        this.content = content;
    }
    
    public ZHtmlValue(ZXmlValue xmlValue){
        this.content = xmlValue.getContent();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZHtmlElementType getType() {
        return ZHtmlElementType.VALUE;
    }
    
    @Override
    public String toString(){
        return content;
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
