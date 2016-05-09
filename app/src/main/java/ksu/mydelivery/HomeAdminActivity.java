package ksu.mydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  Admin admin;

    private ArrayList<String> jsonList;
    private ArrayList<Request> requestList;
    private Request request;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        admin = (Admin) getIntent().getSerializableExtra("Admin");

        requestList = new ArrayList<>();
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");


        FloatingActionButton fabSuspendUser = (FloatingActionButton) findViewById(R.id.fabSuspendUser);
        fabSuspendUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(HomeAdminActivity.this, SuspendUserActivity.class);
                intent.putExtra("Admin",admin);
                startActivity(intent);
            }
        });


        FloatingActionButton fabVerifyProvider = (FloatingActionButton) findViewById(R.id.fabVerifyProvider);
        fabVerifyProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(HomeAdminActivity.this, VerifyProviderActivity.class);
                intent.putExtra("Admin",admin);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);


        TextView name = (TextView) hView.findViewById(R.id.txtProfileName);
        TextView phone = (TextView) hView.findViewById(R.id.txtProfilePhone);
        name.setText(admin.getfName() + " " + admin.getlName());
        phone.setText("0" + admin.getPhoneNumber());


        final ListView lvRequests = (ListView) findViewById(R.id.lvRequests);

        lvRequests.setAdapter(new RequestBaseAdapter(this, requestList));

        lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lvRequests.getItemAtPosition(position);
                Request request = (Request) o;

              /*  Intent intent = new Intent(HomeActivity.this, EditOfferActivity.class);  ////// تتعدل الى اسناد طلب *******
                intent.putExtra("Requests", requestList);
                intent.putExtra("Request", request);
                startActivity(intent);
                finish();

                */
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_admin, menu);
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

        if (id == R.id.nav_profile) {

            Intent intent = new Intent(HomeAdminActivity.this, ProfileAdminActivity.class);
            intent.putExtra("Admin", admin);
            intent.putExtra("Requests" , requestList);
            startActivity(intent);

        } else if (id == R.id.nav_logOut) {
            startActivity(new Intent(HomeAdminActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(HomeAdminActivity.this, ShareActivity.class));
        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(HomeAdminActivity.this, ContactActivity.class));
        } else if (id == R.id.nav_tips) {
            startActivity(new Intent(HomeAdminActivity.this, TipsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeAdminActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
