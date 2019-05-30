package com.olabode33.smallbooks.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olabode33.smallbooks.Common.CircleTransformation;
import com.olabode33.smallbooks.Fragments.AllEntriesFragment;
import com.olabode33.smallbooks.Fragments.CreateEditTransactionFragment;
import com.olabode33.smallbooks.Interfaces.OnFragmentInteractionListener;
import com.olabode33.smallbooks.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    private static final String TAG = "MainActivityClass";
    private static Boolean persistenceEnabled = false;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    private TextView mUserNameTextView;
    private TextView mUserEmailTextView;
    private ImageView mUserProfileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        View navigationViewHeader = mNavigationView.getHeaderView(0);
        mUserEmailTextView = navigationViewHeader.findViewById(R.id.user_email_textView);
        mUserNameTextView = navigationViewHeader.findViewById(R.id.user_name_textView);
        mUserProfileImageView = navigationViewHeader.findViewById(R.id.user_profile_imageView);

        if(!persistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            persistenceEnabled = true;
        }

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    mUserNameTextView.setText(user.getDisplayName());
                    mUserEmailTextView.setText(user.getEmail());
                    Picasso.get()
                            .load(user.getPhotoUrl())
                            .resize(100, 100)
                            .transform(new CircleTransformation())
                            .centerCrop()
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher)
                            .into(mUserProfileImageView);
                }
            }
        };

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = CreateEditTransactionFragment.newInstance("", "");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = AllEntriesFragment.newInstance();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = AllEntriesFragment.newInstance();
        } else if (id == R.id.nav_gallery) {
            fragment = CreateEditTransactionFragment.newInstance("", "");
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            mAuth.signOut();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast toast = Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_SHORT);
        toast.show();
    }
}
