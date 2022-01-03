package com.jyg.tankgame3;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allEnemyTankNum = 0;
//    private static FileWriter fw = null;
//    private static BufferedWriter bw = null;
    private static String recordFile = "src/record.txt";
    private static ObjectOutputStream objectOutputStream = null;

    public static Vector<EnemyTank> restore() {
        Vector<EnemyTank> enemyTanks = new Vector<>();
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(recordFile));
            allEnemyTankNum = objectInputStream.readInt();
            int length = objectInputStream.readInt();
            for (int i = 0; i < length; i++) {
                EnemyTank enemyTank = new EnemyTank(0, 0);

                enemyTank.setX(objectInputStream.readInt());
                enemyTank.setY(objectInputStream.readInt());
                enemyTank.setDirection(objectInputStream.readInt());
                enemyTanks.add(enemyTank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return enemyTanks;
    }

    public static void keepRecord(Vector<EnemyTank> enemyTanks) {
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(recordFile));
            objectOutputStream.writeInt(allEnemyTankNum);
            objectOutputStream.writeInt(enemyTanks.size());
            for (int i = 0; i < enemyTanks.size(); i++) {
                objectOutputStream.writeInt(enemyTanks.get(i).getX());
                objectOutputStream.writeInt(enemyTanks.get(i).getY());
                objectOutputStream.writeInt(enemyTanks.get(i).getDirection());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void keepRecord() {
//        try {
//            objectOutputStream = new ObjectOutputStream(new FileOutputStream(recordFile));
//            objectOutputStream.writeInt(allEnemyTankNum);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (objectOutputStream != null) {
//                try {
//                    objectOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum() {
        allEnemyTankNum++;
    }

    //new ObjectInputStream(new FileInputStream())
}
