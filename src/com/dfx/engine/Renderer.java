package com.dfx.engine;

import java.awt.image.DataBufferInt;

public class Renderer
{
    private int pxWidth, pxHeight;
    private int[] px;
    
    public Renderer(Engine engine)
    {
        pxWidth = engine.getWidth();
        pxHeight = engine.getHeight();
        
        //Get pixel value of the window/ canvas in RGB value
        px = ((DataBufferInt)engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }
    
    //Simple clear screen
    private void clear()
    {
        for (int i = 0; i != px.length; i++)
            px[i] = 0xff000000;
    }
    
    //Create a rectangle filled with solid color
    public void fillRect(int offX, int offY, int width, int height, int color)
    {
        //Check for out of bounds coordinates
        if (offX > pxWidth || offY > pxHeight || color == 0xffff00ff)
            return;
        
        if (offX < 0)
        {
            width += offX;
            offX = 0;
        }
        
        if (offY < 0)
        {
            height += offY;
            offY = 0;
        }
        
        if (offY < 0)
        {    
            offY = 0;
        }
        
        if (offX + width > pxWidth)
            width -= (offX + width) - pxWidth;
        
        if (offY + height > pxHeight)
            height -= (offY + height) - pxHeight;
        
        //If clear, start modifying the pixel data
        for (int x = offX; x != offX + width; x++)
        {
            for (int y = offY; y != offY + height; y++)
            {
                px[x + y * pxWidth] = color;
            }
        }
    }
    
    //Same as fillRect but with a simple pattern. Experiment with the separation value for different patterns
    public void texturedRect(int offX, int offY, int width, int height, int color, int separation)
    {
        if (offX > pxWidth || offY > pxHeight || color == 0xffff00ff)
            return;
        
        if (offX < 0)
        {
            width += offX;
            offX = 0;
        }
        
        if (offY < 0)
        {
            height += offY;
            offY = 0;
        }
        
        if (offY < 0)
        {    
            offY = 0;
        }
        
        if (offX + width > pxWidth)
            width -= (offX + width) - pxWidth;
        
        if (offY + height > pxHeight)
            height -= (offY + height) - pxHeight;
        
        for (int x = offX; x != offX + width; x++)
        {
            for (int y = offY; y != offY + height; y++)
            {
                if ((x + y * pxWidth) % separation == 0 )
                    px[x + y * pxWidth] = 0xffffffff;
                else
                    px[x + y * pxWidth] = color;
            }
        }
    }
    
    public void setPixel(int x, int y, int value)
    {
        if ((x < 0 || x >= pxWidth || y < 0 || y >= pxHeight) || value == 0xffff00ff)
            return;
        
        px[x + y * pxWidth] = value;
    }
    
    public void drawImage(Image image, int offX, int offY)
    {
        for (int y = 0; y != image.getHeight(); y++)
        {
            for (int x = 0; x != image.getWidth(); x++)
            {
                setPixel(x + offX, y + offY, image.getPx()[x + y * image.getWidth()]);
            }
        }
    }
    
    //Update the renderer
    public void update()
    {
        clear();
    }
}
