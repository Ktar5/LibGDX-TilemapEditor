package com.ktar5.tileeditor.tilemap;

import com.ktar5.tileeditor.properties.RootProperty;
import com.ktar5.tileeditor.tileset.Tilesets;
import com.ktar5.tileeditor.util.Tabbable;
import com.ktar5.utilities.annotation.callsuper.CallSuper;
import com.ktar5.utilities.common.undo.UndoStack;
import com.ktar5.utilities.common.undo.UndoStack_Linked;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Tilemap implements Tabbable {
    private final Layers layers;
    private final Tilesets tilesets;
    private final RootProperty rootProperty;
    private final UndoStack_Linked undoStack;

    private final int tileWidth, tileHeight;
    private final int numTilesWide, numTilesHigh;
    private final int dimensionX, dimensionY;

    private final UUID id;
    private File saveFile;

    /**
     * This constructor is used to deserialize tilemap.
     * It **MUST** be overrode by all subclasses.
     *
     * @param saveFile the file used for saving
     * @param json     the json serialization of the tilemap
     */
    public Tilemap(File saveFile, JSONObject json) {
        this(saveFile,
                json.getJSONObject("dimensions").getInt("numTilesWide"),
                json.getJSONObject("dimensions").getInt("numTilesHigh"),
                json.getJSONObject("dimensions").getInt("tileWidth"),
                json.getJSONObject("dimensions").getInt("tileHeight"),
                UUID.fromString(json.getString("identifier")));
        rootProperty.deserialize(json.getJSONObject("properties"));
        tilesets.loadTilesets(json.getJSONArray("tilesets"));
        layers.deserialize(json.getJSONArray("layers"));
    }

    /**
     * This constructor is used to initialize tilemap on creation.
     * It **MUST** be overrode by all subclasses.
     *
     * @param saveFile     the file used for saving
     * @param numTilesWide the number of tiles in the X direction
     * @param numTilesHigh the number of tiles in the Y direction
     * @param tileWidth    pixel width of a tile on this map
     * @param tileHeight   pixel height of a tile on this map
     */
    public Tilemap(File saveFile, int numTilesWide, int numTilesHigh, int tileWidth, int tileHeight, UUID id) {
        this.undoStack = new UndoStack_Linked();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numTilesHigh = numTilesHigh;
        this.numTilesWide = numTilesWide;

        this.dimensionX = tileWidth * numTilesWide;
        this.dimensionY = tileHeight * numTilesHigh;

        this.saveFile = saveFile;
        this.rootProperty = new RootProperty();
        this.id = id;
        this.layers = new Layers(this);
        this.tilesets = new Tilesets(this);
    }

    /**
     * @return true if the x and y are within the bounds of the map
     */
    public boolean isInMapRange(int x, int y) {
        return x >= 0 && x < dimensionX && y >= 0 && y < dimensionY;
    }

    /**
     * Serializes the information stored in the tilemap to a json file.
     * Subclasses should override this and provide specific implementations.
     * NOTE: all subclasses must call super or else code won't compile.
     */
    @Override
    @CallSuper
    public JSONObject serialize() {
        JSONObject json = new JSONObject();

        getRootProperty().serialize(json);

        json.put("identifier", id.toString());

        JSONObject dimensions = new JSONObject();
        dimensions.put("numTilesWide", getNumTilesWide());
        dimensions.put("numTilesHigh", getNumTilesHigh());
        dimensions.put("tileWidth", getTileWidth());
        dimensions.put("tileHeight", getTileHeight());
        json.put("dimensions", dimensions);

        json.put("tilesets", tilesets.serialize());

        json.put("layers", layers.serialize());
        return json;
    }

    /**
     * Saves the map to a file.
     */
    @Override
    public void save() {
        MapManager.get().saveMap(getId());
    }

    /**
     * Gets the name of the map.
     */
    @Override
    public String getName() {
        return getSaveFile().getName();
    }

    /**
     * Removes the map from the application.
     */
    @Override
    public void remove() {
        MapManager.get().remove(getId());
    }

    /**
     * Change the save file to a different file.
     */
    @Override
    public void updateSaveFile(File file) {
        this.saveFile = file;
    }

    @Override
    public void undo() {
        undoStack.undo();
    }

    @Override
    public void redo() {
        undoStack.redo();
    }

}
