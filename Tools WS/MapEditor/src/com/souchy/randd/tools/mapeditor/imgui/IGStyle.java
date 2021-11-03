package com.souchy.randd.tools.mapeditor.imgui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.badlogic.gdx.Gdx;
import com.souchy.randd.tools.mapeditor.main.MapEditorCore;

import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.internal.ImGui;

public class IGStyle {
	
	
	public static void style() {
		var style = ImGui.getStyle();
		//style.

		// remove the V button on windows
//		ImGui.getStyle().setWindowMenuButtonPosition(ImGuiDir.None);
		
		// remove borders
		ImGui.getStyle().setColor(ImGuiCol.Border, 255, 0, 0, 0);
//		ImGui.getStyle().setColor(ImGuiCol.FrameBg, 255, 0, 0, 0);
//		ImGui.getStyle().setColor(ImGuiCol.ChildBg, 255, 0, 0, 0);
//		ImGui.getStyle().setColor(ImGuiCol.TableRowBg, 255, 0, 0, 0);
//		ImGui.getStyle().setColor(ImGuiCol.DockingEmptyBg, 255, 0, 0, 0);
		ImGui.getStyle().setColor(ImGuiCol.WindowBg, 8, 8, 8, 200);
		ImGui.getStyle().setColor(ImGuiCol.MenuBarBg, 24, 24, 24, 200);
//		ImGui.getStyle().setColor(ImGuiCol.Tab, 50, 0, 0, 200);
//		
//
//		ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 100, 0, 0, 255);
//		ImGui.getStyle().setColor(ImGuiCol.TabActive, 100, 0, 0, 255);

//		ImGui.getStyle().setColor(ImGuiCol.Button, 64, 64, 128, 150);
	}
	
	
	public static void initImGui() {
        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
//        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);
        
        initFonts(io);
	}

    /**
     * Example of fonts configuration
     * For more information read: https://github.com/ocornut/imgui/blob/33cdbe97b8fd233c6c12ca216e76398c2e89b0d8/docs/FONTS.md
     */
    private static void initFonts(final ImGuiIO io) {
        io.getFonts().addFontDefault(); // Add default font for latin glyphs

        // You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based on text input.
        // For example: for a game where your script is known, if you can feed your entire script to it (using addText) and only build the characters the game needs.
        // Here we are using it just to combine all required glyphs in one place
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after atlas is built
        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);  // Enable merge mode to merge cyrillic, japanese and icons with default font

        final short[] glyphRanges = rangesBuilder.buildRanges();
        io.getFonts().addFontFromMemoryTTF(loadFromResources("Tahoma.ttf"), 14, fontConfig, glyphRanges); // cyrillic glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("NotoSansCJKjp-Medium.otf"), 14, fontConfig, glyphRanges); // japanese glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().build();

        fontConfig.destroy();
    }

	private static byte[] loadFromResources(String name) {
		return Gdx.files.internal("res/fonts/" + name).readBytes();
//        try {
//            return Files.readAllBytes(Paths.get(MapEditorCore.class.getResource(name).toURI()));
//        } catch (IOException | URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
    }
    
}
