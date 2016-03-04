package com.example.myname.ispphoneandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;


public class MainActivity extends Activity {

    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;
    public String sipAddress = "sip:6019@10.152.128.145";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSIPManager();
        createSIPProfile();

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);

        try {
            mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

                public void onRegistering(String localProfileUri) {
                    System.out.println("Registering with SIP Server...");
                }
                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    System.out.println("SIP Registration Ready");
                }
                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    System.out.println("Registration failed.  Please check settings.");
                }
            });

            mSipManager.open(mSipProfile, pendingIntent, null);

        } catch (SipException e) {
            e.printStackTrace();
        }

        Button button= (Button) findViewById(R.id.buttonCall);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
    }

    public void makeCall() {
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    // Do something.
                }
            };

            SipAudioCall  call = mSipManager.makeAudioCall(mSipProfile.getUriString(), sipAddress, listener, 30);
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    public void createSIPManager() {
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(this);
        }

        SipProfile.Builder builder = null;

    }

    public void createSIPProfile() {

        String username = "iulian";
        String domain = "10.152.128.145";
        String password = "unsecurepassword";

        try {

            SipProfile.Builder builder = new SipProfile.Builder(username, domain);

            builder.setPassword(password);
            mSipProfile = builder.build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
