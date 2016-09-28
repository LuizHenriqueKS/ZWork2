package br.zul.zwork2.html;

import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.xml.*;
import java.util.List;

/**
 *
 * @author luiz.silva
 */
public abstract class ZHtmlElement {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private ZHtmlTag parent;
    private ZHtml html;
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTÁTICOS
    //==========================================================================
    public static ZHtmlElement parseElement(String html){
        return parseElement(ZXmlElement.parseElement(html));
    }
    
    public static ZHtmlElement parseElement(ZXmlElement element){
        ZLogger logger = new ZLogger(ZHtmlElement.class,"parseElement(ZXmlElement element)");
        switch (element.getType()){
            case TAG:
                return new ZHtmlTag(element.asTag());
            case VALUE:
                return new ZHtmlValue(element.asValue());
            default:
                throw logger.error.prepareException("Não foi possível converter para um elemento html: %s",element.toString());
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZHtmlElementType getType();
    
    //==========================================================================
    //MÉTODOS PÚBLICOS CONVERSORES
    //==========================================================================
    public ZHtmlTag asTag(){
        return (ZHtmlTag)this;
    }
    
    public ZHtmlValue asValue(){
        return (ZHtmlValue)this;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public boolean isType(ZHtmlElementType type){
        return getType().equals(type);
    }
    
    public boolean isValue(){
        return isType(ZHtmlElementType.VALUE);
    }
    
    public boolean isTag(){
        return isType(ZHtmlElementType.TAG);
    }
    
    public Integer getIndex(){
        if (getParent()==null){
            if (getHtml()==null){
                return null;
            }
            return getHtml().listElements().indexOf(this);
        } else {
            return getParent().listElements().indexOf(this);
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public ZHtmlTag getParent() {
        return parent;
    }
    public void setParent(ZHtmlTag parent) {
        this.parent = parent;
    }

    public ZHtml getHtml() {
        return html;
    }
    public void setHtml(ZHtml html) {
        this.html = html;
    }

}
