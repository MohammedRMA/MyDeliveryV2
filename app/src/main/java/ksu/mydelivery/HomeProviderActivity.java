package ksu.mydelivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.ListView;
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
    private ArrayList<Request> requestList , assignedRequestList;
    private Request request;
    private  boolean found;
    private int i ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_provider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        provider = (Provider) getIntent().getSerializableExtra("Provider");
      //  Toast.makeText(HomeProviderActivity.this, provider.getPhoneNumber(), Toast.LENGTH_LONG).show();

        requestList = new ArrayList<>();
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");
        assignedRequestList = new ArrayList<>();
        assignedRequestList= (ArrayList<Request>) getIntent().getSerializableExtra("AssignedRequests");
        found = false ;
        for ( i = 0 ; i < assignedRequestList.size() ; i++) {

            if (assignedRequestList.get(i).getStatus().equalsIgnoreCase("new")) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(HomeProviderActivity.this);

                aBuilder.setMessage("You have a new request assigned for you !")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {


                                HashMap postData = new HashMap();
                                postData.put("txtRequestID", String.valueOf(assignedRequestList.get(i-1).getId()) );
                                postData.put("txtTitle", assignedRequestList.get(i-1).getTitle());
                                postData.put("txtDescription", assignedRequestList.get(i-1).getDescription());
                                postData.put("txtType", assignedRequestList.get(i-1).getType());
                                postData.put("txtSrcAddress", assignedRequestList.get(i-1).getSrcAddress());
                                postData.put("txtDestAddress", assignedRequestList.get(i-1).getDestAddress());
                                postData.put("txtPrice", String.valueOf(assignedRequestList.get(i-1).getPrice()) );
                                postData.put("txtDueTime", assignedRequestList.get(i-1).getDueTime());
                                postData.put("txtContactInfo", assignedRequestList.get(i-1).getContactInfo());
                                postData.put("txtRequestStatus", "InProgress");


                                PostResponseAsyncTask task1 = new PostResponseAsyncTask(HomeProviderActivity.this, postData, new AsyncResponse() {
                                    @Override
                                    public void processFinish(String s) {
                                        if (s.contains("success")) {
                                            Toast.makeText(HomeProviderActivity.this,"Request has been assigned successfully",Toast.LENGTH_LONG);
                                           found = true ;
                                            dialog.cancel();
                                        }
                                        else {
                                            Toast.makeText(HomeProviderActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                task1.execute("http://10.0.2.2/user/updateRequest.php");

                            }
                        });

                AlertDialog alert = aBuilder.create();
                alert.setTitle("New Request");
                alert.show();

            }
            if (found) {
                break;
            }
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

     //   View view=navigationView.inflateHeaderView(R.layout.nav_header_home_provider);
        TextView  name = (TextView)hView.findViewById(R.id.txtProfileName);
        TextView phone = (TextView)hView.findViewById(R.id.txtProfilePhone);
        name.setText(provider.getfName() + " " + provider.getlName());
        phone.setText("0" + provider.getPhoneNumber());



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
            intent.putExtra("Requests" , requestList);

            startActivity(intent);



        } else if (id == R.id.nav_assignedRequest) {

            HashMap putData = new HashMap();
            putData.put("txtProviderID", String.valueOf(provider.getID()) );

            PostResponseAsyncTask getRequests = new PostResponseAsyncTask(HomeProviderActivity.this, putData , new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                    jsonList = new ArrayList<>();
                    requestList = new ArrayList<>();

                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject ;

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonList.add(jsonArray.get(i).toString());
                            }
                        }

                            for (int i = 0; i < jsonList.size(); i++) {
                                jsonObject = new JSONObject(jsonList.get(i));
                                request = new Request(jsonObject.getInt("requestID"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("type")
                                        , jsonObject.getString("source_address"), jsonObject.getString("destination_address"), jsonObject.getDouble("price")
                                        , jsonObject.getString("due_time"), jsonObject.getString("submit_time"), jsonObject.getString("contact_info"), jsonObject.getString("request_status"), jsonObject.getInt("userID_added"));
                                requestList.add(request);
                            }

                            Intent intent = new Intent(HomeProviderActivity.this, MyRequestActivity.class);
                            intent.putExtra("Requests", requestList);
                            String type = "Provider";
                            intent.putExtra("Type", type);
                            startActivity(intent);


                    } catch (org.json.JSONException e) {
                        Toast.makeText(HomeProviderActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }


                }
            });

            getRequests.execute("http://10.0.2.2/transaction/assignedRequest.php");

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
        } else if (id == R.id.nav_rate) {
        Intent intent = new Intent(HomeProviderActivity.this, RateAppActivity.class);
            intent.putExtra("Provider", provider);
            intent.putExtra("Requests" , requestList);

            startActivity(intent);

        }else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeProviderActivity.this, AboutActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
