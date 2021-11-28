package com.example.namaste;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardScreen extends AppCompatActivity {

    EditText code;
    Button share, join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);

        code = findViewById(R.id.code);
        share = findViewById(R.id.sharebtn);
        join = findViewById(R.id.joinbtn);


        URL serverUrl;

        try {
            serverUrl = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverUrl)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(code.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(getApplicationContext(),defaultOptions);
            }
        });
    }
}