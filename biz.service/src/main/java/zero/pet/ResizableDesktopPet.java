package zero.pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class ResizableDesktopPet extends JFrame {
    private JLabel petLabel;
    private Point mouseOffset;
 
    public ResizableDesktopPet(int width, int height) {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(width, height);
 
        petLabel = new JLabel();
        ImageIcon normalIcon = new ImageIcon("/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test1.png"); //替换为你自己的宠物正常状态的图片路径
        ImageIcon hoverIcon = new ImageIcon("/Users/chaser/InfoCrawler/biz.service/src/main/java/zero/pet/test2.png"); //替换为你自己的宠物鼠标悬停状态的图片路径
        petLabel.setIcon(normalIcon);
 
        // 监听鼠标按下事件
        petLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseOffset = e.getPoint(); // 记录当前鼠标位置和窗口左上角的距离
            }
 
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseOffset = null; // 重置鼠标位置和窗口左上角的距离
                }
            }
        });
 
        // 监听鼠标移动事件
        petLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseOffset != null) {
                    Point mousePosition = e.getLocationOnScreen();
                    setLocation(mousePosition.x - mouseOffset.x, mousePosition.y - mouseOffset.y); // 移动窗口
                    petLabel.setIcon(hoverIcon); // 切换为悬停状态的图片
                }
            }
 
            @Override
            public void mouseMoved(MouseEvent e) {
                petLabel.setIcon(normalIcon); // 切换回正常状态的图片
            }
        });
 
        setLayout(new BorderLayout());
        add(petLabel, BorderLayout.CENTER);
 
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResizableDesktopPet(200, 200));
    }
}