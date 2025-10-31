package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;

@SimpleObject
public class LegoMindstormsEv3Sensor extends LegoMindstormsEv3Base {
    protected static final String DEFAULT_SENSOR_PORT = "1";
    protected int sensorPortNumber;

    protected LegoMindstormsEv3Sensor(ComponentContainer container, String logTag) {
        super(container, logTag);
        SensorPort("1");
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = false)
    public String SensorPort() {
        return portNumberToSensorPortLetter(this.sensorPortNumber);
    }

    @DesignerProperty(defaultValue = "1", editorType = "lego_ev3_sensor_port")
    @SimpleProperty
    public void SensorPort(String sensorPortLetter) {
        setSensorPort("SensorPort", sensorPortLetter);
    }

    /* access modifiers changed from: protected */
    public final void setSensorPort(String functionName, String sensorPortLetter) {
        try {
            this.sensorPortNumber = sensorPortLetterToPortNumber(sensorPortLetter);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_SENSOR_PORT, sensorPortLetter);
        }
    }

    /* access modifiers changed from: protected */
    public final int readInputPercentage(String functionName, int layer, int no, int type, int mode) {
        int i = layer;
        int i2 = no;
        int i3 = mode;
        if (i < 0 || i > 3 || i2 < 0 || i2 > 3 || i3 < -1 || i3 > 7) {
            String str = functionName;
            int i4 = type;
            throw new IllegalArgumentException();
        }
        Object[] objArr = {(byte) 27, Byte.valueOf((byte) i), Byte.valueOf((byte) i2), Byte.valueOf((byte) type), Byte.valueOf((byte) i3), (byte) 1, (byte) 0};
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 1, 0, "ccccccg", objArr), true);
        if (reply != null && reply.length == 2 && reply[0] == 2) {
            return reply[1];
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public final double readInputSI(String functionName, int layer, int no, int type, int mode) {
        String str = functionName;
        int i = layer;
        int i2 = no;
        int i3 = mode;
        if (i < 0 || i > 3 || i2 < 0 || i2 > 3 || i3 < -1 || i3 > 7) {
            int i4 = type;
            throw new IllegalArgumentException();
        }
        byte[] reply = sendCommand(str, Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 4, 0, "ccccccg", (byte) 29, Byte.valueOf((byte) i), Byte.valueOf((byte) i2), Byte.valueOf((byte) type), Byte.valueOf((byte) i3), (byte) 1, (byte) 0), true);
        if (reply != null && reply.length == 5 && reply[0] == 2) {
            return (double) ((Float) Ev3BinaryParser.unpack("xf", reply)[0]).floatValue();
        }
        this.form.dispatchErrorOccurredEvent(this, str, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return -1.0d;
    }
}
