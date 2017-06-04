/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.figures;

import java.util.Random;

/**
 *
 * @author Андрей
 */
public class TFigure extends Figures {

    private int direction;

    private final byte[][] mask0;
    private final byte[][] mask1;
    private final byte[][] mask2;
    private final byte[][] mask3;

    private byte[][] generateRotetedMask(int direction, boolean changeOffset) {
        switch (direction) {
            case 0:
                if (changeOffset) {
                    super.rotateOffsetX = 1;
                    super.rotateOffsetY = 0;
                }
                return mask0;
                
            case 1:
                if (changeOffset) {
                    super.rotateOffsetX = -1;
                    super.rotateOffsetY = 1;
                }
                return mask1;
            case 2:
                if (changeOffset) {
                    super.rotateOffsetX = 0;
                    super.rotateOffsetY = -1;
                }
                return mask2;
            case 3:
                if (changeOffset) {
                    super.rotateOffsetX = 0;
                    super.rotateOffsetY = 0;
                }
                return mask3;
        };
        return null;
    }

    private int color;

    public TFigure(int posX) {
        super.posX = posX;
        super.posY = 0;

        super.sizeX = 3;
        super.sizeY = 2;

        super.rotateOffsetX = 1;
        super.rotateOffsetY = 0;

        color = (byte) ((new Random().nextInt() & 0xFF) % 7 + 1);

        isRotated = true;

        direction = 0;

        byte[][] mask0 = {
            {0, (byte) color, 0},
            {(byte) color, (byte) color, (byte) color}
        };
        this.mask0 = mask0;

        byte[][] mask1 = {
            {(byte) color, 0},
            {(byte) color, (byte) color},
            {(byte) color, 0}
        };
        this.mask1 = mask1;

        byte[][] mask2 = {
            {(byte) color, (byte) color, (byte) color},
            {0, (byte) color, 0}
        };
        this.mask2 = mask2;

        byte[][] mask3 = {
            {0, (byte) color},
            {(byte) color, (byte) color},
            {0, (byte) color}
        };
        this.mask3 = mask3;
        
        super.mask = mask0;
    }

    @Override
    public void setRotated() {
        if (direction % 2 != 0) {
            sizeX = 3;
            sizeY = 2;
        } else {
            sizeX = 2;
            sizeY = 3;
        }

        
        //TODO: rotateOffset
        if (direction < 4) {
            direction++;
        } else {
            direction = 0;
        }

        super.mask = generateRotetedMask(direction, true);
    }

    @Override
    public byte[][] getRotatedMask() {
        return generateRotetedMask(direction + 1, false);
    }

}
