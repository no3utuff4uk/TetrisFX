/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.no3utuff4uk.tetris;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ESCAPE;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

/**
 *
 * @author torne
 */
public class MainWindowController implements Initializable {

    private Label label;
    @FXML
    private Canvas gameField;
    @FXML
    private Label scoreLabel;
    @FXML
    private Canvas previewFigure;
    @FXML
    private Label speedLevelLabel;
    private Integer speedLevel;
    @FXML
    private Button newGameButton;
    @FXML
    private Button aboutButton;
    @FXML
    private VBox mainScene;
    @FXML
    private Label statusLabel;
    @FXML
    private Button speedPlusButton;
    @FXML
    private Button speedMinusButton;

    private GameTask gameTask;
    private Thread gameThread;

    private final KeyCombination newGameCombination;
    Popup pausedStage;

    public MainWindowController() {
        this.newGameCombination = new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN);
        this.speedLevel = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO: всплывающее окно должно следить за родительским
        pausedStage = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pause.fxml"));
        try {
            pausedStage.getContent().add((Parent) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Обработчик управления с клавиатуры
     *
     * @param event
     */
    public void keyHandler(KeyEvent event) {
        if (newGameCombination.match(event)) {
            startNewGame();
        }
        if (gameTask == null) {
            return;
        }

        if (event.getCode() == ESCAPE) {
            if (gameThread != null && gameThread.isAlive()) {
                gameTask.stopGame();
                gameOverHandler(null);
            }
        }

        gameTask.figureMover(event.getCode());
    }

    private void pauseGame() {
        if (gameThread != null
                && gameThread.isAlive()) {
            if (pausedStage.isShowing()) {
                pausedStage.hide();
            } else {
                pausedStage.show(mainScene.getScene().getWindow());
            }
            gameTask.pauseGame();

        }
    }

    /**
     * Остановка потока игры при выходе из программы
     */
    public void clearOnExit(WindowEvent event) {
        if (gameThread != null
                && gameThread.isAlive()) {
            gameTask.stopGame();
        }
        System.out.println("Thread stoped!");
    }

    /**
     * Старт новой игры
     */
    @FXML
    private void startNewGame() {
        if (gameThread != null
                && gameThread.isAlive()) {
            gameTask.stopGame();
        }

        if (pausedStage.isShowing()) {
            pausedStage.hide();
        }

        if (!(speedMinusButton.isDisable() && speedPlusButton.isDisable())) {
            speedPlusButton.setDisable(true);
            speedMinusButton.setDisable(true);
        }

        GraphicsContext fieldGraphicsContext = gameField.getGraphicsContext2D();
        GraphicsContext nextFigureContext = previewFigure.getGraphicsContext2D();

        gameTask = new GameTask(fieldGraphicsContext, nextFigureContext, speedLevel);

        gameTask.setOnSucceeded(event -> gameOverHandler((WorkerStateEvent) event));
        scoreLabel.textProperty().bind(gameTask.messageProperty());

        gameThread = new Thread(gameTask);
        gameThread.start();

    }

    private void gameOverHandler(WorkerStateEvent event) {
        speedPlusButton.setDisable(false);
        speedMinusButton.setDisable(false);

        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);

        if (gameTask.isGameOver()) {
            gameOverAlert.setTitle("Game OVER!");
            gameOverAlert.setHeaderText(null);
            gameOverAlert.initModality(Modality.APPLICATION_MODAL);
            gameOverAlert.setContentText("Your score: " + scoreLabel.getText());
            gameOverAlert.show();
        } else if (event == null) {
            gameOverAlert.setTitle("Game stoped!");
            gameOverAlert.setHeaderText(null);
            gameOverAlert.initModality(Modality.APPLICATION_MODAL);
            gameOverAlert.setContentText("Your score: " + scoreLabel.getText());
            gameOverAlert.show();
        }
    }

    @FXML
    private void changeSpeed(ActionEvent event) {
        if (((Button) event.getSource()).getText().compareTo("+") == 0) {
            if (speedLevel < 9) {
                speedLevel++;
                speedLevelLabel.setText(speedLevel.toString());
            }
        } else {
            if (speedLevel > 0) {
                speedLevel--;
                speedLevelLabel.setText(speedLevel.toString());
            }
        }
    }

}
