package com.cs446.kluster.views.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.cs446.kluster.R;
import com.cs446.kluster.R.id;
import com.cs446.kluster.R.layout;
import com.cs446.kluster.views.fragments.PhotoGridFragment;

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
