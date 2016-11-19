package project.ks.com.posts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import project.ks.com.posts.user.LoginActivity;
import project.ks.com.posts.user.Registration;
import project.ks.com.posts.util.AppConstant;


public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Notification","Add","Profile"};
    int Numboftabs =4;

    SharedPreferences settings ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myTag", "going to set layout");
        setContentView(R.layout.activity_main);
        this.invalidateOptionsMenu();

        settings = getSharedPreferences(AppConstant.LOGIN_PREFERENCES,MODE_PRIVATE);
        //getSharedPreferences(AppConstant.LOGIN_PREFERENCES,MODE_APPEND);

        Log.i("IS_LOGIN_SKIPED ",""+settings.getBoolean(AppConstant.IS_LOGIN_SKIPED,false));
        Log.i("IS_LOGIN ",""+settings.getBoolean(AppConstant.IS_LOGIN,false));

        if(!(settings.getBoolean(AppConstant.IS_LOGIN_SKIPED,false) || settings.getBoolean(AppConstant.IS_LOGIN,false))){
            Log.i("Loign Activity ","start login Activity");
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            Log.i("Loign Activity ","not start login");
        }

        // Creating The Toolbar and setting it as the Toolbar for the activity
        Log.i("myTag", "going to set toolbar");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        AppConstant.PACKAGE_NAME = getApplicationContext().getPackageName();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs,getApplicationContext());

        // Assigning ViewPager View and setting the adapter
        Log.i("myTag", "going to set pager adapter");
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        Log.i("myTag", "going to set view pager");

        tabs.setCustomTabView(R.layout.custom_tab, 0);
        tabs.setViewPager(pager);

        //QuestionUtils.getQuestionList();
        //startActivity(new Intent(this, LoginActivity.class));
   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem loginMenu = menu.findItem(R.id.action_login);
        MenuItem logoutMenu = menu.findItem(R.id.action_logout);

        /*if(settings.getBoolean(AppConstant.IS_LOGIN,false)){
            loginMenu.setVisible(Boolean.FALSE);
            logoutMenu.setVisible(Boolean.TRUE);
        }else {
            loginMenu.setVisible(Boolean.TRUE);
            logoutMenu.setVisible(Boolean.FALSE);
        }*/

        loginMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            }
        });

        logoutMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences.Editor  editor = settings.edit();
                editor.putBoolean(AppConstant.IS_LOGIN,Boolean.FALSE);
                editor.putString(AppConstant.USER_JSON,null);
                editor.putBoolean(AppConstant.IS_LOGIN_SKIPED,Boolean.FALSE);
                editor.commit();

                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            }
        });

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


}