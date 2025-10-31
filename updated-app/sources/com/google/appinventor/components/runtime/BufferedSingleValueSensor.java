package com.google.appinventor.components.runtime;

import android.hardware.SensorEvent;
import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public abstract class BufferedSingleValueSensor extends SingleValueSensor {
    private AveragingBuffer buffer;

    public BufferedSingleValueSensor(ComponentContainer container, int sensorType, int bufferSize) {
        super(container.$form(), sensorType);
        this.buffer = new AveragingBuffer(bufferSize);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled && sensorEvent.sensor.getType() == this.sensorType) {
            this.buffer.insert(Float.valueOf(sensorEvent.values[0]));
            super.onSensorChanged(sensorEvent);
        }
    }

    /* access modifiers changed from: protected */
    public float getAverageValue() {
        return this.buffer.getAverage();
    }

    private class AveragingBuffer {
        private Float[] data;
        private int next;

        private AveragingBuffer(int size) {
            this.data = new Float[size];
            this.next = 0;
        }

        /* access modifiers changed from: private */
        public void insert(Float datum) {
            Float[] fArr = this.data;
            int i = this.next;
            this.next = i + 1;
            fArr[i] = datum;
            if (this.next == this.data.length) {
                this.next = 0;
            }
        }

        /* access modifiers changed from: private */
        public float getAverage() {
            double d;
            double sum = 0.0d;
            int count = 0;
            for (int i = 0; i < this.data.length; i++) {
                if (this.data[i] != null) {
                    double floatValue = (double) this.data[i].floatValue();
                    Double.isNaN(floatValue);
                    sum += floatValue;
                    count++;
                }
            }
            if (count == 0) {
                d = sum;
            } else {
                double d2 = (double) count;
                Double.isNaN(d2);
                d = sum / d2;
            }
            return (float) d;
        }
    }
}
