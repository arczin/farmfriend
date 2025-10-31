package com.google.appinventor.components.runtime;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PermissionConstraint;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "Bluetooth client component", iconName = "images/bluetooth.png", nonVisible = true, version = 8)
@SimpleObject
@UsesPermissions({"android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"})
public final class BluetoothClient extends BluetoothConnectionBase implements RealTimeDataSource<String, String> {
    private static final String[] RUNTIME_PERMISSIONS = {"android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"};
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private Set<Integer> acceptableDeviceClasses;
    private final List<Component> attachedComponents = new ArrayList();
    private ScheduledExecutorService dataPollService;
    private final HashSet<DataSourceChangeListener> dataSourceObservers = new HashSet<>();
    private boolean noLocationNeeded = false;
    private int pollingRate = 10;

    public BluetoothClient(ComponentContainer container) {
        super(container, PropertyTypeConstants.PROPERTY_TYPE_BLUETOOTHCLIENT);
        DisconnectOnError(false);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Disconnects BluetoothClient automatically when an error occurs.")
    public boolean DisconnectOnError() {
        return this.disconnectOnError;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void DisconnectOnError(boolean disconnectOnError) {
        this.disconnectOnError = disconnectOnError;
    }

    /* access modifiers changed from: package-private */
    public boolean attachComponent(Component component, Set<Integer> acceptableDeviceClasses2) {
        HashSet hashSet;
        if (this.attachedComponents.isEmpty()) {
            if (acceptableDeviceClasses2 == null) {
                hashSet = null;
            } else {
                hashSet = new HashSet(acceptableDeviceClasses2);
            }
            this.acceptableDeviceClasses = hashSet;
        } else if (this.acceptableDeviceClasses == null) {
            if (acceptableDeviceClasses2 != null) {
                return false;
            }
        } else if (acceptableDeviceClasses2 == null || !this.acceptableDeviceClasses.containsAll(acceptableDeviceClasses2) || !acceptableDeviceClasses2.containsAll(this.acceptableDeviceClasses)) {
            return false;
        }
        this.attachedComponents.add(component);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void detachComponent(Component component) {
        this.attachedComponents.remove(component);
        if (this.attachedComponents.isEmpty()) {
            this.acceptableDeviceClasses = null;
        }
    }

    @SimpleFunction(description = "Checks whether the Bluetooth device with the specified address is paired.")
    public boolean IsDevicePaired(String address) {
        if (this.adapter == null) {
            this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", ErrorMessages.ERROR_BLUETOOTH_NOT_AVAILABLE, new Object[0]);
            return false;
        } else if (!this.adapter.isEnabled()) {
            this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED, new Object[0]);
            return false;
        } else {
            int firstSpace = address.indexOf(" ");
            if (firstSpace != -1) {
                address = address.substring(0, firstSpace);
            }
            if (!BluetoothAdapter.checkBluetoothAddress(address)) {
                this.form.dispatchErrorOccurredEvent(this, "IsDevicePaired", ErrorMessages.ERROR_BLUETOOTH_INVALID_ADDRESS, new Object[0]);
                return false;
            } else if (this.adapter.getRemoteDevice(address).getBondState() == 12) {
                return true;
            } else {
                return false;
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The addresses and names of paired Bluetooth devices")
    public List<String> AddressesAndNames() {
        if (Build.VERSION.SDK_INT >= 31) {
            String[] strArr = RUNTIME_PERMISSIONS;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                if (!this.form.isDeniedPermission(strArr[i])) {
                    i++;
                } else {
                    this.form.askPermission(new BulkPermissionRequest(this, "AddressesAndNames", RUNTIME_PERMISSIONS) {
                        public void onGranted() {
                        }
                    });
                    throw new StopBlocksExecution();
                }
            }
        }
        List<String> addressesAndNames = new ArrayList<>();
        if (this.adapter == null) {
            return addressesAndNames;
        }
        for (BluetoothDevice device : this.adapter.getBondedDevices()) {
            if (isDeviceClassAcceptable(device)) {
                String address = device.getAddress();
                addressesAndNames.add(address + " " + device.getName());
            }
        }
        return addressesAndNames;
    }

    @DesignerProperty(defaultValue = "10")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void PollingRate(int rate) {
        this.pollingRate = Math.max(rate, 1);
    }

    @SimpleProperty
    public int PollingRate() {
        return this.pollingRate;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @UsesPermissions(constraints = {@PermissionConstraint(name = "android.permission.BLUETOOTH_SCAN", usesPermissionFlags = "neverForLocation")})
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = false)
    public void NoLocationNeeded(boolean setting) {
        this.noLocationNeeded = setting;
    }

    @SimpleProperty(userVisible = false)
    public boolean NoLocationNeeded() {
        return this.noLocationNeeded;
    }

    private boolean isDeviceClassAcceptable(BluetoothDevice device) {
        if (this.acceptableDeviceClasses == null) {
            return true;
        }
        BluetoothClass clazz = device.getBluetoothClass();
        if (clazz == null) {
            return false;
        }
        return this.acceptableDeviceClasses.contains(Integer.valueOf(clazz.getDeviceClass()));
    }

    @SimpleFunction(description = "Connect to the Bluetooth device with the specified address and the Serial Port Profile (SPP). Returns true if the connection was successful.")
    public boolean Connect(String address) {
        return connect("Connect", address, SPP_UUID);
    }

    @SimpleFunction(description = "Connect to the Bluetooth device with the specified address and UUID. Returns true if the connection was successful.")
    public boolean ConnectWithUUID(String address, String uuid) {
        return connect("ConnectWithUUID", address, uuid);
    }

    /* access modifiers changed from: private */
    public boolean connect(final String functionName, String address, final String uuidString) {
        final String finalAddress = address;
        if (SUtil.requestPermissionsForConnecting(this.form, this, functionName, new PermissionResultHandler() {
            public void HandlePermissionResponse(String permission, boolean granted) {
                boolean unused = BluetoothClient.this.connect(functionName, finalAddress, uuidString);
            }
        })) {
            return false;
        }
        if (this.adapter == null) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_AVAILABLE, new Object[0]);
            return false;
        } else if (!this.adapter.isEnabled()) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED, new Object[0]);
            return false;
        } else {
            int firstSpace = address.indexOf(" ");
            if (firstSpace != -1) {
                address = address.substring(0, firstSpace);
            }
            if (!BluetoothAdapter.checkBluetoothAddress(address)) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_ADDRESS, new Object[0]);
                return false;
            }
            BluetoothDevice device = this.adapter.getRemoteDevice(address);
            if (device.getBondState() != 12) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_PAIRED_DEVICE, new Object[0]);
                return false;
            } else if (!isDeviceClassAcceptable(device)) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_REQUIRED_CLASS_OF_DEVICE, new Object[0]);
                return false;
            } else {
                try {
                    UUID uuid = UUID.fromString(uuidString);
                    Disconnect();
                    try {
                        connect(device, uuid);
                        return true;
                    } catch (IOException e) {
                        Disconnect();
                        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_CONNECT, new Object[0]);
                        return false;
                    }
                } catch (IllegalArgumentException e2) {
                    this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_UUID, uuidString);
                    return false;
                }
            }
        }
    }

    private void connect(BluetoothDevice device, UUID uuid) throws IOException {
        BluetoothSocket socket;
        if (!this.secure) {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
        } else {
            socket = device.createRfcommSocketToServiceRecord(uuid);
        }
        socket.connect();
        setConnection(socket);
        String str = this.logTag;
        String address = device.getAddress();
        Log.i(str, "Connected to Bluetooth device " + address + " " + device.getName() + ".");
    }

    private void startBluetoothDataPolling() {
        this.dataPollService = Executors.newSingleThreadScheduledExecutor();
        this.dataPollService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                String value = BluetoothClient.this.getDataValue((String) null);
                if (!value.equals("")) {
                    BluetoothClient.this.notifyDataObservers((String) null, (Object) value);
                }
            }
        }, 0, (long) this.pollingRate, TimeUnit.MILLISECONDS);
    }

    public synchronized void addDataObserver(DataSourceChangeListener dataComponent) {
        if (this.dataPollService == null) {
            startBluetoothDataPolling();
        }
        this.dataSourceObservers.add(dataComponent);
    }

    public synchronized void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
        if (this.dataSourceObservers.isEmpty()) {
            this.dataPollService.shutdown();
            this.dataPollService = null;
        }
    }

    public void notifyDataObservers(String key, Object newValue) {
        Iterator<DataSourceChangeListener> it = this.dataSourceObservers.iterator();
        while (it.hasNext()) {
            it.next().onReceiveValue(this, key, newValue);
        }
    }

    public String getDataValue(String key) {
        if (!IsConnected() || BytesAvailableToReceive() <= 0) {
            return "";
        }
        return ReceiveText(-1);
    }
}
