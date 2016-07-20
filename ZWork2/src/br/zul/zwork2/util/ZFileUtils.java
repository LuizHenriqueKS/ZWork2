package br.zul.zwork2.util;

import java.io.File;

/**
 *
 * @author Luiz Henrique
 */
public class ZFileUtils {

    /**
     * 
     * RETORNA A EXTENSÃO SEM PONTO
     * 
     *  EX: 
     *      getFileExtension("Arquivo.txt");
     *  SAÍDA:
     *      "txt"
     * 
     * @param filename
     * @return 
     */
    public static String getFileExtension(String filename){
        
        int index = filename.indexOf(".");
        
        //VERIFICA SE POSSUI EXTENSÃO
        if (index<0){
            //SE NÃO POSSUI RETORNA VAZIO
            return "";
        }
        
        //OBTEM A EXTENSÃO
        String extension = filename.substring(index);
        
        //RETORNA A EXTENSÃO
        return extension;
        
    }
    
    public static String getFileExtension(File file){
        
        return getFileExtension(file.getAbsolutePath());
        
    }
    
}
