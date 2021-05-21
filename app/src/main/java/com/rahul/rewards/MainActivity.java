package com.rahul.rewards;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//library for Button, View and Toast
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// important library for Google adMob
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    //creating Object of RewardedAdLoadCallback
    RewardedAdLoadCallback rewardedAdLoadCallback;
    //creating Object of RewardedAdCallback
    RewardedAdCallback rewardedAdCallback;
    //creating Object of RewardedAd
    private RewardedAd rewardedAd;
    private AdView mAdView;
    private AdView mAdView2;
    //creating Object of Buttons
    private Button loadAdBtn;
    private Button showAdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the Google Admob SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {@Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed
            Toast.makeText(MainActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();

        }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest2);

        //Initializing the RewardedAd  objects
        rewardedAd = new RewardedAd(this, "ca-app-pub-9591247605621401/6464820823");

        // Initializing the Button  objects to their respective views from activity_main.xml file
        loadAdBtn = (Button) findViewById(R.id.loadRewardedBtn);
        showAdBtn = (Button) findViewById(R.id.showRewardedBtn);

        //OnClickListener listeners for loadAdBtn and showAdBtn buttons
        loadAdBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            //calling the loadRewardedAd method to load  the Rewarded Ad
            loadRewardedAd();
        }
        });

        showAdBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            //calling the showRewardedAd method to show the Rewarded Ad
            showRewardedAd();
        }
        });

        // creating  RewardedAdLoadCallback for Rewarded Ad with some 2 Override methods
        rewardedAdLoadCallback = new RewardedAdLoadCallback() {@Override
        public void onRewardedAdLoaded() {
            // Showing a simple Toast message to user when Rewarded Ad Failed to Load
            Toast.makeText(MainActivity.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
        }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Showing a simple Toast message to user when Rewarded Ad Failed to Load
                Toast.makeText(MainActivity.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void loadRewardedAd() {
        // Creating  an Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Rewarded Ad with the Request
        rewardedAd.loadAd(adRequest, rewardedAdLoadCallback);

        // Showing a simple Toast message to user when Rewarded an ad is Loading
        Toast.makeText(MainActivity.this, "Rewarded Ad is loading ", Toast.LENGTH_LONG).show();
    }

    private void showRewardedAd() {
        if (rewardedAd.isLoaded()) {

            //creating the Rewarded Ad Callback and showing the user appropriate message
            rewardedAdCallback = new RewardedAdCallback() {@Override
            public void onRewardedAdOpened() {
                // Showing a simple Toast message to user when Rewarded Ad is opened
                Toast.makeText(MainActivity.this, "Rewarded Ad is Opened", Toast.LENGTH_LONG).show();
            }

                @Override
                public void onRewardedAdClosed() {
                    // Showing a simple Toast message to user when Rewarded Ad is closed
                    Toast.makeText(MainActivity.this, "Rewarded Ad Closed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onUserEarnedReward(RewardItem reward) {
                    // Showing a simple Toast message to user when user earned the reward by completely watching the Rewarded Ad
                    Toast.makeText(MainActivity.this, "You won the reward :" + reward.getAmount(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Showing a simple Toast message to user when Rewarded Ad Failed to Show
                    Toast.makeText(MainActivity.this, "Rewarded Ad failed to show due to error:" + adError.toString(), Toast.LENGTH_LONG).show();
                }
            };

            //showing the ad Rewarded Ad if it is loaded
            rewardedAd.show(MainActivity.this, rewardedAdCallback);

            // Showing a simple Toast message to user when an Rewarded ad is shown to the user
            Toast.makeText(MainActivity.this, "Rewarded Ad  is loaded and Now showing ad  ", Toast.LENGTH_LONG).show();
        }
        else {
            //Load the Rewarded ad if it is not loaded
            loadRewardedAd();

            // Showing a simple Toast message to user when Rewarded ad is not loaded
            Toast.makeText(MainActivity.this, "Rewarded Ad is not Loaded ", Toast.LENGTH_LONG).show();

        }
    }
}