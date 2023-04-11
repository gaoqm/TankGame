/**
 * @author 高谦民
 * @version 1.0
 */

//编写一个Node类，表示敌方坦克的信息
@SuppressWarnings({"all"})
public class Node {
    private int x;
    private int y;
    private int direct;

    public Node(int x,int y,int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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
}