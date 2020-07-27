package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;

public class AsteroidOutside {
    private PolygonRegion left, right;
    private PolygonSpriteBatch batch;
    private float[] vert;

    public AsteroidOutside() {
        batch = new PolygonSpriteBatch();

    }

    public void draw(Vector2[] lp, Vector2[] rp) {
        left = new PolygonRegion(new TextureRegion(AssetLoader.asteroidOutside), vertices(lp,0), triangles());
        right = new PolygonRegion(new TextureRegion(AssetLoader.asteroidOutside), vertices(rp,1), triangles());
        batch.begin();
        batch.draw(left, 0,0);
        batch.draw(right, 0,0);
        batch.end();
    }

    public float[] vertices(Vector2[] path, int test) {
        vert = new float[path.length+4];
        for(int i=0; i<path.length; i+=2) {
            vert[i] = path[i].x;
            vert[i + 1] = path[i].y;
        }
        if(test==0) {
            vert[path.length] = 0;
            vert[path.length+1] = path[path.length-1].y;
            vert[path.length+2] = 0;
            vert[path.length+3] = path[0].y;
        }else{
            vert[path.length] = Gdx.graphics.getWidth();
            vert[path.length+1] = path[path.length-1].y;
            vert[path.length+2] = Gdx.graphics.getWidth();
            vert[path.length+3] = path[0].y;
        }
        return vert;
    }

    public short[] triangles() {
        ShortArray tri = new EarClippingTriangulator().computeTriangles(vert);
        return tri.toArray();
    }
}
