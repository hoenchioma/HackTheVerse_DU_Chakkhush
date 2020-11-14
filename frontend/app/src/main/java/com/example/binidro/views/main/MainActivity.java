package com.example.binidro.views.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.binidro.R;
import com.example.binidro.models.Ward;
import com.example.binidro.views.auth.fragments.ForgotPasswordFragment;
import com.example.binidro.views.auth.fragments.SignInFragment;
import com.example.binidro.views.auth.fragments.SignUpFragment;
import com.example.binidro.views.main.fragments.PatientsFragment;
import com.example.binidro.views.main.fragments.WardsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private Boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView fragmentTitle;
    private Button menuButton;
    private NavigationView navigationView;
    private TextView profileNameTextView;
    private TextView profileEmailTextView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findXmlElements();
        setUpListeners();
        initializeUI();
        initializeFcm();
        initializeAndroidNetworkingLibrary();
    }

    public void findXmlElements(){
        // Parent Layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutMain);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        fragmentTitle = (TextView) findViewById(R.id.fragmentTitleToolbarMain);
        menuButton = (Button) findViewById(R.id.menuButtonToolbarMain);

        // Navigation Drawer
        navigationView = (NavigationView) findViewById(R.id.navigationViewMain);
        profileNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameTextViewDrawerHeader);
        profileEmailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailTextViewDrawerHeader);
    }

    public void setUpListeners(){
        drawerLayout.setDrawerListener(drawerToggle);
        menuButton.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initializeUI(){
        updateNavigationView();
        navigationView.getMenu().findItem(R.id.wardsDrawerMenu).setChecked(true);

        // TODO - Set User Details
//        profileNameTextView.setText(CONSTANTS.getCurrentUser().getProperty("name").toString());
//        profileEmailTextView.setText(CONSTANTS.getCurrentUser().getEmail());

        openWards();
    }

    public void initializeFcm(){
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });
    }

    public void initializeAndroidNetworkingLibrary() {
        // Adding an Network Interceptor for Debugging purpose :
        AndroidNetworking.initialize(getApplicationContext());
    }

    public void openWards(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentContainerMain, new WardsFragment(), "wards");
        fragmentTransaction.commit();
    }


    private void updateNavigationView(){
        navigationView.getMenu().findItem(R.id.wardsDrawerMenu).setChecked(false);
        navigationView.getMenu().findItem(R.id.aboutUsDrawerMenu).setChecked(false);
        navigationView.getMenu().findItem(R.id.signOutDrawerMenu).setChecked(false);
    }

    private void signOut() {
        // TODO - Implement Sign Out Method
    }

    @Override
    public void onClick(View view) {
        if(view == menuButton){
            new CountDownTimer(100, 20){
                int i;
                @Override
                public void onTick(long l) {
                    if(i%2==0) {
                        menuButton.setVisibility(View.INVISIBLE);
                    }
                    else{
                        menuButton.setVisibility(View.VISIBLE);
                    }
                    i++;
                }

                @Override
                public void onFinish() {
                    menuButton.setVisibility(View.VISIBLE);
                    if(drawerLayout.isDrawerOpen(navigationView)){
                        drawerLayout.closeDrawer(navigationView);
                    }

                    else if(!drawerLayout.isDrawerOpen(navigationView)){
                        drawerLayout.openDrawer(navigationView);
                    }
                }
            }.start();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.wardsDrawerMenu) {
            updateNavigationView();
            navigationView.getMenu().findItem(R.id.wardsDrawerMenu).setChecked(true);
            openWards();
        } else if (id == R.id.aboutUsDrawerMenu) {
            updateNavigationView();
            navigationView.getMenu().findItem(R.id.aboutUsDrawerMenu).setChecked(true);
//            Intent intent = new Intent(getApplicationContext(), RequestBookActivity.class);
//            startActivity(intent);
        } else if (id == R.id.signOutDrawerMenu) {
            updateNavigationView();
            navigationView.getMenu().findItem(R.id.signOutDrawerMenu).setChecked(true);
            signOut();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if(!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                showToast("Press Once Again to EXIT");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }
            else {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        } else {
            String fragmentName = getSupportFragmentManager().getBackStackEntryAt(count-1).getName();
            if(fragmentName.equals("wards")) {
                fragmentTitle.setText("Wards");
                updateNavigationView();
                navigationView.getMenu().findItem(R.id.wardsDrawerMenu).setChecked(true);
            } else if(fragmentName.equals("patients")) {
                fragmentTitle.setText("Patients");
                updateNavigationView();
            } else if(fragmentName.equals("sensors")) {
                fragmentTitle.setText("Sensors");
                updateNavigationView();
            }
            getSupportFragmentManager().popBackStack();
        }
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}