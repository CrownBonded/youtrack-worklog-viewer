package de.pbauerochse.worklogviewer.settings;

import de.pbauerochse.worklogviewer.settings.properties.Property;

/**
 * @author Patrick Bauerochse
 */
public class WindowSettings {

    @Property("window.width")
    private int width = 800;

    @Property("window.height")
    private int height = 600;

    @Property("window.x")
    private int posX = 0;

    @Property("window.y")
    private int posY = 0;

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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
