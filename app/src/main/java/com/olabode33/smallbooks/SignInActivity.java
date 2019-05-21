package com.olabode33.smallbooks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 3300;
    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @BindView(R.id.sign_in_button) SignInButton mSignInButton;
    @BindView(R.id.sign_progress_bar) ProgressBar mProgressBar;
//    @BindView(R.id.user_name_textView) TextView mUserNameTextView;
//    @BindView(R.id.user_email_textView) TextView mUserEmailTextView;
//    @BindView(R.id.user_profile_imageView) ImageView mUserProfileImageView;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        //If User is already signed in goto MainActivity
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Set firebaseAuthOnclickListener
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        //Configure Google SignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
    }

    private void signIn() {
        mProgressBar.setVisibility(View.VISIBLE);
//        Intent intent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(intent, RC_SIGN_IN);

        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
                );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (Exception e) {
//                Log.d(TAG, getString(R.string.googleSignInFailed) + " Error Message: " + e.getMessage());
//            }
            mProgressBar.setVisibility(View.GONE);
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else {
                if (response != null) {
                    Log.d(TAG, "Error Signing in" + response.getError().getMessage());
                }
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, user.getDisplayName());
            Log.d(TAG, user.getEmail());
            mProgressBar.setVisibility(View.GONE);
//            mUserNameTextView.setText(user.getDisplayName());
//            mUserEmailTextView.setText(user.getEmail());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Log.d(TAG, "Signed in success");
                } else {
                    Toast.makeText(SignInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
