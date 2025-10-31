package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import com.google.appinventor.components.runtime.util.Ev3Constants;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a LEGO MINDSTORMS EV3 robot, with functions to draw graphs on EV3 screen.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3UI extends LegoMindstormsEv3Base {
    public Ev3UI(ComponentContainer container) {
        super(container, "Ev3UI");
    }

    @SimpleFunction(description = "Draw a point on the screen.")
    public void DrawPoint(int color, int x, int y) {
        int i = color;
        if (i == 0 || i == 1) {
            sendCommand("DrawPoint", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccc", (byte) 2, Byte.valueOf((byte) i), Short.valueOf((short) x), Short.valueOf((short) y)), false);
            sendCommand("DrawPoint", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "DrawPoint", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawPoint");
    }

    @SimpleFunction(description = "Draw a built-in icon on screen.")
    public void DrawIcon(int color, int x, int y, int type, int no) {
        int i = color;
        if (i == 0 || i == 1) {
            sendCommand("DrawIcon", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", (byte) 6, Byte.valueOf((byte) i), Short.valueOf((short) x), Short.valueOf((short) y), Integer.valueOf(type), Integer.valueOf(no)), false);
            sendCommand("DrawIcon", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "DrawIcon", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawIcon");
    }

    @SimpleFunction(description = "Draw a line on the screen.")
    public void DrawLine(int color, int x1, int y1, int x2, int y2) {
        int i = color;
        if (i == 0 || i == 1) {
            sendCommand("DrawLine", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", (byte) 3, Byte.valueOf((byte) i), Short.valueOf((short) x1), Short.valueOf((short) y1), Short.valueOf((short) x2), Short.valueOf((short) y2)), false);
            sendCommand("DrawLine", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "DrawLine", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawLine");
    }

    @SimpleFunction(description = "Draw a rectangle on the screen.")
    public void DrawRect(int color, int x, int y, int width, int height, boolean fill) {
        int i = color;
        if (i == 0 || i == 1) {
            sendCommand("DrawRect", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccccc", Byte.valueOf(fill ? (byte) 9 : 10), Byte.valueOf((byte) i), Short.valueOf((short) x), Short.valueOf((short) y), Short.valueOf((short) width), Short.valueOf((short) height)), false);
            sendCommand("DrawRect", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "DrawRect", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawRect");
    }

    @SimpleFunction(description = "Draw a circle on the screen.")
    public void DrawCircle(int color, int x, int y, int radius, boolean fill) {
        int i = color;
        int i2 = radius;
        if ((i == 0 || i == 1) && i2 >= 0) {
            sendCommand("DrawCircle", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "ccccc", Byte.valueOf(fill ? (byte) 24 : 4), Byte.valueOf((byte) i), Short.valueOf((short) x), Short.valueOf((short) y), Short.valueOf((short) i2)), false);
            sendCommand("DrawCircle", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "DrawCircle", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "DrawCircle");
    }

    @SimpleFunction(description = "Fill the screen with a color.")
    public void FillScreen(int color) {
        if (color == 0 || color == 1) {
            sendCommand("FillScreen", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "cccc", (byte) 19, Byte.valueOf((byte) color), (short) 0, (short) 0), false);
            sendCommand("FillScreen", Ev3BinaryParser.encodeDirectCommand(Ev3Constants.Opcode.UI_DRAW, false, 0, 0, "c", (byte) 0), false);
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "FillScreen", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, "FillScreen");
    }
}
