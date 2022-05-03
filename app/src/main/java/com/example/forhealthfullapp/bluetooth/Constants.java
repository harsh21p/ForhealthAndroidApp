package com.example.forhealthfullapp.bluetooth;

public interface Constants {

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    public static final float BORDER_THICKNESS = (float)0.025;

    public static final String PROTOCOL_VERSION = "2";
    public static final String CLIENT_NAME = "Blue Dot Android app";

}
