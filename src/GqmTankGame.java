import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
public class GqmTankGame extends JFrame {
    public static void main(String[] args) {
        GqmTankGame gqmTankGame01 = new GqmTankGame();
    }

    Scanner myScanner = new Scanner(System.in);

    static boolean loop = false;

    //定义MyPanel
    MyPanel myPanel = null;

    public GqmTankGame() {
        do {
            System.out.print("请输入选择(1. 开始新游戏；2. 继续上一局游戏)：");
            String key = myScanner.next();
            myPanel = new MyPanel(key);
        } while(loop);
        Thread thread = new Thread(myPanel);    //将myPanel放入到Thread
        thread.start();    //启动MyPanel线程
        this.add(myPanel);
        this.addKeyListener(myPanel);    //让JFrame监听myPanel的键盘事件
        this.setSize(1500,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中增加相应的关闭窗口处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}