package com.ktar5.tileeditor.tilemap.layers.tile;

import com.ktar5.tileeditor.tileset.Tile;
import com.ktar5.utilities.common.undo.UndoCommand;

import java.util.Objects;

public final class SetTileCommand extends UndoCommand {
    private TileLayer layer;

    private int x;
    private int y;

    private Tile newTile;
    private Tile oldTile;

    SetTileCommand(TileLayer layer, int x, int y, Tile tile) {
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.newTile = tile;

        oldTile = layer.grid[x][y];
    }

    @Override
    protected final void undo() {
        layer.grid[x][y] = oldTile;
    }

    @Override
    protected final void redo() {
        layer.grid[x][y] = newTile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetTileCommand that = (SetTileCommand) o;
        return x == that.x &&
                y == that.y &&
                layer.equals(that.layer) &&
                Objects.equals(newTile, that.newTile) &&
                Objects.equals(oldTile, that.oldTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(layer, x, y, newTile, oldTile);
    }
}
