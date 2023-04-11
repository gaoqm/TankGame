import java.io.*;
import java.util.Vector;

/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
//该类用于记录相关的游戏信息并和文件交互
public class Recorder {
    //定义变量，记录我方坦克击毁敌方坦克数
    private static int hiteEnemyTankNum = 0;
    //定义变量，记录我方坦克个数
    private static int allHeroTankNum = 1;
    //定义变量，记录敌方坦克总数
    private static int allEnemyTankNum;
    //定义变量，记录我方坦克击毁敌方坦克成功率
    private static float successRate;
    //定义变量，记录游戏用时
    private static int gameTime1;
    private static float gameTime2;
    //定义游戏评价
    private static String gameComment = "";
    //定义Vector，指向MyPanel对象的敌方坦克Vector
    private static Vector<Enemy> enemies = null;
    //定义一个Node的Vector，用于保存敌方坦克的信息node
    private static Vector<Node> nodes = new Vector<>();
    //定义我方坦克并给出setHero和getHero方法，得到hero对象用于记录我方坦克信息
    static Hero hero;
    public static void setHero(Hero getHero) {
        hero = getHero;
    }
    public static Hero getHero() {
        return hero;
    }
    //定义IO对象，准备写数据到文件中
    private static BufferedWriter bw1 = null;
    private static BufferedWriter bw2 = null;
    private static BufferedReader br = null;
    private static String recordFile_1 = "E:\\SourceCodeAndScript\\JavaProject\\TankGame\\GameRecord\\myRecord_1.txt";
    private static String recordFile_2 = "E:\\SourceCodeAndScript\\JavaProject\\TankGame\\GameRecord\\myRecord_2.txt";
    //编写一个get方法，返回记录游戏数据的文件(recordFile_2)路径
    public static String getRecordFile_2() {
        return recordFile_2;
    }

