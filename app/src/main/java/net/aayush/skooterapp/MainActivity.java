package net.aayush.skooterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.localytics.android.LocalyticsActivityLifecycleCallbacks;
import com.localytics.android.LocalyticsAmpSession;

import net.aayush.skooterapp.common.view.SlidingTabLayout;

public class MainActivity extends BaseActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private LocalyticsAmpSession localyticsSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if the user is opening the app the first time
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int introScreen = settings.getInt("intro_screen", 0);

        if (introScreen == 0) {
            Intent intent = new Intent(this, IntroductoryActivity.class);
            startActivity(intent);
        }

        this.localyticsSession = new LocalyticsAmpSession(
                this.getApplicationContext());
        getApplication().registerActivityLifecycleCallbacks(
                new LocalyticsActivityLifecycleCallbacks(this.localyticsSession));

        activateToolbar();
        if (savedInstanceState == null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingFragment fragment = new SlidingFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alerts) {
            Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.score) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
