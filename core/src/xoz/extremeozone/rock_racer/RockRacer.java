package xoz.extremeozone.rock_racer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RockRacer extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private float updateTime = 0;
	private static boolean end;
	private static int endTime = 300;
	private BitmapFont font;
	private float score = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		AssetLoader.load();
		World.create();
	}

	public void update (float delta) {
		updateTime += delta;
		if(updateTime > 0.01){
			updateTime -= 0.01;
			if(collision()) {
				end = true;
			}
			World.setSpeed(score);
			World.update();
		}
		score += delta;
	}

	public boolean collision() {
		Vector2[] left = World.getLeftPoints();
		Vector2[] right = World.getRightPoints();
		for(int i=0; i<left.length; i++){
			// if the wall point is between the height of the player
			if(left[i].y >= Player.getPoints()[0].y && left[i].y <= Player.getPoints()[7].y) {
				for (int j = 0; j < Player.getPoints().length; j++) {
					float px = Player.getPoints()[j].x;
					if(px < left[i].x || px > right[i].x)
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void render () {
		if(!end) {
			update(Gdx.graphics.getDeltaTime());
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			World.Draw();
			batch.begin();
			font.setColor(Color.BLACK);
			font.draw(batch, "Score: "+(int)score,20, Gdx.graphics.getHeight()-20);
			batch.end();
		}else if ((endTime -= Gdx.graphics.getDeltaTime()) > 0) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.setColor(Color.WHITE);
			font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			font.draw(batch, "Final Score: "+(int)score, Gdx.graphics.getWidth() / 2, (Gdx.graphics.getHeight()/2)-20);
			batch.end();
		}else {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) { World.resize(width, height); }
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		World.dispose();
		AssetLoader.dispose();
	}
}