    //编写一个方法，用于读取recordFile_2文件，以恢复相关信息
    //该方法在选择继续上一局游戏的时候调用即可
    public static Vector<Node> keepGame() {
        try {
            br = new BufferedReader(new FileReader(recordFile_2));
            allHeroTankNum = Integer.parseInt(br.readLine());
            allEnemyTankNum = Integer.parseInt(br.readLine());
            hiteEnemyTankNum = Integer.parseInt(br.readLine());
            successRate = Float.parseFloat(br.readLine());
            gameTime2 = Float.parseFloat(br.readLine());
            String line = "";
            line = br.readLine();
            String[] xyd = line.split(" ");
            hero = new Hero(Integer.parseInt(xyd[0]),Integer.parseInt(xyd[1]));
            hero.setDirect(Integer.parseInt(xyd[2]));
            //循环读取文件recordFile_2，生成nodes集合
            while((line = br.readLine()) != null) {
                xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                                     Integer.parseInt(xyd[2]));
                nodes.add(node);    //放入nodes Vector集合中
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    //编写一个方法，当游戏退出时，我们将以上游戏信息保存到文件recordFile_1和文件recordFile_2中
    //对keepRecord进行升级，保存敌方剩余坦克的坐标和方向
    public static void keepRecord() {
        try {
            //将详细游戏信息写入文件recordFile_1中，用于玩家查看
            bw1 = new BufferedWriter(new FileWriter(recordFile_1));
            bw1.write("==================== 游戏信息如下 ====================" + "\r\n");
            bw1.write("游戏名：《坦克大战》\r\n");
            if(Recorder.getAllHeroTankNum() == 1 && enemies.size() != 0) {
                bw1.write("游戏状态：未结束！（可以选择是否继续上一局游戏）\r\n");
            } else {
                bw1.write("游戏状态：已结束！\r\n");
            }
            bw1.write("我方坦克个数： " + allHeroTankNum + " 个\r\n");
            bw1.write("敌方坦克总数： " + allEnemyTankNum + " 个\r\n");
            bw1.write("击毁敌方坦克个数： " + hiteEnemyTankNum + " 个\r\n");
            bw1.write("击毁成功率： " + String.format("%.1f",successRate) + " %\r\n");
            bw1.write("游戏用时： " + (int)gameTime2 + " 秒\r\n");
            if(Recorder.getAllHeroTankNum() == 1 && enemies.size() != 0) {
                bw1.write("游戏评价：游戏尚未结束，系统还不能做出评价捏~\r\n\n");
            } else {
                bw1.write("游戏评价：" + gameComment + "\r\n\n");
            }
            bw1.write("剩余坦克位置信息：\r\n");
            if(allHeroTankNum == 1) {
                bw1.write("(1). 我方坦克\r\n");
                bw1.write("\t  横坐标\t  纵坐标\t  方向\r\n");
                String record1 =  hero.getX() + "   \t" + hero.getY() + "   \t" + hero.getDirect();
                bw1.write("坦克：  " + record1 + "\r\n");
                bw1.write("注：方向中的数字0表示向上，1表示向右，2表示向下，3表示向左\r\n\n");
            } else {
                bw1.write("(1). 我方坦克\r\n");
                bw1.write("我方坦克已被击毁！\r\n\n");
            }
            if(enemies.size() != 0) {
                bw1.write("(2). 敌方坦克\r\n");
                bw1.write("\t  横坐标\t  纵坐标\t  方向\r\n");
                //遍历敌方坦克的Vector集合，然后根据实际情况保存即可
                //根据OOP编程思想，定义一个属性，然后通过set方法得到敌方坦克的Vector集合
                for(int i = 0;i < enemies.size();i++) {
                    //取出敌方坦克
                    Enemy enemy = enemies.get(i);
                    if(enemy.getisLive()) {
                        //保存该enemy信息
                        String record2 = enemy.getX() + "   \t" + enemy.getY() + "   \t" + enemy.getDirect();
                        //写入到文件myRecord.txt中去
                        bw1.write("坦克" + (i + 1) + ": " + record2 + "\r\n");
                    }
                }
                bw1.write("注：方向中的数字0表示向上，1表示向右，2表示向下，3表示向左\r\n");
            } else {
                bw1.write("(2). 敌方坦克\r\n");
                bw1.write("敌方坦克已被全部击毁！\r\n");
            }
            bw1.write("==================== 游戏信息如上 ====================");
            //将各项游戏数据记录到文件recordFile_2中，用于恢复数据以便继续上局游戏
            bw2 = new BufferedWriter(new FileWriter(recordFile_2));
            bw2.write(allHeroTankNum + "\r\n");
            bw2.write(allEnemyTankNum + "\r\n");
            bw2.write(hiteEnemyTankNum + "\r\n");
            bw2.write(String.format("%.1f",successRate) + "\r\n");
            bw2.write((int)gameTime2 + "\r\n");
            if(enemies.size() != 0) {
                bw2.write(hero.getX() + " " + hero.getY() + " " + hero.getDirect() + "\r\n");
            } else {
                bw2.write(hero.getX() + " " + hero.getY() + " " + hero.getDirect() + "");
            }
            for(int i = 0;i < enemies.size();i++) {
                //取出敌方坦克
                Enemy enemy = enemies.get(i);
                if(enemy.getisLive()) {
                    //保存该enemy信息
                    String record = enemy.getX() + " " + enemy.getY() + " " + enemy.getDirect();
                    //写入到文件myRecord.txt中去
                    if((i + 1) != enemies.size()) {
                        bw2.write(record + "\r\n");
                    } else {
                        bw2.write(record + "");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw1 != null) {
                try {
                    bw1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw2 != null) {
                try {
                    bw2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getHiteEnemyTankNum() {
        return hiteEnemyTankNum;
    }

    public static void setHiteEnemyTankNum(int hiteEnemyTankNum) {
        Recorder.hiteEnemyTankNum = hiteEnemyTankNum;
    }

    public static int getAllHeroTankNum() {
        return allHeroTankNum;
    }

    public static void setAllHeroTankNum(int allHeroTankNum) {
        Recorder.allHeroTankNum = allHeroTankNum;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static float getSuccessRate() {
        return successRate;
    }

    public static void setSuccessRate(float successRate) {
        Recorder.successRate = successRate;
    }

    public static int getGameTime1() {
        return gameTime1;
    }

    public static void setGameTime1(int gameTime1) {
        Recorder.gameTime1 = gameTime1;
    }

    public static float getGameTime2() {
        return gameTime2;
    }

    public static void setGameTime2(float gameTime2) {
        Recorder.gameTime2 = gameTime2;
    }

    public static String getGameComment() {
        return gameComment;
    }

    public static void setGameComment(String gameComment) {
        Recorder.gameComment = gameComment;
    }

    //通过set方法得到敌方坦克的Vector集合
    public static void setEnemies(Vector<Enemy> enemies) {
        Recorder.enemies = enemies;
    }

    //编写方法，对hiteEnemyTankNum++
    public static void addHiteEnemyTankNum() {
        Recorder.hiteEnemyTankNum++;
    }

    //编写方法，对allHeroTankNum--
    public static void reduceAllHeroTankNum() {
        Recorder.allHeroTankNum--;
    }

    //编写方法，随机生成敌方坦克个数(1~10)
    public static void randomAllEnemyTankNum() {
        Recorder.allEnemyTankNum = (int)(Math.random() * 10 + 1);
    }

    //编写方法，计算我方坦克击毁敌方坦克的成功率
    public static void calculateSuccessRate() {
        Recorder.successRate = (float)Recorder.getHiteEnemyTankNum()/Recorder.getAllEnemyTankNum() * 100;
    }

    //编写方法，计算游戏用时
    public static void addGameTime1() {
        Recorder.gameTime1 += 10;
    }

    public static void addGameTime2() {
        Recorder.gameTime2 += 1;
    }
}