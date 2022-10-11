package effects.awesome.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import effects.awesome.PerspectiveViewport;
import effects.awesome.ScreenController;

public abstract class UIScreen implements Screen {
    public ScreenController controller;
    public Stage stage;
    public int width,height;
    public float viewportWidth,viewportHeight;
    public UIScreen(ScreenController controller){
        this.controller=controller;
    }

    protected void buildStage(){
        this.width=Gdx.graphics.getWidth();
        this.height= Gdx.graphics.getHeight();
        this.viewportWidth=this.width;
        this.viewportHeight=this.height;
        this.stage=new Stage(new ScreenViewport(),new PolygonSpriteBatch());
    }

    public abstract void buildUI();

    protected void setInputProcessor(){
        Gdx.input.setInputProcessor(stage);
    }

    public void removeInputProcessor(){
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void show() {
        buildStage();
        buildUI();
        setInputProcessor();
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        removeInputProcessor();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
