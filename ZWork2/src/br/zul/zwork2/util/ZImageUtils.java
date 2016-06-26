/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.util;

import br.zul.zwork2.log.ZLogger;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Luiz Henrique
 */
public class ZImageUtils {
    
    /**
     * LÊ UMA IMAGEM APARTIR DE UM ARQUIVOS
     * 
     * @param file O ARQUIVO QUE VOCÊ QUER LER
     * @return RETORNA A IMAGEM OU LANÇA UM EXCEPTION SE NÃO CONSEGUIR LER
     */
    public static BufferedImage loadImage(File file) {
        
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(ZImageUtils.class,"loadImage(File file)");
        
        //VERIFICA SE O ARQUIVO NÃO ESTÁ NULL
        if (file==null){
            throw logger.error.prepareException("Foi informado como arquivo um null!");
        }
        
        //TENTA LER A IMAGEM
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            //LANÇA UMA EXCEPTION SE NÃO CONSEGUIU LER A IMAGEM
            throw logger.error.prepareException(e,"Não foi possível ler a imagem '%s'!",file.getAbsolutePath());
        }
        
        //RETORNA A IMAGEM LIDA
        return image;
        
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage loadImage(File file, int width, int height) {
        BufferedImage image = loadImage(file);
        return resizeImage(image, width, height);
    }

    public static ImageIcon loadImageIcon(File file) {
        return new ImageIcon(loadImage(file));
    }

    public static ImageIcon loadImageIcon(File file, int width, int height) {
        return new ImageIcon(loadImage(file, width, height));
    }

}
