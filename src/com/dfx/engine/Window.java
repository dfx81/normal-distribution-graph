package com.dfx.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window
{
    private Engine engine;
    private JFrame frame;
    private JPanel topPanel, panel, statPanel;
    private JLabel zLabel, statLabel;
    private JMenuBar menuBar;
    private JMenu menu, about;
    private JMenuItem credits, help, contact, changelog;
    private Dimension resolution;
    private Canvas canvas;
    private BufferedImage image;
    private BufferStrategy buffer;
    private Graphics graphics;
    
    public Window(Engine engine)
    {
    	try
    	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
    	
    	catch (Exception err)
    	{
			err.printStackTrace();
		}
    	
        this.engine = engine;
        resolution = new Dimension(engine.getWidth(), engine.getHeight());
        
        menuBar = new JMenuBar();
        menu = new JMenu("Info");
        about =  new JMenu("About");
        credits = new JMenuItem("Credits");
        changelog = new JMenuItem("Changelog");
        help = new JMenuItem("Help");
        contact = new JMenuItem("Contact");
        
        help.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(frame, "Press [LEFT] or [RIGHT] to change between 'less than' or 'more than' mode."
                        + "\nClick [LMB] to set a second Z score or change its position."
                        + "\nClick [RMB] to remove the second Z score.");
            }
        });
        
        credits.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(frame, "Developed by Danial Fitri 'dfx' Ghazali (F1T2 - 2018/2019)\n\n"
                        + "Developed for Kedah Matriculation College's Mathematics Unit\n");
            }
        });
        
        contact.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(frame, "Any bugs or problems encountered in this program can be reported\n"
                        + "by emailing me at [danialfitrighazali@gmail.com]\n"
                        + "Please include detail regarding the problem such as screenshots to\n"
                        + "aid the debugging process.\n\n"
                        + "Future updates to the program will be made available for download at my site\n"
                        + "[https://dfx81.github.io]");
            }
        });
        
        changelog.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(frame, "Standard Normal Distribution : Interactive Graph\n\n"
                        + "V1.0 : Initial release");
            }
        });
        
        menu.add(help);
        menu.add(changelog);
        about.add(credits);
        about.add(contact);
        menu.add(about);
        menuBar.add(menu);
        
        frame = new JFrame(this.engine.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("res/icon.png").getImage());
        frame.setJMenuBar(menuBar);
        frame.setSize(1000, 720);
        frame.setLocationRelativeTo(null);
        
        zLabel = new JLabel();
        zLabel.setHorizontalAlignment(JLabel.CENTER);
        zLabel.setFont(new Font("Serif", Font.BOLD, 24));
        zLabel.setForeground(new Color(0xff5555ff));
        statLabel = new JLabel();
        statLabel.setHorizontalAlignment(JLabel.CENTER);
        statLabel.setFont(new Font("Serif", Font.BOLD, 14));
        statLabel.setForeground(new Color(0xff5555ff));
        statLabel.setVerticalAlignment(JLabel.BOTTOM);
        
        topPanel = new JPanel();
        panel = new JPanel();
        statPanel = new JPanel();
        
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(resolution);
        
        statPanel.setLayout(new BorderLayout());
        statPanel.add(zLabel, BorderLayout.CENTER);
        statPanel.add(statLabel, BorderLayout.LINE_END);
        statPanel.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        
        //Set up canvas to display graphics and trap inputs
        canvas = new Canvas();
        canvas.setPreferredSize(resolution);
        canvas.setFocusable(true);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(canvas);
        panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(panel, BorderLayout.CENTER);
        topPanel.add(statPanel, BorderLayout.PAGE_END);
        
        frame.add(topPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
        //Create buffer for canvas to draw graphics
        image = new BufferedImage(engine.getWidth(), engine.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
        graphics = buffer.getDrawGraphics();
    }
    
    //Update the canvas
    public void update()
    {
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        buffer.show();
    }

    public Canvas getCanvas()
    {
        return canvas;
    }

    public JLabel getZLabel()
    {
        return zLabel;
    }

    public JLabel getStatLabel()
    {
        return statLabel;
    }
    
    public JFrame getFrame()
    {
        return frame;
    }

    public BufferedImage getImage()
    {
        return image;
    }
}
