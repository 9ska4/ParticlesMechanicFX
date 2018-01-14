package com.ehr.particles;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticlesApplication extends Application {

    private Emitter emitter = new FireEmitter();

    private List<Particle> particles = new ArrayList<>();

    private GraphicsContext g;

    private void onUpdate() {
        g.setGlobalAlpha(1.0);
        g.setGlobalBlendMode(BlendMode.SRC_OVER);
        g.setFill(Color.BLACK);
        g.fillRect(0,0,600,600);

        particles.addAll(emitter.emit(300, 300));
        for (Iterator<Particle> iterator = particles.iterator(); iterator.hasNext();) {
            Particle p = iterator.next();
            p.update();

            if (!p.isAlive()) {
                iterator.remove();
                System.out.println("Particle removed");
                continue; // goes to next iterator particle ^up
            }

            p.render(g);
        }
    }

    private Parent createParent() {
        Pane root = new Pane();
        root.setPrefSize(600,600);

        Canvas canvas = new Canvas(600,600);
        g = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        return  root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createParent()));
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
