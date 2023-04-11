/**
 * @author 高谦民
 * @version 1.0
 */

@SuppressWarnings({"all"})
public class Shot implements Runnable {
    int x;    //子弹的横坐标
    int y;    //子弹的纵坐标
    int direct = 0;    //子弹的方向
    int speed = 20;    //子弹的速度
    boolean isLive = true;    //子弹是否还存在

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//射击
        while(true) {
            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x和y坐标
            switch(direct) {
                case 0:    //向上
                    y -= speed;
                    break;
                case 1:    //向右
                    x += speed;
                    break;
                case 2:    //向下
                    y += speed;
                    break;
                case 3:    //向左
                    x -= speed;
                    break;
            }
            //当子弹移动到屏幕边界时，就应该销毁(把启动的子弹的线程销毁)
            //当子弹碰到坦克时，也应该结束子弹的线程
            if(!(x >= 0 && x <= 1096 && y >= 0 && y <= 708 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}