import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
//坦克大战的绘图区域
//为了监听键盘事件，需要实现KeyListener接口
//为了让Panel不停地重绘子弹，需要将MyPanel实现Runnable接口，当做一个线程使用
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌方的坦克，放入到集合Vector中
    Vector<Enemy> enemies = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌方坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector，用于存放炸弹
    //当子弹击中坦克时，就加入一个Bomb对象到bombs集合
    Vector<Bomb> bombs = new Vector<>();
    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public MyPanel(String key) {
        //先判断记录游戏信息的文件是否存在
        //如果存在就正常执行，如果不存在就显示提示信息并将key置成1
        File file = new File(Recorder.getRecordFile_2());
        if(!file.exists() && key.equals("2")) {
            System.out.println("目标文件不存在，系统自动为您开启新的游戏！");
            key = "1";
        }

        //将MyPanel对象的enemies设置给Recorder类中的enemies
        Recorder.setEnemies(enemies);

        switch(key) {
            case "1":    //开始新的游戏
                //重新初始化我方坦克
                hero = new Hero(30, 600);
                hero.speed();

                //这里调用Enemy中的静态方法setHero，以便将该hero对象给到Enemy中的hero
                Enemy.setHero(hero);

                //这里调用Recorder中的静态方法setHero，以便将该hero对象给到Recorder中的hero
                Recorder.setHero(hero);

                //重新初始化敌方的坦克
                Recorder.randomAllEnemyTankNum();    //随机生成敌方坦克个数(1~10)
                for(int i = 0;i < Recorder.getAllEnemyTankNum();i++) {
                    Enemy enemy = new Enemy(100 * (i + 1),0);
                    enemies.add(enemy);
                    //将enemies设置给enemy
                    enemy.setEnemies(enemies);
                    //将enemies设置给hero
                    hero.setEnemies(enemies);
                    //将初始化后的hero设置给Hero
                    hero.setHero(hero);
                    //启动敌方坦克线程，使它们动起来
                    Thread threadEnemy = new Thread(enemy);
                    threadEnemy.start();
                    //给该enemy加入一颗子弹
                    Shot shot = new Shot(enemy.getX() + 18, enemy.getY() + 65, enemy.getDirect());
                    //将shot对象加入到Enemy的Vector成员
                    enemy.shots.add(shot);
                    //启动shot对象
                    Thread threadShot = new Thread(shot);
                    threadShot.start();
                }
                GqmTankGame.loop = false;
                break;
            case "2":    //继续上局游戏
                //调用KeepGame方法导入上一局游戏数据
                nodes = Recorder.keepGame();

                //判断游戏是否还能继续进行
                if(Recorder.getAllHeroTankNum() == 1 && nodes.size() != 0) {
                    //初始化上局游戏中的我方坦克
                    hero = Recorder.getHero();
                    hero.speed();

                    //这里调用Enemy中的静态方法setHero，以便将该hero对象给到Enemy中的hero
                    Enemy.setHero(hero);

                    //这里调用Recorder中的静态方法setHero，以便将该hero对象给到Recorder中的hero
                    Recorder.setHero(hero);

                    for(int i = 0;i < nodes.size();i++) {
                        Node node = nodes.get(i);
                        Enemy enemy = new Enemy(node.getX(),node.getY());
                        enemies.add(enemy);
                        //将enemies设置给enemy！
                        enemy.setEnemies(enemies);
                        //将enemies设置给hero!
                        hero.setEnemies(enemies);
                        //将初始化后的hero设置给Hero
                        hero.setHero(hero);
                        //设置方向
                        enemy.setDirect(node.getDirect());
                        //启动敌方坦克线程，使它们动起来
                        Thread threadEnemy = new Thread(enemy);
                        threadEnemy.start();
                        //给该enemy加入一颗子弹
                        Shot shot = new Shot(enemy.getX() + 18, enemy.getY() + 65, enemy.getDirect());
                        //将shot对象加入到Enemy的Vector成员
                        enemy.shots.add(shot);
                        //启动shot对象
                        Thread threadShot = new Thread(shot);
                        threadShot.start();
                    }
                    GqmTankGame.loop = false;
                } else {
                    //若判断游戏已经结束了，就给出提示信息并重置游戏数据
                    System.out.println("游戏已经结束，请重新开始游戏！");
                    Recorder.setHiteEnemyTankNum(0);
                    Recorder.setSuccessRate(0);
                    Recorder.setGameTime1(0);
                    Recorder.setGameTime2(0);
                    Recorder.setAllHeroTankNum(1);
                    Recorder.setGameComment(null);
                    GqmTankGame.loop = true;
                }
                break;
            default:
                System.out.println("您的选择有误，请重新选择！");
                GqmTankGame.loop = true;
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/Bomb_1.jpg"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/Bomb_2.jpg"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/Bomb_3.jpg"));
    }

    //编写方法，显示游戏信息
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.BLUE);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("玩家战绩",1120,60);
        g.drawString("操作指引",1120,340);
        g.setColor(Color.BLACK);
        g.drawString("击毁敌方坦克个数：",1120,90);
        g.drawString("击毁成功率：",1120,220);
        g.drawString("游戏用时：",1120,260);
        g.drawString("评价：",1120,300);
        g.drawString("W键：向上移动",1120,370);
        g.drawString("D键：向右移动",1120,400);
        g.drawString("S键：向下移动",1120,430);
        g.drawString("A键：向左移动",1120,460);
        g.drawString("J键：发射子弹",1120,490);
        g.drawString("个",1210,160);
        g.drawString("%",1340,220);
        g.drawString("秒",1300,260);
        g.drawString("：我方坦克",1180,560);
        g.drawString("(玩家操作)",1192,590);
        g.drawString("：敌方坦克",1180,660);
        g.drawString("(电脑操作)",1192,690);
        drawTank(1120,520,g,0,0);    //画出一个我方坦克
        drawTank(1120,120,g,0,1);    //画出一个敌方坦克
        drawTank(1120,620,g,0,1);    //画出一个敌方坦克
        g.setColor(Color.GREEN);
        g.drawString(Recorder.getHiteEnemyTankNum() + "",1180,160);
        Recorder.calculateSuccessRate();    //调用calculateSuccessRate()方法计算击毁成功率
        g.drawString(String.format("%.1f",Recorder.getSuccessRate()) + "",1265,220);
        if(Recorder.getGameTime1() % 1000 == 0 && Recorder.getAllHeroTankNum() == 1 && enemies.size() != 0) {
            Recorder.addGameTime2();
        }
        g.drawString((int)Recorder.getGameTime2() + "",1260,260);
        if(Recorder.getAllHeroTankNum() == 1 && Recorder.getSuccessRate() == 100) {
            Recorder.setGameComment("哎呦不错哦~");
        } else {
            Recorder.setGameComment("你个菜鸡!");
        }
        g.setColor(Color.RED);
        g.fill3DRect(1098,0,4,712,true);
        g.fill3DRect(0,0,1098,4,true);
        g.fill3DRect(0,0,4,712,true);
        g.fill3DRect(0,708,1098,4,true);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        if(Recorder.getAllHeroTankNum() == 1 && enemies.size() != 0) {
            g.drawString("坦克大战",1170,30);
        } else {
            g.drawString("游戏结束！",1170,30);
            g.setColor(Color.GREEN);
            g.setFont(new Font("宋体", Font.BOLD, 25));
            g.drawString(Recorder.getGameComment() + "",1200,300);
            if(Recorder.getGameComment().equals("哎呦不错哦~")) {
                g.setColor(Color.RED);
                g.setFont(new Font("宋体", Font.BOLD, 100));
                g.drawString("牛",500,370);
            } else {
                g.setFont(new Font("宋体", Font.BOLD, 100));
                g.drawString("菜",500,370);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1100,712);    //填充矩形，默认黑色

        //调用showInfo方法，在画板上显示游戏信息
        this.showInfo(g);

        //绘制自己的坦克-封装方法
        if(hero != null && hero.getisLive()) {
            drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        }

        //将hero的子弹集合shots遍历取出绘制
        for(int i = 0;i < hero.shots.size();i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive) {
                g.setColor(Color.cyan);
                g.fill3DRect(shot.x,shot.y,4,4,true);
            } else {
                //如果该shot对象已经无效，就从shots集合中拿掉
                hero.shots.remove(shot);
            }
        }

        //如果bombs集合中有对象，就画出来
        for(int i =0;i < bombs.size();i++) {
            //取出炸弹前休眠一会，以此能够让第一个爆炸效果显示出来
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if(bomb.life > 6) {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            } else if(bomb.life > 3) {
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            } else {
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life为0，就从bombs的集合中删除
            if(bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //绘制敌方的坦克-封装方法，遍历集合enemies
        for(int i = 0;i < enemies.size();i++) {
            //从集合Vector中取出坦克
            Enemy enemy = enemies.get(i);
            if(enemy != null && enemy.getisLive()) {    //判断敌方坦克是否存活，只有存活才画出坦克
                drawTank(enemy.getX(),enemy.getY(),g,enemy.getDirect(),1);
            }

            //画出enemy所有子弹
            for(int j = 0;j < enemy.shots.size();j++) {
                //取出子弹
                Shot shot = enemy.shots.get(j);
                //绘制子弹
                if(shot != null && shot.isLive) {
                    g.setColor(Color.yellow);
                    g.fill3DRect(shot.x,shot.y,4,4,true);
                } else {
                    //将子弹从Vector集合中移除
                    enemy.shots.remove(shot);
                }
            }
        }
    }

    //编写方法，绘制坦克
    public void drawTank(int x,int y,Graphics g,int direct,int type) {
        //根据不同类型的坦克，设置不同的颜色
        switch(type) {
            case 0:    //我方坦克
                g.setColor(Color.cyan);
                break;
            case 1:    //敌方坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据方向，来绘制对应朝向坦克
        //direct表示方向(0：向上，1：向右，2：向下，3：向左)
        switch(direct) {
            case 0:    //表示向上
                g.fill3DRect(x,y,10,60,false);    //画出坦克左边轮子
                g.fill3DRect(x + 30,y,10,60,false);    //画出坦克右边轮子
                g.fill3DRect(x + 10,y+10,20,40,false);    //画出坦克的身子
                g.fillOval(x + 10,y + 20,20,20);    //画出坦克的圆盖
                g.fill3DRect(x + 18,y - 5,4,35,false);    //画出坦克的炮筒
                break;
            case 1:    //表示向右
                g.fill3DRect(x,y,60,10,false);    //画出坦克上边轮子
                g.fill3DRect(x,y + 30,60,10,false);    //画出坦克下边轮子
                g.fill3DRect(x + 10,y+10,40,20,false);    //画出坦克的身子
                g.fillOval(x + 20,y + 10,20,20);    //画出坦克的圆盖
                g.fill3DRect(x + 30,y + 18,35,4,false);    //画出坦克的炮筒
                break;
            case 2:    //表示向下
                g.fill3DRect(x,y,10,60,false);    //画出坦克左边轮子
                g.fill3DRect(x + 30,y,10,60,false);    //画出坦克右边轮子
                g.fill3DRect(x + 10,y+10,20,40,false);    //画出坦克的身子
                g.fillOval(x + 10,y + 20,20,20);    //画出坦克的圆盖
                g.fill3DRect(x + 18,y + 30,4,35,false);    //画出坦克的炮筒
                break;
            case 3:    //表示向左
                g.fill3DRect(x,y,60,10,false);    //画出坦克上边轮子
                g.fill3DRect(x,y + 30,60,10,false);    //画出坦克下边轮子
                g.fill3DRect(x + 10,y+10,40,20,false);    //画出坦克的身子
                g.fillOval(x + 20,y + 10,20,20);    //画出坦克的圆盖
                g.fill3DRect(x - 5,y + 18,35,4,false);    //画出坦克的炮筒
                break;
        }
    }

    //编写方法，判断敌方坦克子弹是否击中我方坦克
    public void hitHero() {
        //遍历所有的敌方坦克
        for(int i = 0;i < enemies.size();i++) {
            //取出敌方坦克
            Enemy enemy = enemies.get(i);
            //遍历enemy对象的所有子弹
            for(int j = 0;j < enemy.shots.size();j++) {
                //取出子弹
                Shot shot = enemy.shots.get(j);
                //判断该子弹shot是否击中我方坦克
                if(hero.getisLive() && shot.isLive) {
                    hitTank(shot,hero);
                }
            }
        }
    }

    //编写方法，判断我方坦克子弹是否击中敌方坦克
    public void hitEnemy() {
        for(int i = 0;i < hero.shots.size();i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive) {
                //当我的子弹存活时，遍历所有敌方的坦克
                for(int j = 0;j < enemies.size();j++) {
                    Enemy enemy = enemies.get(j);
                    hitTank(shot,enemy);
                }
            }
        }
    }

    //编写方法，判断子弹是否击中坦克
    public void hitTank(Shot s,Tank tank) {
        switch (tank.getDirect()) {
            case 0:    //坦克向上
            case 2:    //坦克向下
                if(s.x > tank.getX() && s.x < tank.getX() + 40 &&
                   s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.setisLive(false);
                    //判断坦克类型
                    if(tank instanceof Enemy) {
                        //当我方坦克击毁一个敌方坦克时，就应当对hiteEnemyTankNum++
                        Recorder.addHiteEnemyTankNum();
                    }
                    //判断坦克类型
                    if(tank instanceof Hero) {
                        //当敌方坦克击毁我方坦克时，就应当对allHeroTankNum--
                        Recorder.reduceAllHeroTankNum();
                    }
                    //当我方子弹击中敌方的坦克后，将enemy对象从集合Vector中移出
                    enemies.remove(tank);
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:    //坦克向右
            case 3:    //坦克向左
                if(s.x > tank.getX() && s.x < tank.getX() + 60 &&
                   s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.setisLive(false);
                    //判断坦克类型
                    if(tank instanceof Enemy) {
                        //当我方坦克击毁一个敌方坦克时，就应当对hiteEnemyTankNum++
                        Recorder.addHiteEnemyTankNum();
                    }
                    //判断坦克类型
                    if(tank instanceof Hero) {
                        //当敌方坦克击毁我方坦克时，就应当对allHeroTankNum--
                        Recorder.reduceAllHeroTankNum();
                    }
                    //当我方子弹击中敌方的坦克后，将enemy对象从集合Vector中移出
                    enemies.remove(tank);
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理W，D，S，A，J五个键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        //按下W，D，S，A四个键控制我方坦克移动
        if(e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克的方向
            hero.setDirect(0);
            if(hero.getY() - 5 > 0 && !hero.isTouchHero()) {
                hero.moveUp();
            }
        } else if(e.getKeyCode() == KeyEvent.VK_D) {//按下D键
            hero.setDirect(1);
            if(hero.getX() + 65 < 1100 && !hero.isTouchHero()) {
                hero.moveRight();
            }
        } else if(e.getKeyCode() == KeyEvent.VK_S) {//按下S键
            hero.setDirect(2);
            if(hero.getY() + 65 < 712 && !hero.isTouchHero()) {
                hero.moveDown();
            }
        } else if(e.getKeyCode() == KeyEvent.VK_A) {//按下A键
            hero.setDirect(3);
            if(hero.getX() - 5 > 0 && !hero.isTouchHero()) {
                hero.moveLeft();
            }
        }

        //按下J键我方坦克发射子弹
        if(e.getKeyCode() == KeyEvent.VK_J) {
            //规定我方坦克可以发射多颗子弹(最多发射七颗)
            //规定发射完毕前一颗子弹后间隔大于等于100毫秒才能再次发射
            if(hero.shots.size() < 7 && hero.getisLive()) {
                hero.shot();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        //每隔10毫秒重绘一次画板，刷新绘图区域，子弹就会移动
        while(true) {
            try {
                Thread.sleep(10);
                Recorder.addGameTime1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断我方坦克子弹是否击中了敌方坦克
            this.hitEnemy();

            //判断敌方坦克子弹是否击中了我方坦克
            this.hitHero();

            //重绘画板
            this.repaint();
        }
    }
}