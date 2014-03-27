package com.cs446.kluster;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.cs446.kluster.view.fragments.PhotoGridFragment;

public class PhotoGridActivity extends Activity {    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); 

        Fragment firstFragment = new PhotoGridFragment();
        
        firstFragment.setArguments(getIntent().getBundleExtra("events"));
        
        getFragmentManager().beginTransaction().add(R.id.main_container, firstFragment).commit();
    }

}
