package com.dfx.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class Input implements MouseMotionListener, MouseInputListener, KeyListener
{
    private int mouseX, mouseY, mouseButton, clickX, clickY, key;
    
    //Add event listeners on the canvas to trap inputs
    public Input(Engine engine)
    {
        engine.getWindow().getCanvas().addMouseMotionListener(this);
        engine.getWindow().getCanvas().addMouseListener(this);
        engine.getWindow().getCanvas().addKeyListener(this);
    }
    
    public void keyPressed(KeyEvent event)
    {
        key = event.getKeyCode();
    }

    public void keyReleased(KeyEvent event)
    {
        
    }

    public void keyTyped(KeyEvent event)
    {
        
    }

    public void mouseDragged(MouseEvent event)
    {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    public void mouseMoved(MouseEvent event)
    {
        mouseX = event.getX();
        mouseY = event.getY();
    }
    
    public void mouseClicked(MouseEvent event)
    {
        mouseButton = event.getButton();
        clickX = event.getX();
        clickY = event.getY();
    }

    public void mouseEntered(MouseEvent event)
    {
        
    }

    public void mouseExited(MouseEvent event)
    {
        
    }

    public void mousePressed(MouseEvent event)
    {
        mouseButton = event.getButton();
    }

    public void mouseReleased(MouseEvent event)
    {
        
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    public int getMouseButton()
    {
        return mouseButton;
    }

    public int getKey()
    {
        return key;
    }

    public int getClickX()
    {
        return clickX;
    }

    public int getClickY()
    {
        return clickY;
    }
}
