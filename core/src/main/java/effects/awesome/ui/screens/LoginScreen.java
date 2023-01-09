package effects.awesome.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;

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
        mainTable.top();

        Label.LabelStyle titleStyle=new Label.LabelStyle();
        titleStyle.font= FontPool.obtain(FontType.ROBOTO_BOLD,20);
        titleStyle.fontColor= Color.valueOf("000000");
        Label titleLabel=new Label("LOGIN",titleStyle);
        mainTable.add(titleLabel).padBottom(100);
        mainTable.row();


        NinePatchDrawable drawable=new NinePatchDrawable(new NinePatch(new Texture("bg_textfield.png"),8,8,8,8));
        drawable.setMinSize(4,4);


        TextField.TextFieldStyle textFieldStyle=new TextField.TextFieldStyle();
        textFieldStyle.font=FontPool.obtain(FontType.ROBOTO_MEDIUM,10);
        textFieldStyle.fontColor=Color.valueOf("FFFFFF");
        textFieldStyle.messageFontColor=Color.valueOf("FFFFFFBB");
        textFieldStyle.background=drawable.tint(Color.valueOf("999999"));
        textFieldStyle.cursor= drawable;

        float textFieldHeight=textFieldStyle.font.getLineHeight()*2f;

        TextField emailField=new TextField("",textFieldStyle);
        emailField.setMessageText("Enter Email");
        mainTable.add(emailField).width(width*0.8f).height(textFieldHeight).padBottom(20);
        mainTable.row();


        TextField passwordField=new TextField("",textFieldStyle);
        passwordField.setMessageText("Enter Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        mainTable.add(passwordField).width(width*0.8f).height(textFieldHeight).padBottom(20);
        mainTable.row();


        TextButton.TextButtonStyle loginButtonStyle=new TextButton.TextButtonStyle();
        loginButtonStyle.up=drawable.tint(Color.valueOf("00BB00"));
        loginButtonStyle.font=FontPool.obtain(FontType.ROBOTO_MEDIUM,12);
        loginButtonStyle.fontColor=Color.valueOf("FFFFFF");

        TextButton loginButton=new TextButton("LOGIN",loginButtonStyle);

        mainTable.add(loginButton).width(width*0.5f).height(textFieldHeight);

        mainTable.setFillParent(true);

        stage.addActor(mainTable);
    }
}
