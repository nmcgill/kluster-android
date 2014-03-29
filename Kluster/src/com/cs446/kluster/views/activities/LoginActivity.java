package com.cs446.kluster.views.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs446.kluster.R;
import com.cs446.kluster.data.serialize.AuthUserSerializer;
import com.cs446.kluster.net.http.AuthRequest;
import com.cs446.kluster.net.http.HttpRequestListener;
import com.cs446.kluster.net.http.Request;
import com.cs446.kluster.net.http.task.HttpRequestTask;
import com.cs446.kluster.net.http.task.HttpRequestTaskCompat;
import com.cs446.kluster.user.AuthUser;
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
        
        
        loginButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Request authRequest = AuthRequest.create(email.getText().toString(), password.getText().toString());
				HttpRequestTaskCompat<AuthUser> task = new HttpRequestTaskCompat<AuthUser>(new HttpRequestListener<AuthUser>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onComplete() {						
					}

					@Override
					public void onError(Exception e) {
						Toast.makeText(getApplicationContext(),
								"Could not login...", Toast.LENGTH_LONG)
								.show();
					}

					@Override
					public void onSuccess(AuthUser result) {
						Toast.makeText(getApplicationContext(),
								"Hello " + result.getFirstName(), Toast.LENGTH_LONG)
								.show();
					}
				}, new AuthUserSerializer());
				
				task.executeAsync(authRequest);	
				
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