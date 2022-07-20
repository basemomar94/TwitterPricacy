package com.example.twitterpricacy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PrivacyPlugin extends AppCompatActivity {

    TextView name;
    EditText tweet;
    String user;
    ImageView logoout;
    Button post;
    String barrierToken = "AAAAAAAAAAAAAAAAAAAAAGzWdAEAAAAA2js79sJhx526dFAOU3I1yIzBHto%3DKme8faENIEG5tf2qThc81ppkRel7jQQR2K1AQaJJmrNazFYJth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_plugin);
        getTweets();


        user = getIntent().getStringExtra("username");
        name = (TextView) findViewById(R.id.nametextView);
        logoout = findViewById(R.id.logout);
        tweet=findViewById(R.id.textView);
        post = findViewById(R.id.button);

        logoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String  Tweetm = tweet.getText().toString();

             //  shareTwitter(Tweetm);
                sendTweet();
            }
        });

        name.setText("Hello " + user);

    }

    //A second Activity allows the user to select one of at least 5 colours
    public void onPreferenceClick(View v) {
        Intent intent = new Intent(PrivacyPlugin.this, Preference.class);
        Bundle bundle = new Bundle();
        //bundle.putInt("colour", defaultColour);

        intent.putExtras(bundle);

        startActivityForResult(intent, 1);
    }

    //A second Activity allows the user to select one of at least 5 colours
    public void onSubmitClick(View v) {
    }


    public void logout() {
        Intent intent = new Intent(PrivacyPlugin.this, LoginActivity.class);
        startActivity(intent);

    }

    void getTweets() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.twitter.com/2/users/1418959211292241941/tweets")

                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAGzWdAEAAAAA2js79sJhx526dFAOU3I1yIzBHto%3DKme8faENIEG5tf2qThc81ppkRel7jQQR2K1AQaJJmrNazFYJth")
                .addHeader("Cookie", "guest_id=v1%3A165680679062409344; guest_id_ads=v1%3A165680679062409344; guest_id_marketing=v1%3A165680679062409344; personalization_id=\"v1_LTnYHXZHxlYgsud7YUSGIw==\"")
                .build();
        Runnable runnable = () -> {
            try {
                Response response = client.newCall(request).execute();
                System.out.println("==============="+response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {

            });
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("TAG", "UTF-8 should always be supported", e);
            return "";
        }
    }

    public void sendTweet() {
        String msg = tweet.getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setType("text/plain");

        intent.setPackage("com.twitter.android");
        startActivity(intent);
    }


}
