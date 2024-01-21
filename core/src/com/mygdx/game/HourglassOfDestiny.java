package com.mygdx.game;

import Screens.MenuScreen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class HourglassOfDestiny extends Game{
	public AssetManager assetManager;
	public BitmapFont font;

	@Override
	public void create () {

		assetManager = new AssetManager();
		font = new BitmapFont();
		setScreen(new MenuScreen(this));
	}
	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		font.dispose();
	}

	public void switchScreen(Screen screen) {
		setScreen(screen);
	}
}
