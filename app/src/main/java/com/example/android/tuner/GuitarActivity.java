package com.example.android.tuner;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class GuitarActivity extends AppCompatActivity {
    private final String DEVICE_ADDRESS="20:15:04:16:50:67";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

//    Button startButton, sendButton,clearButton,stopButton;
//    TextView textView;
//    EditText editText;
    boolean deviceConnected=false;
    byte buffer[];
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitar);

        getSupportActionBar().setTitle("Guitar");

        /** No sé com fer per a que es pugui traduir**/
        start();
        try {
            outputStream.write("hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if(BTinit()) {
            if(BTconnect()) {
                deviceConnected=true;
                beginListenForData();
            }
        }
    }

    private interface MessageConstants {
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;
    }

    // Call this from the main activity to send data to the remote device.
    public void write(byte[] bytes) {
        Handler handler = new Handler();
        try {outputStream.write(bytes);
            // Share the sent message with the UI activity.
            Message writtenMsg = handler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, buffer);
            writtenMsg.sendToTarget();
        }
        catch (IOException e) {Log.e("Debug", "Error occurred when sending data", e);
            // Send a failure message back to the activity.
            Message writeErrorMsg = handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast", "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            handler.sendMessage(writeErrorMsg);
        }
    }

    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device does not Support Bluetooth", Toast.LENGTH_SHORT).show();
        }

        int REQUEST_ENABLE_BT = 1;
        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();

        if (pairedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : pairedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect() {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }

        if(connected) {
            try {
                outputStream=socket.getOutputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }

    void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopThread) {
                    try {int byteCount = inputStream.available();
                        if(byteCount > 0) {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String recMes = new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run() {
                                Toast.makeText(toasty, recMes, duration).show();
                                if(recMes == "lower" || recMes == "higher"){
                                    sendRotateOrder(recMes);
                                }
                                }
                            });
                        }
                    }
                    catch (IOException ex) {
                        stopThread = true;
                    }
                }
            }
        });
        thread.start();
    }

    Context toasty = GuitarActivity.this;
    int duration = Toast.LENGTH_SHORT;

    private void pressString(String noteName, String noteCode){
        CharSequence pressed = "Tuning to " + noteName;
        Toast.makeText(toasty, pressed, duration).show();

        try {
            outputStream.write(noteCode.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        openTunningActivity();

        /**
         send "string1" to Arduino
         boolean done = false;
         while (done == false) {
         wait for orders
         receive "listen" from Arduino
         listen for sound
         if (sound > tune) send "tune down" to Arduino
         else if (sound < tune) send "tune up" to Arduino
         else if (sound == tune) {
         send "done" to Arduino;
         done = true;
         }
         }
         **/
    }

    public void pressString1(View view) {
        this.pressString( "E", "1");
    }

    public void pressString2(View view) {
        this.pressString( "A", "2");
    }

    public void pressString3(View view) {
        this.pressString( "D", "3");
    }

    public void pressString4(View view) {
        this.pressString( "G", "4");
    }

    public void pressString5(View view) {
        this.pressString( "B", "5");
    }

    public void pressString6(View view) {
        this.pressString( "E", "6");
    }

    protected void sendRotateOrder(String currentFreqRelativePosition) {
        String order = (currentFreqRelativePosition == "lower" ? "t_left" : "t_right");
        CharSequence orderMes = "Rotating " + order;
        Toast.makeText(toasty, orderMes, duration).show();

        try {
            outputStream.write(order.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openTunningActivity() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setComponent(new ComponentName("com.noisepages.nettoyeur",
                "com.noisepages.nettoyeur.guitartuner.GuitarTunerActivity"));
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        try {
            startActivityForResult(i, 12345);
        } catch (Exception e) {
            Toast.makeText(toasty, "error opening tuner: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (12345) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String recMes = data.getStringExtra("relativePitch");
                    Toast.makeText(toasty, recMes, duration).show();
                    if(recMes == "lower" || recMes == "higher"){
                        sendRotateOrder(recMes);
                    }
                }
                break;
            }
        }
    }
}


