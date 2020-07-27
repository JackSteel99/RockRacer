package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class World {
    //****Track****
    private static Track[] tracks = new Track[2];
    private static Vector2[] leftPoints;
    private static Vector2[] rightPoints;
    private static int index = 0; // Handles which track is currently the lowest (0 or 1)
    private static float speed = 0; // Defines how many pixels the tracks are lowered every update
    //****Player****
    private static int playerWidth = (int) (Gdx.graphics.getWidth()*0.1);
    private static int playerHeight = (int) (Gdx.graphics.getWidth()*0.1);
    //****Misc******
    private static ShapeRenderer sr;
    private static SpriteBatch batch;
    private static OrthographicCamera cam;

    private static AsteroidOutside AI;

    public static void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //****Tracks Create****
        tracks[0] = new Track(1, new Vector2(Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()),
                new Vector2(Gdx.graphics.getWidth()/2 + 100,Gdx.graphics.getHeight()));
        tracks[1] = new Track(2, tracks[0].getLeftEnd(), tracks[0].getRightEnd());
        leftPoints = combineArray(tracks[0].getLeftPath(), tracks[1].getLeftPath());
        rightPoints = combineArray(tracks[0].getRightPath(), tracks[1].getRightPath());
        //****Player Create****
        Player.create(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-50, playerWidth, playerHeight);
        cam.unproject(Player.getPos());

        AI = new AsteroidOutside();

        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        batch = new SpriteBatch();
    }

    public static void update() {
        cam.update();
        //****Track Update****
        tracks[0].lower(speed);
        tracks[1].lower(speed);
        //If lowest track moves below screen, shift track index by one and make new upper Track.
        if(tracks[index].getLeftPath()[tracks[index].getLeftPath().length-1].y <= 0){
            tracks[index] = new Track(1, tracks[1^index].getLeftEnd(), tracks[1^index].getRightEnd());
            index ^= 1;
        }
        leftPoints = combineArray(tracks[index].getLeftPath(), tracks[1^index].getLeftPath());
        rightPoints = combineArray(tracks[index].getRightPath(), tracks[1^index].getRightPath());
        //****Player Update****
        if(Gdx.input.isTouched()) {
            Player.setPos(Gdx.input.getX(), Gdx.input.getY());
            cam.unproject(Player.getPos());
        }
    }

    public static void Draw() {
        batch.begin();
        batch.draw(AssetLoader.asteroidInside, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        AI.draw(leftPoints, rightPoints);
        //****Track Draw****
        /*sr.begin();
        sr.setColor(Color.WHITE);
        for (int i = 1; i < leftPoints.length; i++) {
            sr.line(leftPoints[i-1], leftPoints[i]);
            sr.line(rightPoints[i-1], rightPoints[i]);
        }
        sr.end();*/
        //****Player Draw****
        batch.begin();
        batch.draw(AssetLoader.ship, Player.getPos().x, Player.getPos().y, playerWidth, playerHeight);
        batch.end();
        //sr.begin();
        //sr.setColor(Color.RED);
        //sr.circle(Player.getPoints()[0].x, Player.getPoints()[0].y, 10);
        //sr.circle(Player.getPoints()[7].x, Player.getPoints()[7].y, 10);
        //sr.end();
    }

    public static void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
    }

    public static Vector2[] getLeftPoints() {return leftPoints;}
    public static Vector2[] getRightPoints() {return rightPoints;}

    public static void setSpeed(float i) {speed = i;}

    private static Vector2[] combineArray(Vector2[] arr1, Vector2[] arr2) {
        int length1 = arr1.length;
        int length2 = arr2.length;
        Vector2[] result = new Vector2[length1+length2];
        System.arraycopy(arr1, 0, result, 0, length1);
        System.arraycopy(arr2, 0, result, length1, length2);
        return result;
    }

    public static void dispose() {
        sr.dispose();
    }
}
