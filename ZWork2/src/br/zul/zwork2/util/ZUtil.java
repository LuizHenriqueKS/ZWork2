package br.zul.zwork2.util;

/**
 *
 * @author skynet
 */
public class ZUtil {
    
    public static boolean isEmptyOrNull(Object objs[]){
        return objs==null||objs.length==0;
    }
    
    public static boolean isEmptyOrNull(String str){
        return str==null||str.isEmpty();
    }
    
}
