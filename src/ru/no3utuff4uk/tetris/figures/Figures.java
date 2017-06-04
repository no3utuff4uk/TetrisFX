/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.figures;

/**
 * Супер класс для фигур
 * @author torne
 */
public abstract class Figures {

    protected int color;

    protected byte[][] mask;

    protected int posX;
    protected int sizeX;

    protected int posY;
    protected int sizeY;

    protected int rotateOffsetX;
    protected int rotateOffsetY;
    
    protected boolean isRotated;

    public boolean isRotated()
    {
        return isRotated;
    }

    public int getRotateOffsetX()
    {
        return rotateOffsetX;
    }

    public int getRotateOffsetY()
    {
        return rotateOffsetY;
    }
    
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public abstract void setRotated();
    
    public abstract byte[][] getRotatedMask();

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
