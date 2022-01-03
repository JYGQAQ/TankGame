package com.jyg.tankgame3;

import java.util.Vector;

public class Hero extends Tank {
    Shot shot = null;
    boolean isLive = true;
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        switch (getDirection()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), getDirection());
                shots.add(shot);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, getDirection());
                shots.add(shot);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, getDirection());
                shots.add(shot);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, getDirection());
                shots.add(shot);
                break;
        }
        new Thread(shot).start();
    }
}
