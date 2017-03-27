package ru.no3utuff4uk.tetris;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.no3utuff4uk.tetris.figures.Figures;
import ru.no3utuff4uk.tetris.figures.Square;
import ru.no3utuff4uk.tetris.scene.TetrisScene;

public class GameTask extends Task{

    private GraphicsContext graphicsContext;
    private GraphicsContext nextFigureContext;
    private Integer speedLevel;
    private boolean isPaused;
    
    private TetrisScene instance;
    private Timer drawTimer;
    private TimerTask drawTimerTask;
    private Timer dropDownTimer;
    private TimerTask dropDownTimerTask;
    
    private boolean needToStop = false;

    public GameTask(GraphicsContext graphicsContext, GraphicsContext nextFigureContext, Integer speedLevel) 
    {
        this.graphicsContext = graphicsContext;
        this.nextFigureContext = nextFigureContext;
        this.speedLevel = speedLevel;
        
        this.instance = new TetrisScene();
        this.isPaused = false;
        
        
    }
  
    public boolean isGameOver()
    {
        return instance.isGameOver();
    }
    
    public void moveLeft()
    {
        if(!isPaused)
            instance.moveFigureLeft();
    }
    
    public void moveRight()
    {
        if(!isPaused)
            instance.moveFigureRight();
    }
    
    public void stopGame()
    {
        needToStop = true;
    }
    
    public void dropDown()
    {
        if(!instance.needNewFigure() && !isPaused) 
        {            
            instance.dropFigure();
        }
    }
    
    public void dropDownFast() 
    {
        while(!instance.needNewFigure() && !isPaused)
        {
            instance.dropFigure();
        }
    }
    
    public long getScore()
    {
        return instance.getScore();
    }
    
    private synchronized void drawField()
    {
        byte[][] tmp = instance.getScene();
        graphicsContext.clearRect(0, 0, instance.getSceneSizeX() * 10, instance.getSceneSizeY() * 10);
        for (int i = 0; i < instance.getSceneSizeX(); i++) {
            for (int j = 0; j < instance.getSceneSizeY(); j++) {
                if(tmp[j][i] != 0)
                {
                    switch(tmp[j][i])
                    {
                        case 0x01: graphicsContext.setFill(Color.RED); break;
                        case 0x02: graphicsContext.setFill(Color.ORANGE); break;
                        case 0x03: graphicsContext.setFill(Color.YELLOW); break;
                        case 0x04: graphicsContext.setFill(Color.GREEN); break;
                        case 0x05: graphicsContext.setFill(Color.BLUE); break;
                        case 0x06: graphicsContext.setFill(Color.DARKBLUE); break;
                        case 0x07: graphicsContext.setFill(Color.BLUEVIOLET); break;
                    }

                    graphicsContext.fillRect(10*i, 10*j, 10, 10);
                }
            }

        }
    }
    
    private void drawNextFigure(Figures figure)
    {
        byte[][] tmp = figure.getMask();
        nextFigureContext.clearRect(0, 0, 150, 150);
        
        for (int i = 0; i < figure.getSizeX(); i++) 
            for (int j = 0; j < figure.getSizeY(); j++) {
                if(tmp[j][i] != 0)
                {
                    switch(tmp[j][i])
                    {
                        case 0x01: nextFigureContext.setFill(Color.RED); break;
                        case 0x02: nextFigureContext.setFill(Color.ORANGE); break;
                        case 0x03: nextFigureContext.setFill(Color.YELLOW); break;
                        case 0x04: nextFigureContext.setFill(Color.GREEN); break;
                        case 0x05: nextFigureContext.setFill(Color.BLUE); break;
                        case 0x06: nextFigureContext.setFill(Color.DARKBLUE); break;
                        case 0x07: nextFigureContext.setFill(Color.BLUEVIOLET); break;
                    }

                    nextFigureContext.fillRect(50*i, 50*j, 50, 50);
                }
            }
    }
    
    private void startTimers()
    {
        this.dropDownTimer = new Timer(true);
        this.drawTimer = new Timer(true);
        
        this.dropDownTimerTask = new TimerTask()
        {
            @Override
            public void run() {
                instance.dropFigure();
            }
        };
        
        this.drawTimerTask = new TimerTask() 
        {
            @Override
            public void run() {
               drawField();
            } 
        };
        
        dropDownTimer.scheduleAtFixedRate(dropDownTimerTask, 0, 500 - 50 * speedLevel);
        drawTimer.scheduleAtFixedRate(drawTimerTask, 0, 17);
    }
    
    private void stopTimers()
    {
        dropDownTimer.cancel();
        drawTimer.cancel();
    }
        
    @Override
    protected Void call() throws Exception 
    {   
        Figures nextFigure = new Square(14);
        instance.setFigure(nextFigure);
        
        nextFigure = new Square(14);
        drawNextFigure(nextFigure);
        startTimers();
        long lastScore = 0;
        while(!needToStop && !instance.isGameOver())
        {
            if(instance.needNewFigure())
            {
                if(instance.getScore() > lastScore)
                {
                    this.updateMessage(Long.toString(instance.getScore() * (speedLevel + 1)));
                }
                instance.setFigure(nextFigure);
                nextFigure = new Square(14);
                drawNextFigure(nextFigure);
            }
            Thread.yield();
        }
        
        stopTimers();
        return null;
    }

    void pauseGame() {
        if(!isPaused)
        {
            stopTimers();
            isPaused =true;
        }
        else 
        {
            startTimers();
            isPaused = false;
        }
    }
    
}
