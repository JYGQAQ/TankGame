package com.jyg.tankgame3;

import java.util.Random;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;

    public void moveUp() {
        if (y > 0) {
            y -= speed;
        } else {
            if (this instanceof EnemyTank)
                direction = new Random().nextInt(4);
        }
    }
    public void moveDown() {
        if (y < 690) {
            y += speed;
        } else {
            if (this instanceof EnemyTank)
                direction = new Random().nextInt(4);
        }
    }
    public void moveLeft() {
        if (x > 0) {
            x -= speed;
        } else {
            if (this instanceof EnemyTank)
                direction = new Random().nextInt(4);
        }
    }
    public void moveRight() {
        if (x < 940) {
            x += speed;
        } else {
            if (this instanceof EnemyTank)
                direction = new Random().nextInt(4);
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
