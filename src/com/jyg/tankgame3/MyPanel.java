package com.jyg.tankgame3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero;
//    Tank tank1, tank2, tank3;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    //定义三张爆炸图片
    Image image1;
    Image image2;
    Image image3;
    int enemyTankSize = 3;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
        }
//        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public MyPanel(boolean flag) {
        hero = new Hero(100, 100);
        hero.setSpeed(3);
        if (flag) {
            enemyTanks = Recorder.restore();
            enemyTankSize = enemyTanks.size();
            for (int i = 0; i < enemyTanks.size(); i++) {
                new Thread(enemyTanks.get(i)).start();
            }
        } else {
            for (int i = 0; i < enemyTankSize; i++) {
                EnemyTank enemyTank = new EnemyTank(99 * (i + 1), 0);
                enemyTank.setDirection(2);
//            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
//            enemyTank.shots.add(shot);
//            new Thread(shot).start();
                enemyTanks.add(enemyTank);
                new Thread(enemyTank).start();
            }
        }
//        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("bomb_1.gif"));
//        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        File file1 = new File("src/bomb_1.gif");
        File file2 = new File("src/bomb_2.gif");
        File file3 = new File("src/bomb_3.gif");
        try {
            image1 = ImageIO.read(file1);
            image2 = ImageIO.read(file2);
            image3 = ImageIO.read(file3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //这里播放音乐
        new AePlayWave("src/111.wav").start();

    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g,  0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        if (hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 1);
        } else {
            System.out.println("游戏结束！");
        }

        Vector<Shot> shots = hero.shots;
        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            if (shot.isLive) {
                g.fill3DRect(shot.x, shot.y, 3, 3, false);
            } else {
                shots.remove(shot);
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive == false) continue;
            drawTank(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), g, enemyTanks.get(i).getDirection(), 0);
            for (int j = 0; j < enemyTanks.get(i).shots.size(); j++) {
                Shot shot = enemyTanks.get(i).shots.get(j);
                if (shot.isLive) {
                    g.draw3DRect(shot.x, shot.y, 3, 3, false);
                } else {
                    enemyTanks.get(i).shots.remove(j);
                    j--;
                }
            }
        }


//        drawTank(hero.getX() + 60, hero.getY(), g, 0, 0);
    }

    public void hitTank(Shot s, Hero hero) {
        switch (hero.getDirection()) {
            case 0:
            case 2:
                if (s.x > hero.getX() && s.x < hero.getX() + 40 &&
                        s.y > hero.getY() && s.y < hero.getY() + 60) {
                    s.isLive = false;
                    hero.isLive = false;
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(hero);
                }
                break;
            case 1:
            case 3:
                if (s.x > hero.getX() && s.x < hero.getX() + 60
                        && s.y > hero.getY() && s.y < hero.getY() + 40) {
                    s.isLive = false;
                    hero.isLive = false;
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(hero);
                }
                break;
        }
    }
    public void hitTank(Shot s, EnemyTank enemyTank) {
        switch (enemyTank.getDirection()) {
            case 0:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40 &&
                s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    Recorder.addAllEnemyTankNum();
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                }
                break;
            case 1:
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    Recorder.addAllEnemyTankNum();
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                }
                break;
        }
    }

    /**
     *
     * @param x 坦克左上角x坐标
     * @param y 坦克左上角y坐标
     * @param g 画笔
     * @param direction 方向
     * @param type 坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {

        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }
        //0:向上， 1:向右, 2:向下， 3:向左
        switch (direction) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断是否击中了敌人坦克
            Vector<Shot> shots = hero.shots;
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                if (shot.isLive) {
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank = enemyTanks.get(j);
                        hitTank(shot, enemyTank);
                    }
                }
            }

            //判断是否击中了hero
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                shots = enemyTank.shots;
                for (int j = 0; j < shots.size(); j++) {
                    hitTank(shots.get(j), hero);
                }
            }

            //判断敌方坦克是否重叠，若重叠，则随机变向；
            int n = enemyTanks.size();
            int[][] x = new int[n][2];
            int[][] y = new int[n][2];
            for (int i = 0; i < enemyTanks.size(); i++) {
                calArea(enemyTanks.get(i), x, y, i);
            }
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (((x[i][0] > x[j][0] && x[i][0] > x[j][1]) ||
                            (x[i][1] < x[j][0] && x[i][1] < x[j][0])) ||
                       ((y[i][0] > y[j][0] && y[i][0] > y[j][1]) ||
                               (y[i][1] < y[j][0] && y[i][1] < y[j][0])))
                        continue;
                    enemyTanks.get(i).setDirection(new Random().nextInt(4));
                    enemyTanks.get(j).setDirection(new Random().nextInt(4));
                }
            }

            this.repaint();
        }
    }

    public void calArea(EnemyTank enemyTank, int[][] x, int[][] y, int index) {
        int direction = enemyTank.getDirection();
        int left, right, up, down;
        switch (direction) {
            case 0:
            case 2:
                left = enemyTank.getX();
                right = left + 40;
                up = enemyTank.getY();
                down = up + 60;
                x[index][0] = left;
                x[index][1] = right;
                y[index][0] = up;
                y[index][1] = down;
                break;
            case 1:
            case 3:
                left = enemyTank.getX();
                right = left + 60;
                up = enemyTank.getY();
                down = up + 40;
                x[index][0] = left;
                x[index][1] = right;
                y[index][0] = up;
                y[index][1] = down;
                break;
        }
    }

    public void keepRecord() {
        Recorder.keepRecord(enemyTanks);
    }


}
