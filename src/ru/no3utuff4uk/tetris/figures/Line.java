/*
 * 
 */
package ru.no3utuff4uk.tetris.figures;

import java.util.Random;

/**
 *
 * @author torne
 */
public class Line extends Figures {

    private final byte[][] rotatedMask;
    private final byte[][] unRotatedMask;   
    
    private int color;
    public Line(int posX) {
        super.isRotated = false;
        super.posX = posX;
        super.posY = 0;

        super.sizeX = 4;
        super.sizeY = 1;

        super.rotateOffsetX = 1;
        super.rotateOffsetY = -1;
        
        color = (byte) ((new Random().nextInt() & 0xFF) % 7 + 1);
        
        unRotatedMask = new byte[1][4];
        for (int i = 0; i < 4; i++) {
            unRotatedMask[0][i] = (byte)color;
        }

        super.mask = unRotatedMask;

        rotatedMask = new byte[4][1];
        for (int i = 0; i < 4; i++) {
            rotatedMask[i][0] = (byte) color;
        }
    }

    @Override
    public void setRotated() {
        if (isRotated) {
            super.mask = unRotatedMask;
            sizeX = 4;
            sizeY = 1;
            isRotated = false;
            posX -= rotateOffsetX;
            posY -= rotateOffsetY;
        } else {
            super.mask = rotatedMask;
            sizeX = 1;
            sizeY = 4;
            isRotated = true;
            posX += rotateOffsetX;
            posY += rotateOffsetY;
        }

    }

    @Override
    public byte[][] getRotatedMask() {
        if(isRotated)
            return unRotatedMask;
        return rotatedMask;
    }

    

}
