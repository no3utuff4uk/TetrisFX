/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.figures;

import java.util.Random;

/**
 *
 * @author torne
 */
public class Square extends Figures{

    byte color;
    
    public Square(int posX)
    {
        super.posX = posX;
        super.posY = 0;
        super.mask = new byte[2][2];
        super.sizeX = 2;
        super.sizeY = 2;
        color = (byte)((new Random().nextInt() & 0xFF)%7 + 1);
        
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
            super.mask[i][j] = color; 
    }
    
    @Override
    public void rotate() {
        return;
    }
    
}
