/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Luiz Henrique
 */
public class ZGzip {
    
    public static byte[] gzip(byte[] content) throws IOException{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        GZIPOutputStream gos=new GZIPOutputStream(baos);

        ByteArrayInputStream bais=new ByteArrayInputStream(content);
        byte[ ] buffer=new byte[1024];
        int n;
        while((n=bais.read(buffer))!=-1){
                gos.write(buffer, 0, n);
        }
        gos.flush();
        gos.close();
        return baos.toByteArray();
    }
	
    public static byte[] unGzip(byte[] content) throws IOException{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        GZIPInputStream gis=new GZIPInputStream(new ByteArrayInputStream(content));
        byte[] buffer=new byte[1024];
        int n;
        while((n=gis.read(buffer))!=-1){
                baos.write(buffer, 0, n);
        }

        return baos.toByteArray();
    }
	
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    
}
