package ksu.mydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeProviderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Provider provider;
    private ArrayList<String> jsonList;
    private ArrayList<Offer> offerList;
    private Offer offer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_provider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        provider = (Provider) getIntent().getSerializableExtra("Provider");
        Toast.makeText(HomeProviderActivity.this, provider.getPhoneNumber(), Toast.LENGTH_LONG).show();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

     //   View view=navigationView.inflateHeaderView(R.layout.nav_header_home_provider);
        TextView  name = (TextView)hView.findViewById(R.id.txtProfileName);
        TextView phone = (TextView)hView.findViewById(R.id.txtProfilePhone);
        name.setText(provider.getfName() + " " + provider.getlName());
        phone.setText(provider.getPhoneNumber());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        FloatingActionButton addOffer = (FloatingActionButton) findViewById(R.id.fabAddOffer);
        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeProviderActivity.this, AddOfferActivity.class);
                intent.putExtra("Provider", provider);

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

            Intent intent = new Intent(HomeProviderActivity.this, ProfileProviderActivity.class);
            intent.putExtra("Provider", provider);
            startActivity(intent);



        } else if (id == R.id.nav_assignedRequest) {
            startActivity(new Intent(HomeProviderActivity.this, MyRequestActivity.class));
        } else if (id == R.id.nav_myOffer) {



            HashMap postOffer = new HashMap();
            postOffer.put("txtProviderID", String.valueOf(provider.getID()) );



            PostResponseAsyncTask getOffers = new PostResponseAsyncTask(HomeProviderActivity.this, postOffer, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    jsonList = new ArrayList<>();
                    offerList = new ArrayList<>();

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
                            offer = new Offer(jsonObject.getInt("offerID"), jsonObject.getString("available_days"), jsonObject.getString("available_time_from"), jsonObject.getString("available_time_to")
                                    , jsonObject.getString("regions"), jsonObject.getString("price_limit"), jsonObject.getInt("providerID_made"));
                            offerList.add(offer);
                        }


                        Intent intent = new Intent(HomeProviderActivity.this, MyOfferActivity.class);
                        intent.putExtra("Offers", offerList);
                        startActivity(intent);


                    } catch (org.json.JSONException e) {
                        Toast.makeText(HomeProviderActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            getOffers.execute("http://10.0.2.2/provider/myOffer.php");


        }

        else if (id == R.id.nav_logOut) {
            startActivity(new Intent(HomeProviderActivity.this, LoginActivity.class));
            finish();
        }else if (id == R.id.nav_share) {
            startActivity(new Intent(HomeProviderActivity.this, ShareActivity.class));
        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(HomeProviderActivity.this, ContactActivity.class));
        } else if (id == R.id.nav_tips) {
            startActivity(new Intent(HomeProviderActivity.this, TipsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeProviderActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
