package hu.ris.roofify.render.modes;

import hu.ris.roofify.render.RenderMode;

/**
 * Enumeration of different render mode types. {@link RenderMode}
 */
public enum RenderModeType {
    CHECKMARK, COUNT_BEDROCKS, COUNT_BEDROCKS_BREAK;

    public String getSimpleName() {
        return this.name().toLowerCase();
    }
}
