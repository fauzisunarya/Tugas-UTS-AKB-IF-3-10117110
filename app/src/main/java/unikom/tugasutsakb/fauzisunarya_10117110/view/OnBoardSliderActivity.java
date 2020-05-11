package unikom.tugasutsakb.fauzisunarya_10117110.view;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

import unikom.tugasutsakb.fauzisunarya_10117110.R;


/** NIM : 10117110
 * Nama : Fauzi Sunarya
 * Kelas : IF-3
 * Tanggal : 11-05-2020**/

public class OnBoardSliderActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addFragment(new Step.Builder().setTitle("Add Friend Data")
                .setContent("add friend's data list")
                .setBackgroundColor(Color.parseColor("#03AC0E")) // int background color
                .setDrawable(R.drawable.addfriend) // int top drawable
                .setSummary("Add friend's list feature")
                .build());
        addFragment(new Step.Builder().setTitle("Change Friend Data")
                .setContent("change friend's data list")
                .setBackgroundColor(Color.parseColor("#03AC0E")) // int background color
                .setDrawable(R.drawable.updatefriend) // int top drawable
                .setSummary("change friend's list feature")
                .build());
        addFragment(new Step.Builder().setTitle("Display A Friend List")
                .setContent("displays a list of friend's that have been added")
                .setBackgroundColor(Color.parseColor("#03AC0E")) // int background color
                .setDrawable(R.drawable.listfriend) // int top drawable
                .setSummary("display a friend's list feature")
                .build());
        setCancelText("Skip");

    }

    @Override
    public void finishTutorial() {
        super.finishTutorial();
        Intent intent = new Intent(OnBoardSliderActivity.this, SplashScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
