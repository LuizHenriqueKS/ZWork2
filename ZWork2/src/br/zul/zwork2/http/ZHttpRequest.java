package br.zul.zwork2.http;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZHttpRequest extends ZHttpBase{
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract ZHttpResponse send();    

}
