/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
public class Tank {
    private int x;    //坦克的横坐标
    private int y;    //坦克的纵坐标
    private int direct;    //direct表示坦克方向(0：向上，1：向右，2：向下，3：向左)
    private int speed;    //坦克速度
    private boolean isLive = true;    //坦克是否存活

    public Tank(int x,int y) {
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getisLive() {
        return isLive;
    }

    public void setisLive(boolean isLive) {
        this.isLive = isLive;
    }

    //上、右、下、左移动方法
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }
}