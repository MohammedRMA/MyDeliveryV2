package ksu.mydelivery;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import java.util.ArrayList;



public class MyRequestActivity extends TabActivity   implements TabHost.OnTabChangeListener  {



    private static final String LIST1_TAB_TAG = "Current Request";
    private static final String LIST2_TAB_TAG = "Previous Request";
    private ArrayList<Request> requestList;
    private static ArrayList<Request> currentRequest;
    private static ArrayList<Request> previousRequest;
    private static RequestBaseAdapter currentRequestAdapter;
    private static RequestBaseAdapter previousRequestAdapter;
    private ListView lvCurrentRequest;
    private ListView lvPreviousRequest;
    private TabHost tabHost;
    private String type;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        tabHost = getTabHost();
        tabHost.setOnTabChangedListener(this);

        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");
        type = "";
        type = (String) getIntent().getSerializableExtra("Type");

        currentRequest = new ArrayList<>();
        previousRequest = new ArrayList<>();

        if (type.equalsIgnoreCase("User")) {
            for (int i = 0; i < requestList.size(); i++) {
                if (requestList.get(i).getStatus().equalsIgnoreCase("new")) {
                    currentRequest.add(requestList.get(i));
                } else {
                    previousRequest.add(requestList.get(i));
                }
            }
        }

        else if (type.equalsIgnoreCase("Provider")) {
            for (int i = 0; i < requestList.size(); i++) {
                if (requestList.get(i).getStatus().equalsIgnoreCase("delivered")) {
                    previousRequest.add(requestList.get(i));
                } else {
                    currentRequest.add(requestList.get(i));
                }
            }
        }


        lvPreviousRequest = (ListView) findViewById(R.id.lvMyPreviousRequest);





        lvCurrentRequest = (ListView) findViewById(R.id.lvMyCurrentRequest);


        lvCurrentRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Object o = lvCurrentRequest.getItemAtPosition(position);
                Request request = (Request) o;
                if (type.equalsIgnoreCase("User")) {
                    Intent intent = new Intent(MyRequestActivity.this, EditRequestActivity.class);
                    intent.putExtra("Requests", requestList);
                    intent.putExtra("Request", request);
                    intent.putExtra("Type", type);
                    startActivity(intent);
                    finish();
                }
                else if (type.equalsIgnoreCase("Provider")) {
                    Intent intent = new Intent(MyRequestActivity.this, AssignedRequestActivity.class);
                    intent.putExtra("Requests", requestList);
                    intent.putExtra("Request", request);
                    intent.putExtra("Type", type);
                    startActivity(intent);
                    finish();
                }
            }
        });

        lvPreviousRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Object o = lvPreviousRequest.getItemAtPosition(position);
                Request request = (Request) o;
                if (type.equalsIgnoreCase("User")) {
                    Intent intent = new Intent(MyRequestActivity.this, EditRequestActivity.class); // Rate Activity
                    intent.putExtra("Requests", requestList);
                    intent.putExtra("Request", request);
                    intent.putExtra("Type", type);
                    startActivity(intent);
                    finish();
                }
            }
        });



        tabHost.addTab(tabHost.newTabSpec(LIST1_TAB_TAG).setIndicator(LIST1_TAB_TAG).setContent(new TabHost.TabContentFactory() {

            public View createTabContent(String arg0) {

                return lvCurrentRequest;

            }

        }));

        tabHost.addTab(tabHost.newTabSpec(LIST2_TAB_TAG).setIndicator(LIST2_TAB_TAG).setContent(new TabHost.TabContentFactory() {

            public View createTabContent(String arg0) {

                return lvPreviousRequest;

            }

        }));

    }



    public void onTabChanged(String tabName) {

        if(tabName.equals(LIST2_TAB_TAG)) {
            previousRequestAdapter = new RequestBaseAdapter(this , previousRequest);
            lvPreviousRequest.setAdapter(previousRequestAdapter);

        }

        else if(tabName.equals(LIST1_TAB_TAG)) {
            currentRequestAdapter = new RequestBaseAdapter(this , currentRequest);
            lvCurrentRequest.setAdapter(currentRequestAdapter);
        }

    }
}
