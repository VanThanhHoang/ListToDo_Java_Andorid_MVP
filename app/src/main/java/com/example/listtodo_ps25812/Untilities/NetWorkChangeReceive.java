package com.example.listtodo_ps25812.Untilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.listtodo_ps25812.Listener.NetWorkStateChangeListener;

public class NetWorkChangeReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetWorkStateChangeListener netWorkStateChangeListener = (NetWorkStateChangeListener)  context;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (isConnected) {
            netWorkStateChangeListener.handleNetWorkConnected();
        } else {
            netWorkStateChangeListener.handleNetWorkDisconnected();
        }
    }
}
