package effects.awesome.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import effects.awesome.ScreenController;
import effects.awesome.ui.fonts.FontPool;
import effects.awesome.ui.fonts.FontType;

//
// Created by Vinod(vinod.kumar@witzeal.com)on 09/01/23.
//
// Copyright (c) 2023 Witzeal Technologies Private Limited. All rights reserved.
//
public class LoginScreen extends UIScreen{
    public LoginScreen(ScreenController controller) {
        super(controller);
    }

    @Override
    public void buildUI() {
        Table mainTable=new Table();

        Label.LabelStyle titleStyle=new Label.LabelStyle();
        titleStyle.font= FontPool.obtain(FontType.ROBOTO_BOLD,20);
        titleStyle.fontColor= Color.valueOf("000000");
        Label titleLabel=new Label("LOGIN",titleStyle);
        mainTable.add(titleLabel);
        mainTable.row();

        mainTable.setFillParent(true);

        stage.addActor(mainTable);
    }
}
