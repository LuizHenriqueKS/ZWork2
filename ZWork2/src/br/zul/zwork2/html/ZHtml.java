package br.zul.zwork2.html;

import br.zul.zwork2.xml.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiz.silva
 */
public class ZHtml {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<ZHtmlElement> elementList;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtml(){
        this.elementList = new ArrayList<>();
        init();
    }
    
    public ZHtml(String html){
        this(new ZXml(html));
        init();
    }
    
    public ZHtml(ZXml xml){
        this();
        for (ZXmlElement element:xml.listElements()){
            elementList.add(ZHtmlElement.parseElement(element));
        }
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        for (ZHtmlElement element:elementList){
            element.setHtml(this);
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String getContent(){
        StringBuilder xml = new StringBuilder();
        for (ZHtmlElement element:elementList){
            xml.append(element);
        }
        return xml.toString();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String toString(){
        return getContent();
    }
    
    //==========================================================================
    //MÉTODOS PARA ELEMENTOS
    //==========================================================================
    public List<ZHtmlElement> listElements(){
        return new ArrayList<>(elementList);
    }
    
    public void addElement(ZHtmlElement element){
        elementList.add(element);
        element.setHtml(this);
    }
    
    public void removeElement(ZHtmlElement element){
        elementList.remove(element);
        element.setHtml(null);
    }
    
    public void removeElement(int index){
        elementList.get(index).setHtml(null);
        elementList.remove(index);
    }
    
    public ZHtmlElement getElement(int index){
        return elementList.get(index);
    }
   
    public int countElements(){
        return elementList.size();
    }
    
}
