package br.zul.zwork2.io;

import br.zul.zwork2.util.ZImageUtils;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author luiz.silva
 */
public class ZImage {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Image image;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZImage(File file){
        this.image = ZImageUtils.loadImage(file);
    }
    
    public ZImage(Image image){
        this.image = image;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public boolean isValid(){
        return this.image!=null;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ESTÁTICOS
    //==========================================================================
    public static ZImage fromResource(String resource){
        ZPath path = new ZPath(resource);
        String formattedPath = path.format(ZPath.ZPathPattern.LINUX);
        URL url = ZImage.class.getResource(formattedPath);
        Image image = null;
        if (url!=null){
            image = Toolkit.getDefaultToolkit().getImage(url);
        }
        return new ZImage(image);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    
   
}
