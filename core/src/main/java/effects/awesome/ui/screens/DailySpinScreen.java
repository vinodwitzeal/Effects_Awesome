package effects.awesome.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;

import effects.awesome.ScreenController;
import effects.awesome.ui.widgets.DailySpin;
import effects.awesome.ui.widgets.GiftAnimation;
import effects.awesome.ui.widgets.MaskImage;
import effects.awesome.ui.widgets.SpineAnimation;

public class DailySpinScreen extends UIScreen{
    public DailySpinScreen(ScreenController controller) {
        super(controller);
    }

    @Override
    public void buildUI() {
        Table mainTable=new Table();

        TextureAtlas testAtlas=new TextureAtlas("test.atlas");
        MaskImage maskImage=new MaskImage(testAtlas.findRegion("character"),testAtlas.findRegion("circle"));
        mainTable.add(new MaskImage(testAtlas.findRegion("character"),testAtlas.findRegion("circle"))).width(400).height(400).padBottom(20).row();
        mainTable.add(new MaskImage(testAtlas.findRegion("character"),testAtlas.findRegion("circle"))).width(200).height(200).padBottom(20).row();
        mainTable.add(new MaskImage(testAtlas.findRegion("character"),testAtlas.findRegion("circle"))).width(300).height(300).padBottom(20).row();

        Image image=new Image(testAtlas.findRegion("character"));
       mainTable.add(image).width(200).height(200);
       mainTable.setFillParent(true);
        stage.addActor(mainTable);
    }
}
