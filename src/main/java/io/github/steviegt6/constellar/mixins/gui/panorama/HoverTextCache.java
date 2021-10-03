package io.github.steviegt6.constellar.mixins.gui.panorama;

import java.util.List;

public class HoverTextCache {
    public List<String> hoveringText;
    public int hoveringText2;
    public int hoveringText3;

    public HoverTextCache(List<String> hoveringText, int hoveringText2, int hoveringText3) {
        this.hoveringText = hoveringText;
        this.hoveringText2 = hoveringText2;
        this.hoveringText3 = hoveringText3;
    }
}
