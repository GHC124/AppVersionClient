package com.ghc.appversionclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ghc.appversionclient.common.net.Response.RestResponse;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManager;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManagerFactory;
import com.ghc.appversionclient.common.net.parser.RestResponseParser;
import com.ghc.appversionclient.prefs.UserPreferences;
import com.ghc.appversionclient.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private boolean mLoginTask = false;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		BaseApplication.getInstance().enableLog();
		AppVersionClientManager.setEnableLog(true);
		AppVersionClientManager.setBaseURL("http://10.88.106.123:8088/AppVersion/rest");
		AppVersionClientManager.clearToken();
		// Check token
		UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();
		String username = userPreferences.getUserLoginUserName();
		String loginToken = userPreferences.getUserLoginToken();
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(loginToken)) {
			AppVersionClientManager.setUserName(username);
			AppVersionClientManager.setToken(loginToken);
			// show main activity
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return;
		}

		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);


		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mLoginTask) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		final String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
                    mLoginTask = false;
					showProgress(false);

					RestResponse restResponse = null;
					try {
						restResponse = (RestResponse) new RestResponseParser().parse(response);
					} catch (JSONException e) {
						Logger.print(e);
					} catch (ParseException e) {
						Logger.print(e);
					}
					if (restResponse != null) {
						if (restResponse.getStatus().equals(RestResponse.SUCCESS)) {
							// store login token
							String token = restResponse.getMessage();
							UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();
							userPreferences.setUserLoginUserName(email);
							userPreferences.setUserLoginToken(token);
							// set data for Manager
							AppVersionClientManager.setUserName(email);
							AppVersionClientManager.setToken(token);
							// show main activity
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
							finish();
						} else {
							mPasswordView.setError(getString(R.string.error_incorrect_password));
							mPasswordView.requestFocus();
						}
					}
				}
			};
			Response.ErrorListener errorListener = new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
                    mLoginTask = false;
					showProgress(false);

					mPasswordView.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();

					Logger.print(error);
				}
			};
			AppVersionClientManagerFactory.newPostUserLogin(Volley.newRequestQueue(LoginActivity.this), listener,
					errorListener, email, password).execute();
			mLoginTask = true;
		}
	}

	private boolean isEmailValid(String email) {
		return email
				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	private boolean isPasswordValid(String password) {
		return password.length() > 0;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}
}
