package challenges.sutrix.androidappnetclient.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.adapter.SlideMenuAdapter;
import challenges.sutrix.androidappnetclient.fragment.CountryMusicFragment;
import challenges.sutrix.androidappnetclient.fragment.GroupFragment;
import challenges.sutrix.androidappnetclient.fragment.HomeFragment;
import challenges.sutrix.androidappnetclient.fragment.MainFragment;
import challenges.sutrix.androidappnetclient.fragment.OtherFragment;
import challenges.sutrix.androidappnetclient.fragment.PopFragment;
import challenges.sutrix.androidappnetclient.fragment.RockAndRollFragment;
import challenges.sutrix.androidappnetclient.listener.RecyclerItemClickListener;
import challenges.sutrix.androidappnetclient.utils.KeyboardUtils;

public class BaseActivity extends ActionBarActivity implements TextToSpeech.OnInitListener{
    private Toast mToast;

    private TextToSpeech mNotificationVoice;

    //Navigation drawer
    private String titles[];
    private int ICONS[] = {R.drawable.ic_vocabulary, R.drawable.ic_basic_grammar, R.drawable.ic_advantage_grammar, R.drawable.ic_test, R.drawable.ic_setting, R.drawable.ic_basic_grammar, R.drawable.ic_basic_grammar};

    private RecyclerView mRecyclerView; // Declaring RecyclerView
    private SlideMenuAdapter mAdapter;  // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;  // Declaring Layout Manager as a linear layout manager
    private DrawerLayout Drawer;    // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = new Toast(this);
        mNotificationVoice = new TextToSpeech(this, this);
        titles = getResources().getStringArray(R.array.menu_array_string);
    }

    /**
     * Init navigation drawer
     */
    protected void initNavigationDrawer() {

        Toolbar mToolBar = (Toolbar) findViewById(R.id.card_tool_bar);
        setSupportActionBar(mToolBar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new SlideMenuAdapter(this, titles, ICONS);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position > 0) {
                            Drawer.closeDrawers();
                            mAdapter.setSelected(position);
                            Fragment tCurrentFragment = getCurrentFragment();
                            Fragment tFragment = null;
                            switch (position) {
                                case 1:
                                    if(!(tCurrentFragment instanceof HomeFragment)) {
                                        tFragment = new HomeFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                case 2:
                                    if(!(tCurrentFragment instanceof PopFragment)) {
                                        tFragment = new PopFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                case 3:
                                    if(!(tCurrentFragment instanceof RockAndRollFragment)) {
                                        tFragment = new RockAndRollFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                case 4:
                                    if(!(tCurrentFragment instanceof CountryMusicFragment)) {
                                        tFragment = new CountryMusicFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                case 5:
                                    if(!(tCurrentFragment instanceof OtherFragment)) {
                                        tFragment = new OtherFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                case 6:
                                    if(!(tCurrentFragment instanceof GroupFragment)) {
                                        tFragment = new GroupFragment();
                                        replaceFragment(tFragment, true);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                })
        );

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.draw_layout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, mToolBar, R.string.hello_world, R.string.action_settings) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();
    }

    /**
     * Show toast with a string
     *
     * @param message String message to show
     */
    protected void showToast(String message) {
        mToast.cancel();
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Show toast with a StringID
     *
     * @param stringID String id to show
     */
    protected void showToast(int stringID) {
        mToast.cancel();
        mToast = Toast.makeText(this, stringID, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Cancel the showing toast
     */
    protected void cancelToast() {
        mToast.cancel();
    }

    /**
     * Replace fragment
     *
     * @param sFragment    fragment
     * @param isAddToStack a flag to check if the replaced one is add to the back stack.
     */
    public void replaceFragment(Fragment sFragment, boolean isAddToStack) {
        // Hide keyboard if it is showed
        KeyboardUtils.hideKeyboard(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isAddToStack) {
            transaction.addToBackStack(null);
        }

        //Replace current fragment by sFragment with custom animation.
        transaction
                .setCustomAnimations(R.anim.move_right_in,
                        R.anim.move_left_out, R.anim.move_right_out,
                        R.anim.move_left_in)
                .replace(R.id.main_fragment_container, sFragment).commit();
    }

    /**
     * Add first fragment
     *
     * @param sFragment fragment to adding to the layout
     */
    public void addFragment(Fragment sFragment) {
        // Hide keyboard if it is showed
        KeyboardUtils.hideKeyboard(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!(sFragment instanceof MainFragment)){
            transaction.addToBackStack(null);
        }
        transaction
                .add(R.id.main_fragment_container, sFragment).commit();
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
        if (id == R.id.action_settings) {
            showToast("Setting is click");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            //Finish activity if the number of fragments in the back-stack is 0
            finish();
        } else {
            KeyboardUtils.hideKeyboard(this);
            FragmentManager fm = this.getSupportFragmentManager();
            fm.popBackStackImmediate();

            //Get current fragment
            Fragment tFragment = getCurrentFragment();
            mAdapter.setSelectedOnBackPress(tFragment);
        }
    }

    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
    }

    public void setSlideMenuSelected(int position){
        mAdapter.setSelected(position);
    }

    public void refreshSlideMenu(){
        mAdapter.refreshSlideMenu();
    }

    @Override
    public void onInit(int i) {
        if(mNotificationVoice !=null )
        {
            mNotificationVoice.setLanguage(Locale.ENGLISH);
            mNotificationVoice.stop();
        }else{
            showToast("Sorry, but text speed is not support in your device");
        }
    }

    @SuppressWarnings("deprecation")
    public void speak(String text) {
        if (mNotificationVoice != null) {
            mNotificationVoice.stop();
            mNotificationVoice.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
