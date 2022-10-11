package effects.awesome;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import effects.awesome.ui.screens.BoardScreen;
import effects.awesome.ui.screens.DailySpinScreen;
import effects.awesome.ui.screens.UIScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ScreenController implements ApplicationListener {
	private UIScreen screen;
	public ScreenController(){

	}

	@Override
	public void create() {
		setScreen(new BoardScreen(this));
	}

	public void setScreen(UIScreen screen){
		UIScreen previousScreen=this.screen;
		if (previousScreen!=null){
			previousScreen.hide();
		}

		if (screen!=null){
			this.screen=screen;
			this.screen.show();
		}else {
			this.screen=null;
		}
		if (previousScreen!=null){
			previousScreen.dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (screen!=null)screen.resize(width,height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Color.BROWN,false);
		if (screen!=null)screen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void pause() {
		if (screen!=null)screen.pause();
	}

	@Override
	public void resume() {
		if (screen!=null)screen.resume();
	}

	@Override
	public void dispose() {
		if (screen!=null){
			screen.dispose();
			screen=null;
		}
	}
}