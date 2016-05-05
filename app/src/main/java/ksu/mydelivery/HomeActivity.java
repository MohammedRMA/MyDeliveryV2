package ksu.mydelivery;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RegularUser user;
    private ArrayList<String> jsonList;
    private ArrayList<Request> requestList;
    private Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu_camera);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        user = (RegularUser) getIntent().getSerializableExtra("RegularUser");


        FloatingActionButton addRequest = (FloatingActionButton) findViewById(R.id.fabAddRequest);
        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddRequestActivity.class);
                intent.putExtra("RegularUser", user);
                startActivity(intent);

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
        getMenuInflater().inflate(R.menu.home, menu);
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
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

        } else if (id == R.id.nav_myRequest) {


            HashMap getRequests = new HashMap();
            getRequests.put("txtUid", String.valueOf(user.getID()) );

            PostResponseAsyncTask getOffers = new PostResponseAsyncTask(HomeActivity.this, getRequests, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    jsonList = new ArrayList<>();
                    requestList = new ArrayList<>();

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject ;

                        if (jsonArray != null) {
                            for (int i=0;i<jsonArray.length();i++){
                                jsonList.add(jsonArray.get(i).toString());
                            }
                        }

                        for (int i=0;i<jsonList.size();i++){
                            jsonObject = new JSONObject(jsonList.get(i));
                            request = new Request(jsonObject.getInt("requestID"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("type")
                                    , jsonObject.getString("source_address"), jsonObject.getString("destination_address"),jsonObject.getDouble("price")
                                    ,jsonObject.getString("due_time"),jsonObject.getString("submit_time"),jsonObject.getString("contact_info"),jsonObject.getString("request_status"), jsonObject.getInt("userID_added"));
                            requestList.add(request);
                        }

                        Intent intent = new Intent(HomeActivity.this , MyRequestActivity.class);
                        intent.putExtra("Requests" , requestList);
                        startActivity(intent);


                    } catch (org.json.JSONException e) {
                        Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            getOffers.execute("http://10.0.2.2/user/myRequest.php");

        } else if (id == R.id.nav_logOut) {

            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        } else if (id == R.id.nav_share) {
            startActivity(new Intent(HomeActivity.this, ShareActivity.class));
        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        } else if (id == R.id.nav_tips) {
            startActivity(new Intent(HomeActivity.this, TipsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
