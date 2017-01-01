package wassilni.pl.navigationdrawersi.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Objects.MyApp;
import Objects.Passenger;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import timber.log.Timber;
import wassilni.pl.navigationdrawersi.R;
import wassilni.pl.navigationdrawersi.data.Fragments;
import wassilni.pl.navigationdrawersi.data.model.NavigationDrawerItem;
import wassilni.pl.navigationdrawersi.ui.navigationdrawer.NavigationDrawerView;




public class MainActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private int currentSelectedPosition = 0;

    @InjectView(R.id.navigationDrawerListViewWrapper)
    NavigationDrawerView mNavigationDrawerListViewWrapper;

    @InjectView(R.id.linearDrawer)
    LinearLayout mLinearDrawerLayout;

    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.leftDrawerListView)
    ListView leftDrawerListView;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private List<NavigationDrawerItem> navigationItems;
    TextView userName,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        sharedPreferences = getSharedPreferences("session", Context.MODE_APPEND);
        mTitle = mDrawerTitle = getTitle();

SessionSetup();


        Timber.tag("LifeCycles");
        Timber.d("Activity Created");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame,
                    Fragment.instantiate(MainActivity.this, Fragments.ONE.getFragment())).commit();
        } else {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_one), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_two), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_three), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_four), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_fifth), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_about),
                R.drawable.ic_action_about, false));

        mNavigationDrawerListViewWrapper.replaceWith(navigationItems);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                 R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_ab_transparent);

      // getSupportActionBar().setIcon(R.drawable.ic_action_ab_transparent);
        userName=(TextView) findViewById(R.id.drawerUserName);
        email=(TextView) findViewById(R.id.drawerUserEmail);

        selectItem(currentSelectedPosition);

    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if((!MyApp.passenger_from_session.getFName().equals(null))) {
            userName.setText(MyApp.passenger_from_session.getFName() + " " + MyApp.passenger_from_session.getLName());
            email.setText(MyApp.passenger_from_session.getEmail());
        }else{
            userName.setText("");
            email.setText("");
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.menuRight) {
            if( mDrawerLayout.isDrawerOpen(Gravity.RIGHT))
            {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.leftDrawerListView)
    public void OnItemClick(int position, long id) {

        if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout)) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
            onNavigationDrawerItemSelected(position);

            selectItem(position);
        }
    }

    private void selectItem(int position) {

        if (leftDrawerListView != null) {
            leftDrawerListView.setItemChecked(position, true);

            navigationItems.get(currentSelectedPosition).setSelected(false);
            navigationItems.get(position).setSelected(true);

            currentSelectedPosition = position;

            getSupportActionBar()
                    .setTitle(navigationItems.get(currentSelectedPosition).getItemName());
        }

        if (mLinearDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
        }
    }

    private void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                if (!(getSupportFragmentManager().getFragments()
                        .get(0) instanceof FragmentOne)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.ONE.getFragment()))
                            .commit();
                }
                break;
            case 1:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentTwo)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.TWO.getFragment()))
                            .commit();
                }
                break;
            case 2:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentThree)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.THREE.getFragment()))
                            .commit();
                }
                break;
            case 3:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentFour)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.FOUR.getFragment()))
                            .commit();
                }
                break;
            case 4:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentFifth)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.FiFth.getFragment()))
                            .commit();
                }
                break;
           case 5:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentAbout)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.ABOUT.getFragment()))
                            .commit();
                    /* This code is used to logout AND to delete session file. */
                    new AlertDialog.Builder(this)
                            .setTitle("تأكيد تسجيل الخروج")
                            .setMessage("هل أنت متأكد من تسجيل الخروج؟")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(getApplicationContext(),"تم تسجيل الخروج بنجاح",Toast.LENGTH_SHORT).show();
                                    SharedPreferences sp= getSharedPreferences("session", Context.MODE_APPEND);
                                    SharedPreferences.Editor editor= sp.edit();
                                    editor=editor.clear();
                                    editor.clear();
                                    editor.commit();
                                    MyApp.passenger_from_session=null;
                                    startActivity(new Intent(getApplicationContext(),login.class));
                                    finish();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
                break;
        }

    }

        public void SessionSetup() {

                String flag = "";
                if (sharedPreferences.contains("ID"))//means that the XML file isn't empty.
                {//retrieve session , and set the variable MyApp.passenger_from_session
                    Passenger.retrieveSession(sharedPreferences);
                //System.out.println("فييييييه سييييييييييششششششششششننننننننن موجوده");
                    flag = "succeed";
                } else // there's no session saved, then create one!
                {
                    flag = "failed";
                    // 1.prompt for login, using Toast
                }
                System.out.println("$#$#$#$#$#$#$#$#$#$#$#$#$$#$#$#$#$#$#$ "+flag);



                if (flag.equalsIgnoreCase("succeed")) {
                    Toast.makeText(getApplicationContext(), "مرحبا مجدداً!", Toast.LENGTH_LONG).show();
                    System.out.println("@@@@@@@@@@@\t "+ MyApp.passenger_from_session.getEmail());
                    //direct user to next page.
                } else {
                    //prompt to logiin
                    Toast.makeText(getApplicationContext(), "لا يوجد ملف تعريف, الرجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),login.class));
                    finish();
                }

            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(getIntent());
    }
}


