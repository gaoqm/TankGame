import java.util.Vector;

/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
//自己的坦克
public class Hero extends Tank {
    //初始化我方坦克移动速度
    public void speed() {
        super.setSpeed(10);
    }
    //定义Hero对象
    private Hero hero;
    //定义一个Shot对象，表示一个射击(线程)
    Shot shot = null;

    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    //增加成员，Enemy可以得到敌人坦克的Vector
    Vector<Enemy> enemies = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法，可以将MyPanel的成员hero，设置到Hero的成员hero
    public void setHero(Hero hero) {
        this.hero = hero;
    }

    //这里提供一个方法，可以将MyPanel的成员Vector<Enemy> enemies = new Vector<>();
    //设置到Hero的成员enemies
    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
    }

    //射击
    public void shot() {
        //创建Shot对象，根据当前Hero对象的位置和方向来创建Shot
        switch (super.getDirect()) {//得到Hero对象方向
            case 0:    //向上
                shot = new Shot(getX() + 18,getY() - 5,0);
                break;
            case 1:    //向右
                shot = new Shot(getX() + 65,getY() + 18,1);
                break;
            case 2:    //向下
                shot = new Shot(getX() + 18,getY() + 65,2);
                break;
            case 3:    //向左
                shot = new Shot(getX() - 5,getY() + 18,3);
                break;
        }

        //把新创建的shot放入到shots集合中
        shots.add(shot);

        //启动Shot线程
        Thread thread = new Thread(shot);
        thread.start();
    }

    //编写方法，判断当前的这个我方坦克是否和enemies中的其它坦克发生了重叠或者碰撞
    public boolean isTouchHero() {
        //判断当前我方坦克(this)方向
        switch(this.getDirect()) {
            case 0:    //向上
                //让当前的我方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
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
                break;
            case 1:    //向右
                //让当前的我方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
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
                break;
            case 2:    //向下
                //让当前的我方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
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
                break;
            case 3:    //向左
                //让当前的我方坦克和其它所有的敌方坦克作比较
                for(int i = 0;i < enemies.size();i++) {
                    //从Vector中取出一个敌方坦克
                    Enemy enemy = enemies.get(i);
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
                break;
        }
        return false;
    }
}