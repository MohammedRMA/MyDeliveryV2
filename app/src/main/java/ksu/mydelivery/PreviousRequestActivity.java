package ksu.mydelivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PreviousRequestActivity extends AppCompatActivity {

    private static ArrayList<Request> currentRequest;
    private static ArrayList<Request> previousRequest;
    private static RequestBaseAdapter currentRequestAdapter;
    private static RequestBaseAdapter previousRequestAdapter;
    private static ListView lvRequests;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mSectionsPagerAdapter.getItem(1);
      /*  String[] title = {"Ball","Laptop","Paper"};
        String[] price = {"20","40","15"};

        arrayList = new ArrayList<>(Arrays.asList(title));
        adapter = new ArrayAdapter<>(this,R.layout.layout_my_request_list,R.id.lblTitle);
        arrayList = new ArrayList<>(Arrays.asList(price));
        adapter = new ArrayAdapter<>(this, R.layout.layout_my_request_list , R.id.lblPrice , arrayList);


        String[] items2 = {"Orange","mAnGo","MohammedRMA"};
        arrayList2 = new ArrayList<>(Arrays.asList(items2));
        adapter2 = new ArrayAdapter<>(this, R.layout.layout_my_request_list , R.id.txtItem , arrayList2);*/

        previousRequest = (ArrayList<Request>) getIntent().getSerializableExtra("PreviousRequests");
        currentRequest  = (ArrayList<Request>) getIntent().getSerializableExtra("CurrentRequests");



        lvRequests = (ListView) findViewById(R.id.lvMyRequest);
        previousRequestAdapter = new RequestBaseAdapter(this, previousRequest);



        lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lvRequests.getItemAtPosition(position);
                Request request = (Request) o;
                Toast.makeText(PreviousRequestActivity.this, "You have chosen: " + " " + request.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_previous_request, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("ValidFragment")
    public class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public  PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_previous_request, container, false);
          //  TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {


            }
            else {
                Intent intent = new Intent(PreviousRequestActivity.this , MyRequestActivity.class);
                intent.putExtra("PreviousRequest" , previousRequest);
                intent.putExtra("CurrentRequest" , currentRequest);
                startActivity(intent);
                finish();
            }


            return rootView;
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public  Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment p = new PlaceholderFragment();
            p.newInstance(position + 1);
            return p;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Current Requests";
                case 1:
                    return "Previous Requests";
            }
            return null;
        }
    }
}
