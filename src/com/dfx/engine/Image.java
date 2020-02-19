package com.dfx.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image
{
    private int width, height;
    private int[] px;
    
    public Image(String path)
    {
        BufferedImage image = null;
        
        try
        {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        }
        
        catch (IOException err)
        {
            err.printStackTrace();
        }
        
        width = image.getWidth();
        height = image.getHeight();
        px = image.getRGB(0, 0, width, height, null, 0, width);
        
        image.flush();
    }

    public int[] getPx()
    {
        return px;
    }

    public void setPx(int[] px)
    {
        this.px = px;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
