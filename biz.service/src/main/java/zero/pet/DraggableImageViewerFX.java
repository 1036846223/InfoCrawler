package zero.pet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DraggableImageViewerFX extends Application {

    // 存储鼠标按下时的初始坐标
    private double initialX;
    private double initialY;

    @Override
    public void start(Stage primaryStage) {
        // 加载图片
        Image image = new Image("file:path/to/your/image.png"); // 替换为你的图片路径
        ImageView imageView = new ImageView(image);

        // 创建根节点并添加 ImageView
        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        // 创建场景并设置舞台
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Draggable Image Viewer");

        // 鼠标按下事件监听器
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // 记录鼠标按下时的初始坐标
                initialX = event.getSceneX();
                initialY = event.getSceneY();
            }
        });

        // 鼠标拖拽事件监听器
        scene.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // 计算拖拽偏移量
                double offsetX = event.getScreenX() - initialX;
                double offsetY = event.getScreenY() - initialY;

                // 更新舞台位置
                primaryStage.setX(primaryStage.getX() + offsetX);
                primaryStage.setY(primaryStage.getY() + offsetY);

                // 更新初始坐标
                initialX = event.getScreenX();
                initialY = event.getScreenY();
            }
        });

        primaryStage.setScene(scene);
        // 设置窗口始终在最上方
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
