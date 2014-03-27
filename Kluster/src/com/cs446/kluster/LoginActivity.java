package com.cs446.kluster;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import com.cs446.kluster.fragments.EventGridFragment;
import com.cs446.kluster.network.AuthRequest;
import com.cs446.kluster.tests.TestData;
import com.cs446.kluster.user.UserAuthInfo;
import com.cs446.kluster.user.Users;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//TODO: Store the token in shared preferences. Populate UI only if no token found

public class LoginActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity); 
         
        Button loginButton=(Button)findViewById(R.id.loginKlusterAccountButton);
        Button signupButton=(Button)findViewById(R.id.signupKlusterAccountButton);
        final EditText email=(EditText)findViewById(R.id.loginUserNameInput);
        final EditText password=(EditText)findViewById(R.id.loginPasswordInput);
        
        
        loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AuthRequest authRequest=new AuthRequest();
				// TODO Auto-generated method stub
				authRequest.execute(email.getText().toString(),password.getText().toString());
				UserAuthInfo userAuthInfo=null;
				try {
					userAuthInfo=authRequest.get();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(userAuthInfo==null){
					Toast.makeText(getApplicationContext(),
							"Could not login...", Toast.LENGTH_LONG)
							.show();
					return;
				}
				
				String name=userAuthInfo.getUserInfo().getmFirstName();
				Toast.makeText(getApplicationContext(),
						"Hello " + name, Toast.LENGTH_LONG)
						.show();
				
				
			    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			    startActivity(intent);
			}
		});
        
        final SignupFragment signupFragment=new SignupFragment();
        
        signupButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction().add(R.id.signup_page, signupFragment).addToBackStack(signupFragment.toString()).commit();

			}
		});
        
//        //** TODO: Move user creation to main? */
//        Users.CreateUser(this, new BigInteger("531238e5f330ede5deafbc4e", 16));
//        
//        //Add Testing Data
//        TestData.CreateTestData(getContentResolver());
    }
	
	   @Override
	   public void onBackPressed() {
			FragmentManager fm = getFragmentManager();

			if(fm.getBackStackEntryCount() > 0) {
				fm.popBackStack();
			}
			
	   }
}