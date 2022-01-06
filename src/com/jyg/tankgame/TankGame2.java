package com.jyg.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame2 extends JFrame {
    MyPanel mp;
    static boolean flag;
    public static void main(String[] args) {
        System.out.println("是否继续上一句游戏, 请输入“是”或“否”");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.next();
            if (next.equals("是")) {
                flag = true;
                System.out.println("已为您继续上一句游戏");
                break;
            } else if (next.equals("否")) {
                flag = false;
                System.out.println("已为您开始新游戏");
                break;
            } else {
                System.out.println("请输入“是”或者“否”");
            }
        }
        TankGame2 tankGame2 = new TankGame2();
    }

    public TankGame2() {
        mp = new MyPanel(flag);
        this.add(mp);
        this.setSize(1300, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        new Thread(mp).start();

        //在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口关闭，游戏结束！");
                mp.keepRecord();
                System.exit(0);
            }
        });
    }
}
