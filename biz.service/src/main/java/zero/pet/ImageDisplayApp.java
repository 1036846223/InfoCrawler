package zero.pet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.List;

public class ImageDisplayApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建布局容器
        StackPane root = new StackPane();

        // 创建并添加静态图片
        ImageView staticImageView = new ImageView(new Image(new File("/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test1.png").toURI().toString()));
        root.getChildren().add(staticImageView);

        // 创建并添加 GIF 动图
        // 注意：这里的 'frame1.png', 'frame2.png', ..., 'frameN.png' 需要是你的 GIF 动画的每一帧图片
        Image[] animationImages = new Image[]{
                new Image(new File("/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test2.png").toURI().toString()),
                // ... 添加更多帧
        };
        ImageView animationImageView = new ImageView();
        animationImageView.setImage(animationImages[0]);

        // 添加 GIF 动画效果
        animationImageView.setSmooth(false); // 禁用平滑处理，这通常用于动画
        new AnimationTimer() {
            private int frameIndex = 0;

            @Override
            public void handle(long now) {
                animationImageView.setImage(animationImages[(frameIndex++) % animationImages.length]);
            }
        }.start();

        // 创建场景，并将布局容器添加到场景中
        Scene scene = new Scene(root, 300, 200);

        // 鼠标按下和触摸事件监听器
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.MIDDLE) {
                handleDrag(primaryStage, event);
            }
        });

        scene.setOnTouchPressed(event -> {
            handleDrag(primaryStage, event);
        });


        // 将窗口置于最上层
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        // 设置舞台并显示应用程序
//        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleDrag(Stage primaryStage, MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.MIDDLE) {
            double x = event.getSceneX();
            double y = event.getSceneY();
            primaryStage.setX(x + primaryStage.getX());
            primaryStage.setY(y + primaryStage.getY());
        }
    }

    private void handleDrag(Stage primaryStage, TouchEvent event) {
        TouchPoint touchPoint = event.getTouchPoints().get(0); // 获取第一个触摸点
        double x = touchPoint.getSceneX();
        double y = touchPoint.getSceneY();
        primaryStage.setX(x);
        primaryStage.setY(y);
    }


    public static void main(String[] args) {
        launch(args);
    }


}