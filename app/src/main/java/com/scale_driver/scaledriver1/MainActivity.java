package com.scale_driver.scaledriver1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.ble_handle.BleUtils;
import com.scale_driver.scaledriver1.ble_handle.ScannerFragment;
import com.scale_driver.scaledriver1.settings.SettingsActivity;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScannerFragment.OnDeviceSelectedListener, BtnsFragment.btnListener {
    EditText etSend;
    private TextView tvData;
    Button btn_send;
    SharedPreferences sp;
    BtnsFragment btnsFragment;
    Boolean fragmentVisible = false;
    FragmentTransaction fTrans;
    private static final String TAG = "myUart";
    protected static final int REQUEST_ENABLE_BT = 2;
    public UartService mService = null;
    public boolean mDeviceConnected = false;
    public Handler mHandler;
    public String tx_data;
    Button btn_connect;
    private BluetoothDevice mDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnsFragment = new BtnsFragment();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        service_init();
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                Toast.makeText(MainActivity.this, msg.what, Toast.LENGTH_SHORT).show();
            }
        };

        btn_connect = (Button) findViewById(R.id.action_connect);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        tvData = (TextView) findViewById(R.id.tvData);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean showBtns = preference.getBoolean("showBtns", true);
        fTrans = getFragmentManager().beginTransaction();
        fTrans.addToBackStack("");

        if(showBtns && !fragmentVisible) {

            fragmentVisible = true;
            fTrans.add(R.id.BtnsFrag, btnsFragment);
            Toast.makeText(this, "show btns", Toast.LENGTH_SHORT).show();
        }
        else if (!showBtns && fragmentVisible){
            fragmentVisible = false;
            fTrans.remove(btnsFragment);
            Toast.makeText(this, "hide btns", Toast.LENGTH_SHORT).show();
            //fTrans.remove(btnsFragment);
        }
        fTrans.commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettings();
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            showSettings();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onConnectClicked(View view) {
        if (isBLEEnabled()) {
            if (!mDeviceConnected) {
                showDeviceScanningDialog(null);
                btn_connect.setText(R.string.btn_disconnect);
            } else {
                btn_connect.setText(R.string.btn_connect);
                mService.disconnect();
                mDeviceConnected = false;
            }


        } else showBLEDialog();
    }
    private void showDeviceScanningDialog(final UUID filter) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ScannerFragment dialog = ScannerFragment.getInstance(filter);
                dialog.show(getSupportFragmentManager(), "scan_fragment");
            }
        });
    }
    // Check either BLE enabled or not
    protected boolean isBLEEnabled() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled();
    }
    //Ask user to turn bluetooth on
    protected void showBLEDialog() {
        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }
    // implemented ScannerFragment method getting info about selected device
    @Override
    public void onDeviceSelected(BluetoothDevice device, String name) {


        mDevice = device;
        String deviceaddress = mDevice.getAddress();
        mService.connect(deviceaddress);
    }
    @Override
    public void onDialogCanceled() {

    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            //Log.d(TAG, "onServiceConnected mService= " + mService);
            BleUtils.showmsg("onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    };
    private final BroadcastReceiver UartBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            final Intent mIntent = intent;
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Uart connected");
                        mDeviceConnected = true;
                    }
                });
            }
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Uart disconnected");
                        mService.close();
                    }
                });
            }
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tx_data = new String(txValue, "UTF-8");
                            BleUtils.showmsg(tx_data);
                            tvData.setText(tx_data);
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
                Toast.makeText(context, "Device doesn't support UART. Disconnecting...", Toast.LENGTH_SHORT).show();
                mService.disconnect();
            }
        }
    };
    private void service_init() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(UartBroadcastReceiver, BleUtils.makeGattUpdateIntentFilter());
    }
    public void sendClick(View view) {

        etSend = (EditText) findViewById(R.id.etSend);
        String data = etSend.getText().toString();

        if(!data.isEmpty()) {
            SharedPreferences btns = this.getSharedPreferences(BtnsFragment.btnPrefValues, Context.MODE_PRIVATE);
            String btnValue = btns.getString(data, "no data");
            if (!btnValue.equals("no data")) {
                BleUtils.sendMsgBle(this, mService, btnValue, mDeviceConnected);
            } else {
                BleUtils.sendMsgBle(this, mService, data, mDeviceConnected);

            }
        } else {
            Toast.makeText(this, "Необходимо ввести текст", Toast.LENGTH_SHORT).show();
        }

    }
    public void showSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    @Override
    public void CorrectBtnClicked(String s) {
        BleUtils.sendMsgBle(this, mService, s, mDeviceConnected);

    }
}
