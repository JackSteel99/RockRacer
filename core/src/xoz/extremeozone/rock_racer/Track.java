package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Track {
    private int k = 100;
    private int HEIGHT;
    private int WIDTH;
    private Vector2[] points = new Vector2[k];
    private CatmullRomSpline<Vector2> leftPath;
    private CatmullRomSpline<Vector2> rightPath;
    private Vector2[] leftDataSet = new Vector2[6];
    private Vector2[] rightDataSet = new Vector2[6];

    // Different build method for connecting the end of one track to the start of this one.
    public Track(int screen, Vector2 leftStart, Vector2 rightStart) {
        HEIGHT = Gdx.graphics.getHeight();
        WIDTH = Gdx.graphics.getWidth();
        leftPath = new CatmullRomSpline<Vector2>(buildLeft(leftStart, screen), false);
        rightPath = new CatmullRomSpline<Vector2>(buildRight(rightStart), false);
    }

    private double range(double low, double high) {
        return (new Random().nextDouble() * (high - low) + low);
    }

    private Vector2[] buildLeft(Vector2 start, int screen) {
        leftDataSet[0] = start;
        leftDataSet[1] = leftDataSet[0].cpy();
        //Randomly determine points 2 and 3 of the Bezier curve
        for(int i=2; i<4; i++) {
            int x = (int) (range(0.1, 0.5) * (WIDTH));
            int y = (HEIGHT*(i-1)/3) + (HEIGHT*screen);
            leftDataSet[i] = new Vector2(x, y);
        }
        leftDataSet[4] = new Vector2((int) (range(0.1, 0.5) * WIDTH), HEIGHT + (HEIGHT*screen));
        leftDataSet[5] = leftDataSet[4].cpy();
        return leftDataSet;
    }

    private Vector2[] buildRight(Vector2 start) {
        rightDataSet[0] = start;
        rightDataSet[1] = rightDataSet[0].cpy();
        for(int i=2; i<5; i++) {
            double l = leftDataSet[i].x / WIDTH;
            int x = (int) (range(l+0.3,.9) * WIDTH);
            int y = (int) leftDataSet[i].y;
            rightDataSet[i] = new Vector2(x, y);
        }
        rightDataSet[5] = rightDataSet[4].cpy();
        return rightDataSet;
    }

    public void lower(float low) {
        for (int i = 0; i < leftDataSet.length; i++) {
            leftDataSet[i].add(0,-low);
            rightDataSet[i].add(0,-low);
        }
        leftPath = new CatmullRomSpline<>(leftDataSet,false);
        rightPath = new CatmullRomSpline<>(rightDataSet,false);
    }

    public Vector2[] getPath(CatmullRomSpline path){
        for (int i=0; i<k; ++i) {
            points[i] = new Vector2();
            path.valueAt(points[i], ((float) i) / ((float) k - 1));
        }
        return points.clone();
    }

    public Vector2[] getLeftPath(){ return getPath(leftPath); }

    public Vector2[] getRightPath(){ return getPath(rightPath); }

    public Vector2 getLeftEnd() { return leftDataSet[leftDataSet.length-1].cpy(); }
    public Vector2 getRightEnd() { return rightDataSet[rightDataSet.length-1].cpy(); }

    public void dispose() {

    }
}
