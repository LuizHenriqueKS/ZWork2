/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.util;

import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.inject.ZInjectManager;

/**
 *
 * @author Luiz Henrique
 */
public class ZObject {
    
    public ZObject(){
        init();
    }
    
    private void init(){
        if (this instanceof ZInjectInterface){
            ZInjectManager.injectIn((ZInjectInterface)this);
        }
    }
    
}
