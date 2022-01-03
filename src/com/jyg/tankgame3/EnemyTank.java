package com.jyg.tankgame3;

import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        int cnt = 0;
        while (true) {
            cnt++;
            switch (getDirection()) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    moveDown();
                    break;
                case 3:
                    moveLeft();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //发射子弹
            if (cnt % 88 == 0) {
                int x;
                int y;
                if (getDirection() == 0) {
                    x = getX() + 20;
                    y = getY();
                } else if (getDirection() == 1) {
                    x = getX() + 60;
                    y = getY() + 20;
                } else if (getDirection() == 2) {
                    x = getX() + 20;
                    y = getY() + 60;
                } else {
                    x = getX();
                    y = getY() + 20;
                }
                Shot shot = new Shot(x, y, getDirection());
                shots.add(shot);
                new Thread(shot).start();
            }

            //随机转向
            if (cnt % 100 == 0)
                setDirection(new Random().nextInt(4));

            if (isLive == false) {
                return ;
            }
        }
    }
}
