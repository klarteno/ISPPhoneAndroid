package com.example.myname.ispphoneandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class WalkieTalkieActivity extends Activity implements View.OnTouchListener {

    public IncomingCallReceiver callReceiver;
    public SipManager mSipManager;
    public SipAudioCall call;
    public SipProfile mSipProfile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
        try {
            mSipManager.open(mSipProfile, pendingIntent, null);
        } catch (SipException e) {
            e.printStackTrace();
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void updateStatus(SipAudioCall incomingCall) {

    }
}