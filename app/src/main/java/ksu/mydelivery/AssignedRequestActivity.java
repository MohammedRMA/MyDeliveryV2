package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AssignedRequestActivity extends AppCompatActivity {

    private ArrayList<Request> requestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_request);

        requestList = new ArrayList<>();

        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");
/*

        final ListView lvRequest= (ListView) findViewById(R.id.lvMyOffer);
        lvRequest.setAdapter(new RequestBaseAdapter(this, requestList));

        lvRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lvRequest.getItemAtPosition(position);
                Request request = (Request) o;

                Intent intent = new Intent(AssignedRequestActivity.this, EditRequestStatusActivity.class);
                intent.putExtra("Requests", requestList);
                intent.putExtra("Request", request);
                startActivity(intent);
                finish();
            }
        });

        */

    }
}
