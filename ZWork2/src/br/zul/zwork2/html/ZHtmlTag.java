package br.zul.zwork2.html;

import br.zul.zwork2.xml.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author luiz.silva
 */
public class ZHtmlTag extends ZHtmlElement {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String tagName;
    private Map<String, String> attributesMap;
    private List<ZHtmlElement> elementList;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlTag() {
        this.tagName = "undefined";
        this.attributesMap = new HashMap<>();
        this.elementList = new ArrayList<>();
        init();
    }

    public ZHtmlTag(String html) {
        this(new ZXmlTag(html));
        init();
    }
    
    public ZHtmlTag(ZXmlTag xmlTag){
        this();
        this.tagName = xmlTag.getTagName();
        this.attributesMap = xmlTag.attributeMap();
        
        for (ZXmlElement element:xmlTag.listElements()){
            this.elementList.add(ZHtmlElement.parseElement(element));
        }
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        for (ZHtmlElement element:elementList){
            element.setHtml(getHtml());
            element.setParent(this);
        }
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    @Override
    public String toString() {
        StringBuilder xml = new StringBuilder();

        //INICIA
        xml.append("<");
        xml.append(tagName);

        //PASSA OS ATRIBUTOS
        for (Entry<String, String> attribute : attributesMap.entrySet()) {
            xml.append(" ");
            xml.append(attribute.getKey());
            xml.append("=\"");
            xml.append(attribute.getValue().replace("\"", "\\\""));
            xml.append("\"");
        }

        //FINALIZA
        if (elementList.isEmpty()) {
            xml.append("/>");
        } else {
            xml.append(">");
            xml.append("\r\n");
            xml.append(getContent());
            xml.append("</");
            xml.append(tagName);
            xml.append(">");
        }
        xml.append("\r\n");

        return xml.toString();
    }

    public String getContent() {
        StringBuilder xml = new StringBuilder();
        for (ZHtmlElement element : elementList) {
            xml.append(element);
        }
        if (!xml.toString().endsWith("\r\n") && !xml.toString().trim().isEmpty()) {
            xml.append("\r\n");
        }
        return xml.toString().trim();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public ZHtmlElementType getType() {
        return ZHtmlElementType.TAG;
    }

    //==========================================================================
    //MÉTODOS PARA ATRIBUTOS
    //==========================================================================
    public String getAttribute(String name) {
        return attributesMap.get(name);
    }

    public void putAttribute(String name, String value) {
        attributesMap.put(name, value);
    }

    public void putAttribute(String name) {
        attributesMap.put(name, "");
    }

    public void removeAttribute(String name) {
        attributesMap.remove(name);
    }

    public boolean hasAttribute(String name) {
        return attributesMap.containsKey(name);
    }

    public Map<String, String> attributeMap() {
        return attributesMap;
    }

    public int countAttributes() {
        return attributesMap.size();
    }

    //==========================================================================
    //MÉTODOS PARA ELEMENTOS
    //==========================================================================
    public List<ZHtmlElement> listElements() {
        return new ArrayList<>(elementList);
    }

    public void addElement(ZHtmlElement element) {
        elementList.add(element);
        element.setParent(this);
        element.setHtml(getHtml());
    }

    public void removeElement(ZHtmlElement element) {
        element.setParent(null);
        element.setHtml(null);
        elementList.remove(element);
    }

    public void removeElement(int index) {
        elementList.get(index).setParent(null);
        elementList.get(index).setHtml(null);
        elementList.remove(index);
    }

    public ZHtmlElement getElement(int index) {
        return elementList.get(index);
    }

    public int countElements() {
        return elementList.size();
    }

    public boolean hasElements(){
        return !elementList.isEmpty();
    }

    //==========================================================================
    //GETTERES E SETTERS
    //==========================================================================
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
