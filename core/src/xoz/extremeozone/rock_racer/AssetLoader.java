package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture ship;
    public static Texture asteroidInside;
    public static Texture asteroidOutside;

    public static void load() {
        ship = new Texture(Gdx.files.internal("PlayerShip.png"));
        asteroidInside = new Texture(Gdx.files.internal("AsteroidInside.png"));
        asteroidOutside = new Texture(Gdx.files.internal("AsteroidOutside.png"));
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        ship.dispose();
        asteroidOutside.dispose();
        asteroidInside.dispose();
    }
}
