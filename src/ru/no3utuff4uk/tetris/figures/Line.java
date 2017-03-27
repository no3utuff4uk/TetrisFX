/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.figures;

import java.util.Arrays;

/**
 *
 * @author torne
 */
public class Line extends Figures{

    private byte[][] rotatedMask;
    private byte[][] unRotatedMask;
    
    private boolean isRotated;
    public Line(int posX)
    {
        this.isRotated = false;
        super.posX = posX;
        super.posY = 0;
        
        super.sizeX = 4;
        super.sizeY = 1;
        
        unRotatedMask = new byte[1][4];
        for(int i = 0; i < 4; i++)
            unRotatedMask[0][i] = 0x01; 
        
        super.mask = unRotatedMask;
        
        rotatedMask = new byte[4][1];
        for(int i = 0; i < 4; i++)
            rotatedMask[i][0] = 0x01;
    }
    
    @Override
    public void rotate() {
        if(isRotated)
        {
            super.mask = unRotatedMask;
            sizeX = 4;
            sizeY = 1;
        }
        else
        {
            super.mask = rotatedMask;
            sizeX = 1;
            sizeY = 4;
        }
            
    }

      
    
}
