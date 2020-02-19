package com.dfx.engine;

public class Engine implements Runnable
{
    private Thread thread;
    private Window window;
    private Input input;
    private Program program;
    private Renderer renderer;
    
    //Set the update rate to 60 times per second
    private final double UPDATE_CAP = 1.0 / 60.0;
    
    private boolean running = false;
    private int width = 1000, height = 640;
    private String title = "Standard Normal Distribution";
    
    //Create a thread
    public Engine(Program program)
    {
        this.program = program;
        thread = new Thread(this);
        window = new Window(this);
        input = new Input(this);
        renderer = new Renderer(this);
        thread.start();
    }
    
    //Thread update timing
    public void run()
    {
        program.onCreate(this);
        double timeI = System.nanoTime() / Math.pow(10, 9);
        double timeF = 0, timeElapsed = 0;
        running = true;
        
        while (running)
        {
            timeF = System.nanoTime() / Math.pow(10, 9);
            timeElapsed += timeF - timeI;
            timeI = timeF;
            
            if (timeElapsed >= UPDATE_CAP)
            {
                timeElapsed = 0;
                update();
            }
            
            else
            {
                try
                {
                    Thread.sleep(1);
                }
                catch (Exception err)
                {
                    err.printStackTrace();
                }
            }
        }
    }
    
    //Update the processes and engine logic
    private void update()
    {
        program.onUserUpdate(this);
        window.update();
        renderer.update();
    }
    
    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Window getWindow()
    {
        return window;
    }

    public Input getInput()
    {
        return input;
    }

    public Renderer getRenderer()
    {
        return renderer;
    }
}
