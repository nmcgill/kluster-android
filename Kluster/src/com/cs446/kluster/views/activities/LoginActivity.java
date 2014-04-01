package com.cs446.kluster.views.activities;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs446.kluster.R;
import com.cs446.kluster.models.AuthUser;
import com.cs446.kluster.net.KlusterRestAdapter;
import com.cs446.kluster.net.KlusterService;
import com.cs446.kluster.views.fragments.SignupFragment;

//TODO: Store the token in shared preferences. Populate UI only if no token found

public class LoginActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout); 
         
        Button loginButton=(Button)findViewById(R.id.loginKlusterAccountButton);
        Button signupButton=(Button)findViewById(R.id.signupKlusterAccountButton);
        final EditText email=(EditText)findViewById(R.id.loginUserNameInput);
        final EditText password=(EditText)findViewById(R.id.loginPasswordInput);
        
        SharedPreferences pref = getSharedPreferences("User", Context.MODE_PRIVATE);
        
        if (pref.contains("token")) {
		    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		    startActivity(intent);
        }
        
        
        loginButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String credentials = email.getText().toString() + ":" + password.getText().toString();
				String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
				RestAdapter adapter = new KlusterRestAdapter()
				.build();
				KlusterService service = adapter.create(KlusterService.class);
				
				service.getAuth("Basic " + base64EncodedCredentials, new Callback<AuthUser>() {
					@Override
					public void success(AuthUser user, Response response) {
						SharedPreferences pref = getSharedPreferences("User", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = pref.edit();
						
						editor.putString("id", user.getUserID());
						editor.putString("name", user.getFirstName() + " " + user.getLastName());
						editor.putString("token", user.getToken());
						editor.putString("tokenExpiry", user.getTokenExpiry());
						editor.commit();
												
						Toast.makeText(getApplicationContext(),
								"Hello " + user.getFirstName(), Toast.LENGTH_LONG)
								.show();
					}
					
					@Override
					public void failure(RetrofitError error) {
						Toast.makeText(getApplicationContext(),
								"Could not login...", Toast.LENGTH_LONG)
								.show();
					}
				});
				
			    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			    startActivity(intent);
			}
		});
        
        final SignupFragment signupFragment = new SignupFragment();
        
        signupButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().add(R.id.signup_page, signupFragment).addToBackStack(signupFragment.toString()).commit();
			}
		});
    }
		
	@Override
	public void onBackPressed() {
		FragmentManager fm = getFragmentManager();
		if(fm.getBackStackEntryCount() > 0) {
				fm.popBackStack();
		}

	}
}