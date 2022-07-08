package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.event.*;

import java.awt.*;

class Cell extends Button
{
    Button btn;
    int i;
    int j;

    Cell(int _i, int _j)
    {
        btn = new Button();
        i = _i;
        j = _j;
    }

}


public class Main extends Application {
    Integer num = 0;
    Button prev = null;
    Cell cell[][];
    boolean playing = false;
    int indi;
    int indj;
    Sudoku game;

    void getNum(Button btn)
    {
        num = Integer.parseInt(btn.getText());
        //System.out.println(num);
        btn.setStyle("-fx-background-color: #FFFF00");
        if (prev != null) prev.setStyle("");
        prev = btn;
    }

    void setNum(Button btn)
    {
        //if (!playing) return;
        if (num != 0)
        {
            btn.setText(num.toString());

            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    if (cell[i][j].btn == btn)
                    {
                        if (num != game.field[cell[i][j].j][cell[i][j].i]) {
                            System.out.println(game.field[cell[i][j].j][cell[i][j].i]);
                            btn.setStyle("-fx-background-color: #FF0000");
                        }
                        else
                            btn.setStyle("-fx-background-color: #00FF00");
                    }
                }
            }
            /*
            if (num != game.field[btn.j][btn.i]) {
                System.out.println(game.field[btn.j][btn.i]);
                btn.btn.setStyle("-fx-background-color: #FF0000");
            }
            else
                btn.btn.setStyle("-fx-background-color: #00FF00");

             */

        }
    }

    void startGame()
    {
        game = new Sudoku(25);
        game.createField();
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j< 9; j++)
            {
                if (game.field[i][j] != 0) cell[j][i].btn.setText(game.field[i][j].toString());
            }
        }
        game.solv(game.field, game.allowedHorizont, game.allowedVertical, game.allowedSquare);
        game.printField();

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Hard Sudoku");

        Group group = new Group();

        GridPane field = new GridPane();
        field.setGridLinesVisible(true);
        field.setPadding(new Insets(100,0,0,100));

        cell = new Cell[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                cell[i][j] = new Cell(i,j);
                cell[i][j].btn.setMinHeight(50);
                cell[i][j].btn.setMinWidth(50);
                indi = i;
                indj = j;
                cell[i][j].btn.setOnAction(event -> setNum( (Button) event.getSource()));
                field.add(cell[i][j].btn,i,j);
            }
        }
        group.getChildren().add(field);

        HBox horButs = new HBox();
        horButs.setLayoutX(100);
        horButs.setLayoutY(600);
        final Button choice[] = new Button[9];
        for (Integer i = 0; i < 9; i++)
        {
            Integer j = i + 1;
            choice[i] = new Button(j.toString());
            choice[i].setMinWidth(50);
            choice[i].setMinHeight(50);
            choice[i].setOnAction(event -> getNum((Button) event.getSource()));
            horButs.getChildren().add(choice[i]);
        }
        group.getChildren().add(horButs);


        Button btn = new Button("играть");
        btn.setLayoutX(50);
        btn.setLayoutY(700);
        btn.setOnAction(event -> startGame());

        group.getChildren().add(btn);

        Line lineh[] = new Line[2];
        Line linev[] = new Line[2];
        for (int i = 0; i < 2; i++)
        {
            lineh[i] = new Line(100,250 + 150*i,550,250 + 150*i);
            linev[i] = new Line(250 + 150*i,100,250+150*i,550);
            group.getChildren().addAll(lineh[i],linev[i]);
        }


        Scene scene = new Scene(group, 650,800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
