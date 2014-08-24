
package li.itcc.hackathon2014;

import li.itcc.hackathon2014.Selfie.SelfieLogic;
import li.itcc.hackathon2014.vaduztour.AboutFragment;
import li.itcc.hackathon2014.vaduztour.CompassFragment;
import li.itcc.hackathon2014.vaduztour.FinishFragment;
import li.itcc.hackathon2014.vaduztour.HotColdFragment;
import li.itcc.hackathon2014.vaduztour.IntroFragment;
import li.itcc.hackathon2014.vaduztour.QuestionFragment;
import li.itcc.hackathon2014.vaduztour.SculptureFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static final int REQUEST_CODE_TAKE_PICTURE = 100;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private SelfieLogic fSelfieLogic;

    private AbstractTourFragment fCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fSelfieLogic = new SelfieLogic(this);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        AbstractTourFragment fragment;
        if (position == 0) {
            fragment = IntroFragment.newInstance(position + 1, 0);
            fragment.setId("intro");
        }
        else {
            fragment = AboutFragment.newInstance(position + 1, 0);
            fragment.setId("about");
        }
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commit();
    }

    @Override
    public void onBackPressed() {
        if (fCurrentFragment != null) {
            if (fCurrentFragment.getTourNumber() == 1 && fCurrentFragment.getTourPage() > 0) {
                FragmentManager fragmentManager = getFragmentManager();
                AbstractTourFragment fragment = IntroFragment.newInstance(1, 0);
                fragment.setId("intro");
                FragmentTransaction trans = fragmentManager.beginTransaction();
                trans.replace(R.id.container, fragment);
                trans.commit();
            }
        }
    }

    public void onFragmentAttached(AbstractTourFragment fragment, int tourNumber, int tourPage) {
        fCurrentFragment = fragment;
        switch (tourNumber) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            fSelfieLogic.startTakePictureActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFragmentNextClicked(AbstractTourFragment abstractTourFragment, int tourNumber,
            int tourPage) {
        AbstractTourFragment nextFragment;
        int nextPage = tourPage + 1;
        if (tourPage == 0) {
            nextFragment = HotColdFragment.newInstance(tourNumber, nextPage);
            nextFragment.setId("hotcold");
        }
        else if (tourPage == 1) {
            nextFragment = CompassFragment.newInstance(tourNumber, nextPage);
            nextFragment.setId("compass");
        }
        else if (tourPage == 2) {
            nextFragment = QuestionFragment.newInstance(tourNumber, nextPage);
            nextFragment.setId("question");
        }
        else if (tourPage == 3) {
            nextFragment = SculptureFragment.newInstance(tourNumber, nextPage);
            nextFragment.setId("sculpture");
        }
        else if (tourPage == 4) {
            nextFragment = FinishFragment.newInstance(tourNumber, nextPage);
            nextFragment.setId("finish");
        }
        else {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.container, nextFragment);
        trans.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            fSelfieLogic.onPictureResult(requestCode, resultCode, data);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
