package ru.no3utuff4uk.tetris.scene;

import java.util.Arrays;
import ru.no3utuff4uk.tetris.figures.Figures;

public class TetrisScene 
{
    private final int sceneSizeX = 30;
    private final int sceneSizeY = 50;

    public int getSceneSizeX() 
    {
        return sceneSizeX;
    }

    public int getSceneSizeY() 
    {
        return sceneSizeY;
    }
    
    private byte[][] scene;
    private Figures figure;
    private boolean needNewFigure;
    private boolean gameOver;
    
    private long score;
    private int level = 1; //Заменить

    public TetrisScene() {
        this.needNewFigure = true;
        this.scene = new byte[sceneSizeY][sceneSizeX];
    }
    
    public boolean needNewFigure()
    {
        return needNewFigure;
    }
    
    public boolean isGameOver()
    {
        return gameOver;
    }
    
    public byte[][] getScene()
    {
        return this.scene;
    }

    public void setFigure(Figures figure) {
        this.figure = figure;
        drawFigure();
        needNewFigure = false;
        gameOver = false;
    }
    
    public void rotateFigure() //сложно
    {    
        clearFigure();
        figure.rotate();
        drawFigure();
    }
    
    public synchronized void dropFigure()
    {
        if((figure.getPosY() + figure.getSizeY()) == sceneSizeY)
        {
            checkFullLine();
            needNewFigure = true;
            return;
        }
        boolean isTouched = false;
        byte[][] figureMask = figure.getMask();
        
        for(int i = 0; i < figure.getSizeY(); i++)
            for (int j = 0; j < figure.getSizeX(); j++) 
            {
                if( figureMask[i][j] != 0 &&
                    (scene[figure.getPosY() + i + 1][figure.getPosX() + j] != 0) &&
                    !(i < (figure.getSizeY() - 1) && (figureMask[i + 1][j] != 0)))
                {
                    isTouched = true;
                }
            }
        
        if(isTouched)
        {
            checkFullLine();
            
            if(figure.getPosY() ==0)
               gameOver = true;
            
            needNewFigure = true;
        }
        else
        {
            clearFigure();
            figure.setPosY(figure.getPosY() + 1);
            drawFigure();
        }
    }
    
    public synchronized void moveFigureRight()
    {
        if(figure.getPosX() + figure.getSizeX() == sceneSizeX)
            return;
        
        boolean isTouched = false;
        byte[][] figureMask = figure.getMask();
        for(int i = 0; i < figure.getSizeY(); i++)
            for (int j = 0; j < figure.getSizeX(); j++) 
            {
                if( figureMask[i][j] != 0 &&
                    !((j < figure.getSizeX() - 1) &&  figureMask[i][j + 1] != 0) &&
                    scene[figure.getPosY() + i][figure.getPosX() + j + 1] != 0)
                {
                    isTouched = true;
                }
                    
            }
        if(!isTouched)
        {
            clearFigure();
            figure.setPosX(figure.getPosX() + 1);
            drawFigure();
        }
    }
    
    public synchronized void moveFigureLeft()
    {
        if(figure.getPosX() == 0)
            return;
        
        boolean isTouched = false;
        byte[][] figureMask = figure.getMask();
        for(int i = 0; i < figure.getSizeY(); i++)
            for (int j = 0; j < figure.getSizeX(); j++) 
            {
                if( figureMask[i][j] != 0 &&
                    !(j > 0 &&  figureMask[i][j - 1] != 0) &&
                    scene[figure.getPosY() + i][figure.getPosX() + j - 1] != 0)
                {
                    isTouched = true;
                }
                    
            }
        if(!isTouched)
        {
            clearFigure();
            figure.setPosX(figure.getPosX() - 1);
            drawFigure();
        }
    }

    /**
     * Отрисовка фигуры на сцене
     */
    private synchronized void drawFigure() 
    {
        byte[][] figureMask = figure.getMask();
        for(int i = 0; i < figure.getSizeX(); i++)
            for (int j = 0; j < figure.getSizeY(); j++) {
                if(figureMask[j][i] != 0)
                    scene[figure.getPosY() + j][figure.getPosX() + i] = figureMask[j][i];
            }
    }

    
    /**
     * Проверка заполненности линии
     */
    private void checkFullLine() 
    {
        for(int i = sceneSizeY -1; i >= 0; i--)
        {
            boolean isFullLine = true;
            for (int j = 0; j < sceneSizeX; j++) 
            {
                if(scene[i][j] == 0)
                    isFullLine = false;
            }
            if(isFullLine)
                deleteLine(i);
        }
            
    }
    
    /**
     * Удалаяет линию если она заполнена
     * @param line линия для проверки
     */
    private void deleteLine(int line) 
    {
        for(int i = line - 1; i >= 0; i--)
        {
            scene[i + 1] = Arrays.copyOf(scene[i],sceneSizeX);
        }
        
        //Считаем очки
        score += 100 * level;
        
        checkFullLine();
    }

    /**
     * Удалаяет фигуру со сцены
     */
    private void clearFigure() {
        byte[][] figureMask = figure.getMask();
        for(int i = 0; i < figure.getSizeX(); i++)
            for (int j = 0; j < figure.getSizeY(); j++) {
                if(figureMask[j][i] != 0)
                    scene[figure.getPosY() + j][figure.getPosX() + i] = 0;
            }
    }

    public long getScore() {
        return score;
    }
    
    
}
