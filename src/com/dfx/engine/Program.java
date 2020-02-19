package com.dfx.engine;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.io.File;

public class Program
{
    private DecimalFormat formatZ, formatProb;
    private Image graph;
    private Scanner in;
    
    private boolean lessThan = false, point2 = false;
    private int pointX, pointX2, separation;
    private double mouseX, probVal, tempZ, mouseX2, probVal2, tempZ2;
    private double[] probList = new double[410];
    
    public Program()
    {   
        probVal = probVal2 = 0;
        tempZ = tempZ2 = 0;
        formatZ = new DecimalFormat("0.00");
        formatProb = new DecimalFormat("0.00000");
        graph = new Image("/graph.png");
    }
    
    //Set up the program
    public void onCreate(Engine engine)
    {   
        separation = 35;
        
        try
        {
            in = new Scanner(new File("res/Prob_Values.txt"));
        }
        
        catch (Exception err)
        {
            err.printStackTrace();
        }
        
        //Populating the probability list
        int i = 0;
        while (in.hasNextDouble())
        {
            probList[i] = in.nextDouble();
            i++;
        }
    }
    
    public void onUserUpdate(Engine engine)
    {
        engine.getRenderer().fillRect(0, 0, engine.getWidth(), engine.getHeight(), 0xffddddff);
        boolean convert = false, convert2 = false;
        
        //Check for specific key presses to change the equation
        if (engine.getInput().getKey() == KeyEvent.VK_LEFT)
            lessThan = true;
        
        if (engine.getInput().getKey() == KeyEvent.VK_RIGHT)
            lessThan = false;
        
        if (engine.getInput().getMouseButton() == 1)
        {
            point2 = true;
            pointX2 = engine.getInput().getClickX();
        }
        
        if (engine.getInput().getMouseButton() == 3)
            point2 = false;
        
        //Calculation for a single Z score
        if (!point2)
        {   
            mouseX = Math.round((engine.getInput().getMouseX() / ((engine.getWidth() - 1) / 8.18) - 4.09) * 100.00) / 100.00;
        
            if (mouseX < 0)
            {
                convert = true;
                tempZ = mouseX * -1.0;
            }
        
            else
                tempZ = mouseX;
        
            int i = 0;
            for (double z = 0.00; z < 4.1; z = z + 0.01)
            {
                if (Math.round(z * 100.0) / 100.0 == tempZ && i < probList.length)
                {
                    probVal = probList[i];
                
                    if (convert && !lessThan)
                        probVal = Math.round((1 - probVal) * 100000.0) / 100000.0;
                
                    if (!convert && lessThan && tempZ > 0)
                        probVal = Math.round((1 - probVal) * 100000.0) / 100000.0;
                
                    break;
                }
            
                i++;
            }
        
            if (lessThan)
            {
                engine.getWindow().getZLabel().setText("P(Z < " + formatZ.format(mouseX) + ") = " + formatProb.format(probVal));
                engine.getRenderer().texturedRect(0, 0, engine.getInput().getMouseX(), engine.getHeight(), 0xff7777ff, separation);
            }
            
            else
            {
                engine.getWindow().getZLabel().setText("P(Z > " + formatZ.format(mouseX) + ") = " + formatProb.format(probVal));
                engine.getRenderer().texturedRect(engine.getInput().getMouseX(), 0, engine.getWidth() - engine.getInput().getMouseX(), engine.getHeight(), 0xff7777ff, separation);
            }
        }
        
        //Calculation for two Z score values
        else
        {
            mouseX = Math.round((engine.getInput().getMouseX() / ((engine.getWidth() - 1) / 8.18) - 4.09) * 100.00) / 100.00;
            pointX = engine.getInput().getMouseX();
            mouseX2 = Math.round((pointX2 / ((engine.getWidth() - 1) / 8.18) - 4.09) * 100.00) / 100.00;
            
            if (mouseX < 0)
            {
                convert = true;
                tempZ = mouseX * -1.0;
            }
        
            else
                tempZ = mouseX;
            
            int i = 0;
            for (double z = 0.00; z < 4.1; z = z + 0.01)
            {
                if (Math.round(z * 100.0) / 100.0 == tempZ && i < probList.length)
                {
                    probVal = probList[i];
                
                    if (!convert && tempZ > 0)
                        probVal = Math.round((1 - probVal) * 100000.0) / 100000.0;
                
                    break;
                }
            
                i++;
            }
            
            if (mouseX2 < 0)
            {
                convert2 = true;
                tempZ2 = mouseX2 * -1.0;
            }
        
            else
                tempZ2 = mouseX2;
            
            i = 0;
            for (double z = 0.00; z < 4.1; z = z + 0.01)
            {
                if (Math.round(z * 100.0) / 100.0 == tempZ2 && i < probList.length)
                {
                    probVal2 = probList[i];
                
                    if (!convert2 && tempZ2 > 0)
                        probVal2 = Math.round((1 - probVal2) * 100000.0) / 100000.0;
                
                    break;
                }
            
                i++;
            }
            
            if (pointX < pointX2)
            {
                engine.getRenderer().texturedRect(pointX, 0, pointX2 - pointX, engine.getHeight(), 0xff7777ff, separation);
                engine.getWindow().getZLabel().setText("P(" + formatZ.format(mouseX) + " < Z < " + formatZ.format(mouseX2) + ") = " + formatProb.format(probVal2 - probVal));
            }
            else
            {
                engine.getRenderer().texturedRect(pointX2, 0, pointX - pointX2, engine.getHeight(), 0xff7777ff, separation);
                engine.getWindow().getZLabel().setText("P(" + formatZ.format(mouseX2) + " < Z < " + formatZ.format(mouseX) + ") = " + formatProb.format(probVal - probVal2));
            }
            
            engine.getRenderer().fillRect(pointX2 - 2, 0, 4 , engine.getHeight(), 0x00000000);
        }
        
        //For added visibility, create a 4px black line on the cursor.
        engine.getWindow().getStatLabel().setText("V1.0");
        engine.getRenderer().fillRect(engine.getInput().getMouseX() - 2, 0, 4 , engine.getHeight(), 0x00000000);
        engine.getRenderer().drawImage(graph, 0, 0);
    }
    
    public static void main(String[] args)
    {
        new Engine(new Program());
    }
}
