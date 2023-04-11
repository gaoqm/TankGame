import java.util.Vector;

/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
public class Enemy extends Tank implements Runnable {
    //在敌方坦克类，使用Vector保存多个Shot
    Vector<Shot> shots = new Vector<>();

    //增加成员，Enemy可以得到敌人坦克的Vector
    Vector<Enemy> enemies = new Vector<>();

    //定义我方坦克并给出setHero方法，用于得到hero对象
    static Hero hero;
    public static void setHero(Hero getHero) {
        hero = getHero;
    }

    public Enemy(int x, int y) {
        super(x,y);
    }

    //这里提供一个方法，可以将MyPanel的成员Vector<Enemy> enemies = new Vector<>();
    //设置到Enemy的成员enemies
    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
    }

    //编写方法，判断enemies中的敌方坦克是否和我方坦克发生了重叠或者碰撞
    public boolean isTouchHero() {
        //遍历enemies中的所有坦克
        for(int i = 0;i < enemies.size();i++) {
            //从enemies中取出一个敌方坦克
            Enemy enemy = enemies.get(i);
            //判断该敌方坦克(enemy)的方向
            switch(enemy.getDirect()) {
                case 0:    //向上
                    //让当前的敌方坦克和我方坦克作比较
                    //如果我方坦克是向上或向下
                    if(hero.getDirect() == 0 || hero.getDirect() == 2) {
                        //当前敌方坦克左上角的坐标[enemy.getX(), enemy.getY()]
                        if (enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 40 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 60) {
                            return true;
                        }
                        //当前敌方坦克右上角的坐标[enemy.getX() + 40, enemy.getY()]
                        if (enemy.getX() + 40 >= hero.getX() &&
                                enemy.getX() + 40 <= hero.getX() + 40 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 60) {
                            return true;
                        }
                    }
                    //如果我方坦克是向左或向右
                    if(hero.getDirect() == 1 || hero.getDirect() == 3) {
                        //当前坦克左上角的坐标[enemy.getX(), enemy.getY()]
                        if(enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 60 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 40) {
                            return true;
                        }
                        //当前坦克右上角的坐标[enemy.getX() + 40, enemy.getY()]
                        if(enemy.getX() + 40 >= hero.getX() &&
                                enemy.getX() + 40 <= hero.getX() + 60 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 1:    //向右
                    //让当前的敌方坦克和我方坦克作比较
                    //如果我方坦克是向上或向下
                    if(hero.getDirect() == 0 || hero.getDirect() == 2) {
                        //当前敌方坦克左上角的坐标[enemy.getX() + 60, enemy.getY()]
                        if (enemy.getX() + 60 >= hero.getX() &&
                                enemy.getX() + 60 <= hero.getX() + 40 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 60) {
                            return true;
                        }
                        //当前敌方坦克右上角的坐标[enemy.getX() + 60, enemy.getY() + 40]
                        if (enemy.getX() + 60 >= hero.getX() &&
                                enemy.getX() + 60 <= hero.getX() + 40 &&
                                enemy.getY() + 40 >= hero.getY() &&
                                enemy.getY() + 40 <= hero.getY() + 60) {
                            return true;
                        }
                    }
                    //如果我方坦克是向左或向右
                    if(hero.getDirect() == 1 || hero.getDirect() == 3) {
                        //当前坦克左上角的坐标[enemy.getX() + 60, enemy.getY()]
                        if(enemy.getX() + 60 >= hero.getX() &&
                                enemy.getX() + 60 <= hero.getX() + 60 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 40) {
                            return true;
                        }
                        //当前坦克右上角的坐标[enemy.getX() + 60, enemy.getY() + 40]
                        if(enemy.getX() + 60 >= hero.getX() &&
                                enemy.getX() + 60 <= hero.getX() + 60 &&
                                enemy.getY() + 40 >= hero.getY() &&
                                enemy.getY() + 40 <= hero.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 2:    //向下
                    //让当前的敌方坦克和我方坦克作比较
                    //如果我方坦克是向上或向下
                    if(hero.getDirect() == 0 || hero.getDirect() == 2) {
                        //当前敌方坦克左上角的坐标[enemy.getX(), enemy.getY() + 60]
                        if (enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 40 &&
                                enemy.getY() + 60 >= hero.getY() &&
                                enemy.getY() + 60 <= hero.getY() + 60) {
                            return true;
                        }
                        //当前敌方坦克右上角的坐标[enemy.getX() + 40, enemy.getY() + 60]
                        if (enemy.getX() + 40 >= hero.getX() &&
                                enemy.getX() + 40 <= hero.getX() + 40 &&
                                enemy.getY() + 60 >= hero.getY() &&
                                enemy.getY() + 60 <= hero.getY() + 60) {
                            return true;
                        }
                    }
                    //如果我方坦克是向左或向右
                    if(hero.getDirect() == 1 || hero.getDirect() == 3) {
                        //当前坦克左上角的坐标[enemy.getX(), enemy.getY() + 60]
                        if(enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 60 &&
                                enemy.getY() + 60 >= hero.getY() &&
                                enemy.getY() + 60 <= hero.getY() + 40) {
                            return true;
                        }
                        //当前坦克右上角的坐标[enemy.getX() + 40, enemy.getY() + 60]
                        if(enemy.getX() + 40 >= hero.getX() &&
                                enemy.getX() + 40 <= hero.getX() + 60 &&
                                enemy.getY() + 60 >= hero.getY() &&
                                enemy.getY() + 60 <= hero.getY() + 40) {
                            return true;
                        }
                    }
                    break;
                case 3:    //向左
                    //让当前的敌方坦克和我方坦克作比较
                    //如果我方坦克是向上或向下
                    if(hero.getDirect() == 0 || hero.getDirect() == 2) {
                        //当前敌方坦克左上角的坐标[enemy.getX(), enemy.getY()]
                        if (enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 40 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 60) {
                            return true;
                        }
                        //当前敌方坦克右上角的坐标[enemy.getX(), enemy.getY() + 40]
                        if (enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 40 &&
                                enemy.getY() + 40 >= hero.getY() &&
                                enemy.getY() + 40 <= hero.getY() + 60) {
                            return true;
                        }
                    }
                    //如果我方坦克是向左或向右
                    if(hero.getDirect() == 1 || hero.getDirect() == 3) {
                        //当前坦克左上角的坐标[enemy.getX(), enemy.getY()]
                        if(enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 60 &&
                                enemy.getY() >= hero.getY() &&
                                enemy.getY() <= hero.getY() + 40) {
                            return true;
                        }
                        //当前坦克右上角的坐标[enemy.getX(), enemy.getY() + 40]
                        if(enemy.getX() >= hero.getX() &&
                                enemy.getX() <= hero.getX() + 60 &&
                                enemy.getY() + 40 >= hero.getY() &&
                                enemy.getY() + 40 <= hero.getY() + 40) {
                            return true;
                        }
                    }
                    break;
            }
        }
        return false;
    }

    //编写方法，判断当前的这个敌方坦克是否和enemies中的其它坦克发生了重叠或者碰撞
    public boolean isTouchEnemy() {
        //判断当前敌方坦克(this)方向
        switch(this.getDirect()) {
            case 0:    //向上
                //让当前的敌方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己比较
                    if(enemy != this) {
                        //如果敌方坦克是向上或向下
                        if(enemy.getDirect() == 0 || enemy.getDirect() == 2) {
                            //当前坦克左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 40 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 60) {
                                return true;
                            }
                            //当前坦克右上角的坐标[this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemy.getX() &&
                                    this.getX() + 40 <= enemy.getX() + 40 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌方坦克是向左或向右
                        if(enemy.getDirect() == 1 || enemy.getDirect() == 3) {
                            //当前坦克左上角的坐标[this.getX(), this.getY()]
                            if(this.getX() >= enemy.getX() &&
                                   this.getX() <= enemy.getX() + 60 &&
                                   this.getY() >= enemy.getY() &&
                                   this.getY() <= enemy.getY() + 40) {
                                return true;
                            }
                            //当前坦克右上角的坐标[this.getX() + 40, this.getY()]
                            if(this.getX() + 40 >= enemy.getX() &&
                                   this.getX() + 40 <= enemy.getX() + 60 &&
                                   this.getY() >= enemy.getY() &&
                                   this.getY() <= enemy.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1:    //向右
                //让当前的敌方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己比较
                    if(enemy != this) {
                        //如果敌方坦克是向上或向下
                        if(enemy.getDirect() == 0 || enemy.getDirect() == 2) {
                            //当前坦克右上角的坐标[this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemy.getX() &&
                                    this.getX() + 60 <= enemy.getX() + 40 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemy.getX() &&
                                    this.getX() + 60 <= enemy.getX() + 40 &&
                                    this.getY() + 40 >= enemy.getY() &&
                                    this.getY() + 40 <= enemy.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌方坦克是向左或向右
                        if(enemy.getDirect() == 1 || enemy.getDirect() == 3) {
                            //当前坦克右上角的坐标[this.getX() + 60, this.getY()]
                            if(this.getX() + 60 >= enemy.getX() &&
                                    this.getX() + 60 <= enemy.getX() + 60 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX() + 60, this.getY() + 40]
                            if(this.getX() + 60 >= enemy.getX() &&
                                    this.getX() + 60 <= enemy.getX() + 60 &&
                                    this.getY() + 40 >= enemy.getY() &&
                                    this.getY() + 40 <= enemy.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:    //向下
                //让当前的敌方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己比较
                    if(enemy != this) {
                        //如果敌方坦克是向上或向下
                        if(enemy.getDirect() == 0 || enemy.getDirect() == 2) {
                            //当前坦克左下角的坐标[this.getX(), this.getY() + 60]
                            if (this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 40 &&
                                    this.getY() + 60 >= enemy.getY() &&
                                    this.getY() + 60 <= enemy.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemy.getX() &&
                                    this.getX() + 40 <= enemy.getX() + 40 &&
                                    this.getY() + 60 >= enemy.getY() &&
                                    this.getY() + 60 <= enemy.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌方坦克是向左或向右
                        if(enemy.getDirect() == 1 || enemy.getDirect() == 3) {
                            //当前坦克左下角的坐标[this.getX(), this.getY() + 60]
                            if(this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 60 &&
                                    this.getY() + 60 >= enemy.getY() &&
                                    this.getY() + 60 <= enemy.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX() + 40, this.getY() + 60]
                            if(this.getX() + 40 >= enemy.getX() &&
                                    this.getX() + 40 <= enemy.getX() + 60 &&
                                    this.getY() + 60 >= enemy.getY() &&
                                    this.getY() + 60 <= enemy.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3:    //向左
                //让当前的敌方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己比较
                    if(enemy != this) {
                        //如果敌方坦克是向上或向下
                        if(enemy.getDirect() == 0 || enemy.getDirect() == 2) {
                            //当前坦克左上角的坐标[this.getX(), this.getY()]
                            if (this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 40 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 60) {
                                return true;
                            }
                            //当前坦克左下角的坐标[this.getX(), this.getY() + 40]
                            if (this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 40 &&
                                    this.getY() + 40 >= enemy.getY() &&
                                    this.getY() + 40 <= enemy.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌方坦克是向左或向右
                        if(enemy.getDirect() == 1 || enemy.getDirect() == 3) {
                            //当前坦克左上角的坐标[this.getX(), this.getY()]
                            if(this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 60 &&
                                    this.getY() >= enemy.getY() &&
                                    this.getY() <= enemy.getY() + 40) {
                                return true;
                            }
                            //当前坦克左下角的坐标[this.getX(), this.getY() + 40]
                            if(this.getX() >= enemy.getX() &&
                                    this.getX() <= enemy.getX() + 60 &&
                                    this.getY() + 40 >= enemy.getY() &&
                                    this.getY() + 40 <= enemy.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public int getDirect() {
        return super.getDirect() + 2;
    }

    @Override
    public void run() {
        //初始化敌方坦克移动速度
        super.setSpeed(5);

        while(true) {
            //创建一颗子弹放到集合shots中并启动Shot线程
            //规定敌方坦克也能发射多颗子弹(最多发射五颗)
            if(getisLive() && shots.size() < 5) {
                Shot shot = null;
                //根据坦克的方向来创建对应的子弹
                switch(getDirect()) {
                    case 0:    //向上
                        for(int i = 0;i <= (int)(Math.random() * 5);i++) {
                            shot = new Shot(getX() + 18, getY() - 5, 0);
                            shots.add(shot);
                            //启动Shot线程
                            Thread thread = new Thread(shot);
                            thread.start();
                            try {
                                Thread.sleep((int)(Math.random() * 500));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:    //向右
                        for(int i = 0;i <= (int)(Math.random() * 5);i++) {
                            shot = new Shot(getX() + 65, getY() + 18, 1);
                            shots.add(shot);
                            //启动Shot线程
                            Thread thread = new Thread(shot);
                            thread.start();
                            try {
                                Thread.sleep((int)(Math.random() * 500));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:    //向下
                        for(int i = 0;i <= (int)(Math.random() * 5);i++) {
                            shot = new Shot(getX() + 18, getY() + 65, 2);
                            shots.add(shot);
                            //启动Shot线程
                            Thread thread = new Thread(shot);
                            thread.start();
                            try {
                                Thread.sleep((int)(Math.random() * 500));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:    //向左
                        for(int i = 0;i <= (int)(Math.random() * 5);i++) {
                            shot = new Shot(getX() - 5, getY() + 18, 3);
                            shots.add(shot);
                            //启动Shot线程
                            Thread thread = new Thread(shot);
                            thread.start();
                            try {
                                Thread.sleep((int)(Math.random() * 500));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }

            //根据坦克的方向来继续移动
            switch(getDirect()) {
                case 0:    //向上
                    //让坦克保持在一个方向上走60步
                    for(int i = 0;i < 60;i++) {
                        if(getY() - 5 > 0 && !isTouchEnemy() && !isTouchHero()) {
                            moveUp();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 1:    //向右
                    //让坦克保持在一个方向上走60步
                    for(int i = 0;i < 60;i++) {
                        if(getX() + 65 < 1100 && !isTouchEnemy() && !isTouchHero()) {
                            moveRight();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 2:    //向下
                    //让坦克保持在一个方向上走60步
                    for(int i = 0;i < 60;i++) {
                        if(getY() + 65 < 712 && !isTouchEnemy() && !isTouchHero()) {
                            moveDown();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 3:    //向左
                    //让坦克保持在一个方向上走60步
                    for(int i = 0;i < 60;i++) {
                        if(getX() - 5 > 0 && !isTouchEnemy() && !isTouchHero()) {
                            moveLeft();
                            //休眠50毫秒
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }

            //然后随机地改变坦克的方向 0-3
            setDirect(((int)(Math.random() * 4) - 2));
            if(!getisLive()) {
                break;    //结束该线程
            }
        }
    }
}