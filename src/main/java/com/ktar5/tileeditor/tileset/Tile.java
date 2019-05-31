package com.ktar5.tileeditor.tileset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ktar5.tileeditor.util.ToolSerializeable;
import com.ktar5.utilities.common.data.Pair;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Tile implements ToolSerializeable {
    private final int direction;
    protected final Tileset tileset;
    protected final int blockId;

    public Tile(int blockId, int direction, Tileset tileset) {
        this.tileset = tileset;
        this.blockId = blockId;
        this.direction = direction;
    }

    @Override
    public String serialize() {
        return toString();
    }

    @Override
    public String toString() {
        if (direction == 0) {
            return String.valueOf(blockId);
        } else {
            return blockId + "_" + direction;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return equalsTile(tile);
    }

    public boolean equalsTile(Tile tile) {
        if (tile == null) {
            return false;
        }
        return blockId == tile.blockId && direction == tile.direction && tileset.getId().equals(tile.getTileset().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, direction);
    }

    public Pair toXY() {
        int y = getBlockId() / tileset.getColumns();
        int x = getBlockId() % tileset.getColumns();
        return new Pair(x, y);
    }

    public TextureRegion getTextureRegion() {
        return getTileset().getTileImages().get(blockId);
    }

}

