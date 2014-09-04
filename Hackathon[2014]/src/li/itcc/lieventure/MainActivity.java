
package li.itcc.lieventure;

import li.itcc.lieventure.vaduztour.AboutFragment;
import li.itcc.lieventure.vaduztour.CastleFragment;
import li.itcc.lieventure.vaduztour.DirectionFragment;
import li.itcc.lieventure.vaduztour.DirectionIntroFragment;
import li.itcc.lieventure.vaduztour.FinishFragment;
import li.itcc.lieventure.vaduztour.HotColdFragment;
import li.itcc.lieventure.vaduztour.HotColdIntroFragment;
import li.itcc.lieventure.vaduztour.IntroFragment;
import li.itcc.lieventure.vaduztour.QuestionFragment;
import li.itcc.lieventure.vaduztour.SculptureFragment;
import li.itcc.lieventure.vaduztour.SelfieFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

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

    private AbstractTourFragment fCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        AbstractTourFragment fragment;
        if (position == 0) {
            fragment = createFragmentForPage(1, 0);
        }
        else {
            fragment = AboutFragment.newInstance(position + 1, 0);
        }
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commit();
    }

    @Override
    public void onBackPressed() {
        if (fCurrentFragment != null) {
            int tourNumber = fCurrentFragment.getTourNumber();
            int tourPage =  fCurrentFragment.getTourPage();
            if (tourNumber == 1 && tourPage > 0) {
                FragmentManager fragmentManager = getFragmentManager();
                AbstractTourFragment fragment = createFragmentForPage(tourNumber, tourPage-1);
                FragmentTransaction trans = fragmentManager.beginTransaction();
                trans.replace(R.id.container, fragment);
                trans.commit();
            }
            else {
                super.onBackPressed();
            }
        }
        else {
            super.onBackPressed();
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
            return true;
        }
        else if (id == R.id.action_selfie_cam) {
            Fragment fragment = SelfieFragment.instanceOf();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFragmentNextClicked(AbstractTourFragment abstractTourFragment, int tourNumber,
            int tourPage) {
        int nextPage = tourPage + 1;
        AbstractTourFragment nextFragment = createFragmentForPage(tourNumber, nextPage);
        if (nextFragment == null) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.container, nextFragment);
        trans.commit();
    }

    private AbstractTourFragment createFragmentForPage(int tourNumber, int tourPage) {
        AbstractTourFragment result;
        int index = 0;
        if (tourPage == index++) {
            result = IntroFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = HotColdIntroFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = HotColdFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = DirectionIntroFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = DirectionFragment.newInstance(tourNumber, tourPage);
            //result = CompassFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = QuestionFragment.newInstance(tourNumber, tourPage,
                    QuestionFragment.QUESTION_SET_WOMAN);
        }
        else if (tourPage == index++) {
            result = CastleFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = QuestionFragment.newInstance(tourNumber, tourPage,
                    QuestionFragment.QUESTION_SET_CASTLE);
        }
        else if (tourPage == index++) {
            result = SculptureFragment.newInstance(tourNumber, tourPage);
        }
        else if (tourPage == index++) {
            result = FinishFragment.newInstance(tourNumber, tourPage);
        }
        else {
            result = null;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mNavigationDrawerFragment.myOnActivityResult(requestCode, resultCode, data);
    }

}
