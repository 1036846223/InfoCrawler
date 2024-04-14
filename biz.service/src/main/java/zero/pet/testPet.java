package zero.pet;

import javax.swing.*;
import java.awt.*;

public class testPet {

    private static void cgJLabelImg(JLabel jLabel, String imgUrl) {
        ImageIcon icon = new ImageIcon(imgUrl);
        int picWidth = icon.getIconWidth(), pinHeight = icon.getIconHeight();
        icon.setImage(icon.getImage().getScaledInstance(picWidth, pinHeight, Image.SCALE_DEFAULT));
        jLabel.setBounds(0, 0, picWidth, pinHeight);
        jLabel.setIcon(icon);
    }

    public static void main(String[] args) {
        JLabel jLabel = new JLabel();
        // 动画线程 这里用到了JDK8的lambda表达式
        new Thread(() -> {
            try {
                cgJLabelImg(jLabel, "/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test1.png");
                Thread.sleep(250);
                cgJLabelImg(jLabel, "/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test2.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // 框体透明
//        jLabel.setUndecorated(true); // 取消窗口标题栏
        jLabel.setBackground(new Color(0, 0, 0, 0));// 背景透明
    }
}
