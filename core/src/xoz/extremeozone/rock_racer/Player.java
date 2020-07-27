package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private static Vector3 pos;
    private static int w;
    private static int h;
    private static Vector2[] points;

    public static void create(int x, int y, int width, int height) {
        w = width;
        h = height;
        pos = new Vector3();
        points = new Vector2[8];
        setPos(x,y);
    }

    public static Vector3 getPos() { return pos; }
    public static Vector2[] getPoints() { return points; }

    public static void setPos(int x, int y) {
        x = x-w/2;
        y = y-100;
        pos.set(x, y, 0);
        setPoints(x, y);
    }
    public static void setPoints(int x, int y) {
        y = Gdx.graphics.getHeight()-y; //Flipped because axis flips on y axis
        points[0] = new Vector2(x,y);
        points[1] = new Vector2(x+w/2, y);
        points[2] = new Vector2(x+w, y);
        points[3] = new Vector2(x, y+h/2);
        points[4] = new Vector2(x+w, y+h/2);
        points[5] = new Vector2(x, y+h);
        points[6] = new Vector2(x+w/2, y+h);
        points[7] = new Vector2(x+w, y+h);
    }

    public static void dispose() {
    }
}
