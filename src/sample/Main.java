package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {
    Scene scene1, scene2;
    public int recCounter = 0;
    public double px;
    public double py;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label label1 = new Label("WELCOME TO THE BREAKOUT GAME");
        Button button1 = new Button("Let's play the game");
        VBox layout1 = new VBox(20);

        layout1.getChildren().addAll(label1, button1);

        scene1 = new Scene(layout1,200,200);


        Button button2 = new Button("Return to the menu");


        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        primaryStage.setScene(scene1);
        Group root = new Group();
        Scene theScene = new Scene(root);
        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);
        root.getChildren().addAll(button2);
        //button1.setOnAction(e -> primaryStage.setScene(theScene))

        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(theScene);

            }
        });

        ArrayList<String> input = new ArrayList<String>();
        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        String code = event.getCode().toString();
                        if (!input.contains(code))
                            input.add(code);
                    }
                });
        theScene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        String code = event.getCode().toString();
                        input.remove(code);
                    }
                }
        );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Sprite briefcase = new Sprite();
        briefcase.setPositionX(230);
        briefcase.setPositionY(450);
        briefcase.setImage("sample/paddle.png");
        ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();
        ArrayList<Sprite> newmoneybagList = new ArrayList<Sprite>();

        for (int i = 0; i < 15; i++) {
            Sprite moneybag = new Sprite();
            moneybag.setImage("sample/paddleback.png");
            switch (recCounter) {
                case 0:
                    px = 50;
                    py = 56;
                    break;
                case 1:
                    px = 136;
                    py = 56;
                    break;
                case 2:
                    px = 223;
                    py = 56;
                    break;
                case 3:
                    px = 311;
                    py = 56;
                    break;
                case 4:
                    px = 401;
                    py = 56;
                    break;
                case 5:
                    px = 50;
                    py = 93;
                    break;
                case 6:
                    px = 136;
                    py = 93;
                    break;
                case 7:
                    px = 223;
                    py = 93;
                    break;
                case 8:
                    px = 311;
                    py = 93;
                    break;
                case 9:
                    px = 401;
                    py = 93;
                    break;
                case 10:
                    px = 50;
                    py = 131;
                    break;
                case 11:
                    px = 136;
                    py = 131;
                    break;
                case 12:
                    px = 223;
                    py = 131;
                    break;
                case 13:
                    px = 311;
                    py = 131;
                    break;
                case 14:
                    px = 401;
                    py = 131;
                    break;


            }

            recCounter++;
            moneybag.setPositionX(px);
            moneybag.setPositionY(py);
            moneybagList.add(moneybag);


        }
        Sprite ball = new Sprite();
        ball.setImage("sample/ball.png");
        ball.setPositionX(256);
        ball.setPositionY(256);
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        IntValue score = new IntValue(0);
        new AnimationTimer() {
            double multiplier = 4;
            double ballVelocityX = 50;
            double ballVelocityY = 50;



            public void handle(long currentNanoTime) {

                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;
                //briefcase movement
                root.setOnMouseMoved((evt) -> {
                    System.out.println("Mouse at: " + evt.getX() + ", " + evt.getY() + ")");
                    briefcase.setPositionX(evt.getX()-briefcase.getWidth()/2); //try to change this for center
                    if (briefcase.getPositionX()<0){
                        briefcase.setPositionX(0);
                    }
                    if (briefcase.getPositionX()>431){
                        briefcase.setPositionX(431);
                    }


                });
                //Ball
                boolean ballMoves = false;
                double anKathete = 0;
                double velocity = Math.sqrt(Math.pow(50, 2) + Math.pow(50, 2)); //WHAT is ball velocity?
                double tolerenceRange = 2.5;

                if (ballMoves == false) {
                    ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                    Random r = new Random();
                    anKathete = 40 + (60 - 40) * r.nextDouble();
                    ballMoves = true;
                }
                //Boundaries X
                if (ball.getPositionX() >= 512 - ball.getWidth()) {          //Boundary right

                    if (isPositive(ballVelocityX)) {     //randomX
                        ballVelocityX = anKathete;
                    } else {
                        ballVelocityX = anKathete * -1;
                    }
                    if (isPositive(ballVelocityX)) {     //randomY
                        ballVelocityX = getGegenkathete(anKathete, velocity);
                    } else {
                        ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                    }

                    ballVelocityX = -ballVelocityX;       //direction after collision
                    ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);        //setting new velocity

                    Random r = new Random();
                    anKathete = 40 + (60 - 40) * r.nextDouble();
                }
                if (ball.getPositionX() <= 0) {                            //Boundary left

                    if (isPositive(ballVelocityX)) {     //randomX
                        ballVelocityX = anKathete;
                    } else {
                        ballVelocityX = anKathete * -1;
                    }
                    if (isPositive(ballVelocityX)) {     //randomY
                        ballVelocityX = getGegenkathete(anKathete, velocity);
                    } else {
                        ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                    }

                    ballVelocityX = -ballVelocityX;
                    ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                    Random r = new Random();
                    anKathete = 40 + (60 - 40) * r.nextDouble();
                }
                //Boundaries Y
                if (ball.getPositionY() >= 512 + ball.getHeight()) {        //Bottom
                    primaryStage.close();
                }
                if (ball.getPositionY() <= 0) {                            //Boundary top

                    if (isPositive(ballVelocityX)) {     //randomX
                        ballVelocityX = anKathete;
                    } else {
                        ballVelocityX = anKathete * -1;
                    }
                    if (isPositive(ballVelocityX)) {     //randomY
                        ballVelocityX = getGegenkathete(anKathete, velocity);
                    } else {
                        ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                    }

                    ballVelocityY = -ballVelocityY;
                    ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                    Random r = new Random();
                    anKathete = 40 + (60 - 40) * r.nextDouble();
                }
                //Collision Briefcase-ball
                if (ball.intersects(briefcase)) {

                    if (isPositive(ballVelocityX)) {     //randomX
                        ballVelocityX = anKathete;
                    } else {
                        ballVelocityX = anKathete * -1;
                    }
                    if (isPositive(ballVelocityX)) {     //randomY
                        ballVelocityX = getGegenkathete(anKathete, velocity);
                    } else {
                        ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                    }

                    ballVelocityY = -ballVelocityY;
                    ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                    Random r = new Random();
                    anKathete = 40 + (60 - 40) * r.nextDouble();
                }

                ball.update(elapsedTime);

                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext()) {
                    Sprite moneybag = moneybagIter.next();

                    if (ball.intersects(moneybag)) {
                        if ((moneybag.getPositionX()+tolerenceRange>=ball.getPositionX()+ball.getWidth())&&         //moneybag left
                                (ball.getPositionY()<=moneybag.getPositionY()+moneybag.getHeight())&&
                                (ball.getPositionY()+ball.getHeight()>=moneybag.getPositionY())){

                            if (isPositive(ballVelocityX)) {     //randomX
                                ballVelocityX = anKathete;
                            } else {
                                ballVelocityX = anKathete * -1;
                            }
                            if (isPositive(ballVelocityX)) {     //randomY
                                ballVelocityX = getGegenkathete(anKathete, velocity);
                            } else {
                                ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                            }

                            ballVelocityX = -ballVelocityX;
                            ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                            Random r = new Random();
                            anKathete = 40 + (60 - 40) * r.nextDouble();

                        } else if((moneybag.getPositionX()+moneybag.getWidth()<=ball.getPositionX()+tolerenceRange)&&      //moneybag right
                                (ball.getPositionY()<=moneybag.getPositionY()+moneybag.getHeight())&&
                                (ball.getPositionY()+ball.getHeight()>=moneybag.getPositionY())){

                            if (isPositive(ballVelocityX)) {     //randomX
                                ballVelocityX = anKathete;
                            } else {
                                ballVelocityX = anKathete * -1;
                            }
                            if (isPositive(ballVelocityX)) {     //randomY
                                ballVelocityX = getGegenkathete(anKathete, velocity);
                            } else {
                                ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                            }

                            ballVelocityX = -ballVelocityX;
                            ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                            Random r = new Random();
                            anKathete = 40 + (60 - 40) * r.nextDouble();
                        }else {

                            if (isPositive(ballVelocityX)) {     //randomX
                                ballVelocityX = anKathete;
                            } else {
                                ballVelocityX = anKathete * -1;
                            }
                            if (isPositive(ballVelocityX)) {     //randomY
                                ballVelocityX = getGegenkathete(anKathete, velocity);
                            } else {
                                ballVelocityX = getGegenkathete(anKathete, velocity) * -1;
                            }

                            ballVelocityY = -ballVelocityY;
                            ball.setVelocity(ballVelocityX * multiplier, ballVelocityY * multiplier);

                            Random r = new Random();
                            anKathete = 40 + (60 - 40) * r.nextDouble();
                        }
                        moneybagIter.remove();
                        score.value++;

                    }
                }
                String newText;

                gc.clearRect(0, 0, 512, 512);
                briefcase.render(gc);
                ball.render(gc);

                for (Sprite moneybag : moneybagList)
                    moneybag.render(gc);
                String pointsText = "Score: " +  (100 * score.value);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);
                if (100*score.value==1500) {
                    String text = "CONGRATULATIONS YOU HAVE WON THE GAME!";
                    gc.fillText(text,30,50);
                    gc.strokeText(text,30,50);
                }
            }



        }.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    public static boolean isPositive(double number){
        if (number>=0){
            return true;
        } else{
            return false;
        }
    }

    public static double getGegenkathete(double anKathete, double velocity){
        double cosinus;
        double winkel;
        double gegenKathete;
        cosinus=anKathete/velocity;
        winkel = Math.acos(cosinus) * (180/Math.PI);
        gegenKathete=Math.sin(Math.toRadians(winkel))*velocity;
        return gegenKathete;
    }
}
