package dev.tomat.bridge.constellar.client.gui;

public interface IBackgroundPanorama {
    void drawPanorama(float timerOffset);

    void rotateAndBlurSkybox();

    void render(float timerOffset);
}
