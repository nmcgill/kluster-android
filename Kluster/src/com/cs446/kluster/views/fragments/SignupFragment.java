package com.cs446.kluster.views.fragments;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs446.kluster.R;
import com.cs446.kluster.models.User;
import com.cs446.kluster.net.KlusterRestAdapter;
import com.cs446.kluster.net.KlusterService;

public class SignupFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.signup_fragment, container, false);
		Button doneButton = (Button) view.findViewById(R.id.signupDoneButton);
		final EditText firstName = (EditText) view
				.findViewById(R.id.signupFirstName);
		final EditText lastName = (EditText) view
				.findViewById(R.id.signupLastName);
		final EditText userName = (EditText) view
				.findViewById(R.id.signupUserName);
		final EditText userEmail = (EditText) view
				.findViewById(R.id.signupEmail);
		final EditText password = (EditText) view
				.findViewById(R.id.signupPassword);
		final EditText confirmPassword = (EditText) view
				.findViewById(R.id.signupConfirmPassword);

		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!(password.getText().toString().equals(confirmPassword
						.getText().toString()))) {
					Toast.makeText(getActivity().getBaseContext(),
							"Passwords don't match...", Toast.LENGTH_LONG)
							.show();
					return;
				}

				if (firstName.getText().toString().equals("")
						|| lastName.getText().toString().equals("")
						|| userName.getText().toString().equals("")
						|| userEmail.getText().toString().equals("")
						|| password.getText().toString().equals("")
						|| confirmPassword.getText().toString().equals("")) {
					Toast.makeText(getActivity().getBaseContext(),
							"Complete all fields...", Toast.LENGTH_LONG)
							.show();
					return;
				}

				User user = new User(userName.getText().toString(),
									userEmail.getText().toString(),
									firstName.getText().toString(),
									lastName.getText().toString());
				
				user.setPassword(password.getText().toString());
				
				RestAdapter restAdapter = new KlusterRestAdapter()
				.build();
				
				KlusterService service = restAdapter.create(KlusterService.class);
				service.createUser(user, new Callback<User>() {
					@Override
					public void success(User user, Response response) {
						Toast.makeText(getActivity().getBaseContext(),
								"Registration Complete...", Toast.LENGTH_LONG)
								.show();
						getActivity().onBackPressed();
					}		
					@Override
					public void failure(RetrofitError error) {
						Toast.makeText(getActivity().getBaseContext(),
								"Could not create user...", Toast.LENGTH_LONG)
								.show();
					}
				});
			}
		});

		return view;
	}
}
