package com.turnbased.game;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = true;
    private static final String RAW_ASSET_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.stripWhitespaceX = false;
        settings.stripWhitespaceY = false;


//        settings.pot = false;
//        settings.maxWidth = 484;
//        settings.maxHeight = 804;

        TexturePacker.process(settings,
                RAW_ASSET_PATH + "/yato",
                ASSETS_PATH + "yato",
                "/yato");

//        TexturePacker.process(settings,
//                RAW_ASSET_PATH + "/ui",
//                ASSETS_PATH + "/ui",
//                "/ui");

//        TexturePacker.process(settings,
//                RAW_ASSET_PATH + "/skin",
//                ASSETS_PATH + "/skin",
//                "/uiskin");
//    }




}}
