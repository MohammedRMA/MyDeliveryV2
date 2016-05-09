package ksu.mydelivery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignProviderActivity extends AppCompatActivity {

    private String requestID ;
    private ArrayList<Provider> providerList;
    private ArrayList<Offer> matchedOfferList;
    private RegularUser user ;
    private ArrayList<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_provider);

        providerList = new ArrayList<>();
        matchedOfferList = new ArrayList<>();
        requestList = new ArrayList<>();
        requestID = (String) getIntent().getSerializableExtra("RequestID");
        providerList = (ArrayList<Provider>) getIntent().getSerializableExtra("Providers");
        matchedOfferList = (ArrayList<Offer>) getIntent().getSerializableExtra("MatchedOffers");
        user = (RegularUser) getIntent().getSerializableExtra("RegularUser");
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");

        final ListView lvProviders= (ListView) findViewById(R.id.lvProviders);
        lvProviders.setAdapter( new ProviderBaseAdapter(this, providerList));

        lvProviders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                Object o = lvProviders.getItemAtPosition(position);
               final Provider provider = (Provider) o;

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(AssignProviderActivity.this);

                aBuilder.setMessage("Are you sure you want to assign your request to "+provider.getfName()+" "+provider.getlName()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {

                                HashMap postData = new HashMap();
                                postData.put("txtOfferID", String.valueOf(matchedOfferList.get(position).getID()) );
                                postData.put("txtRequestID", requestID);


                                PostResponseAsyncTask task1 = new PostResponseAsyncTask(AssignProviderActivity.this, postData, new AsyncResponse() {
                                    @Override
                                    public void processFinish(String s) {
                                        if (s.contains("success")) {
                                            Toast.makeText(AssignProviderActivity.this, "Your request has been assigned to "+providerList.get(position).getfName()+" "+providerList.get(position).getlName(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(AssignProviderActivity.this , HomeActivity.class);
                                            intent.putExtra("RegularUser",user);
                                            intent.putExtra("Requests",requestList);
                                            startActivity(intent);
                                            AssignProviderActivity.this.finish();
                                        }
                                        else {
                                            Toast.makeText(AssignProviderActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                task1.execute("http://10.0.2.2/transaction/addTransaction.php");


                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = aBuilder.create();
                alert.setTitle("Request Assigning");
                alert.show();

    }

            });
    }

}
