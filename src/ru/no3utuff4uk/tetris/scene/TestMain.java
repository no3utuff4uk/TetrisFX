/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris.scene;

import java.util.Random;
import ru.no3utuff4uk.tetris.figures.Figures;
import ru.no3utuff4uk.tetris.figures.Line;
import ru.no3utuff4uk.tetris.figures.Square;

/**
 *
 * @author torne
 */
public class TestMain {

    public static void main(String[] args) {
        TetrisScene instance = new TetrisScene();

        Figures figure = new Line(0);
        //figure.rotate();
        instance.setFigure(figure);

        for (int t = 0; t < 1000; t++) {
            byte[][] tmp = instance.getScene();
            System.out.println("_____________________________");
            for (int i = 0; i < 40; i++) {
                System.out.print(i + 1);
                for (int j = 0; j < 20; j++) {
                    System.out.print(tmp[i][j] != 0 ? 'X' : ' ');
                }
                System.out.println('|');
            }
            System.out.println("_____________________________");

            figure.getPosY();
            instance.dropFigure();

            instance.moveFigureLeft();

            if (instance.needNewFigure()) {
                instance.setFigure(new Square((new Random().nextInt() & 0xff) % (7)));
            }
        }
    }
}
