package graphics;

import javafx.application.*;
import javafx.stage.*;


public class Main extends Application{

    public static void main(String[] args) {
        System.out.println( "Main method inside Thread : " +  Thread.currentThread().getName());
        launch(args);
    }

    @Override
    public void start(Stage args) throws Exception {
        System.out.println( "Start method inside Thread : " +  Thread.currentThread().getName());
    }
}