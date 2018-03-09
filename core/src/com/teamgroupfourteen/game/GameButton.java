package com.teamgroupfourteen.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by nick on 3/8/18.
 */

public class GameButton {
    private float x;
    private float y;
    private int width;
    private int height;
    private Texture image;

    public GameButton(float x, float y, int width, int height, String filePath) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setImage(new Texture(filePath));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }
}
