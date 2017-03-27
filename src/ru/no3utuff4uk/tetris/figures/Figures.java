/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.figures;

import javafx.scene.layout.GridPane;

/**
 *
 * @author torne
 */
public abstract class Figures {
    
    protected int color;
    
    protected byte[][] mask;
    
    protected int posX;
    protected int sizeX;
    
    protected int posY;
    protected int sizeY;

    public int getPosX() 
    {
        return posX;
    }

    public int getPosY() 
    {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public abstract void rotate();    

    public int getSizeX() {
        return this.sizeX;
    }

    public byte[][] getMask() {
        return this.mask;
    }

    public int getSizeY() {
        return this.sizeY;
    }
}
