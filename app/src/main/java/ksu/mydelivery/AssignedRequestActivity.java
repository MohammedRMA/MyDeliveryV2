package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignedRequestActivity extends AppCompatActivity {

    private ArrayList<Request> requestList;
    private Request request;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_request);

        requestList = new ArrayList<>();
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");
        request = (Request) getIntent().getSerializableExtra("Request");
        type = "";
        type = (String) getIntent().getSerializableExtra("Type");

            ((TextView) findViewById(R.id.lblTitle)).setText(request.getTitle());
            ((TextView) findViewById(R.id.lblDescr)).setText(request.getDescription());
            ((TextView) findViewById(R.id.lblType)).setText(request.getType());
            ((TextView) findViewById(R.id.lblSrc)).setText(request.getSrcAddress());
            ((TextView) findViewById(R.id.lblDest)).setText(request.getDestAddress());
            ((TextView) findViewById(R.id.lblPrice)).setText(String.valueOf(request.getPrice()));
            ((TextView) findViewById(R.id.lblDueTime)).setText(request.getDueTime());
            ((TextView) findViewById(R.id.lblContactInfo)).setText(request.getContactInfo());
            ((TextView) findViewById(R.id.lblSubmitTime)).setText("Submitted at: "+request.getSubmitTime());

        ImageButton btnDeliver = (ImageButton) findViewById(R.id.imgbtnDeliver) ;

        btnDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverRequest();
            }
        });
    }

    public void deliverRequest() {

        HashMap postData = new HashMap();

        postData.put("txtRequestID",String.valueOf(request.getId()) );


        final PostResponseAsyncTask task1 = new PostResponseAsyncTask(AssignedRequestActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                if (s.contains("success")) {
                    Toast.makeText(AssignedRequestActivity.this,"Request Delivered Successfully", Toast.LENGTH_LONG).show();

                  /*  for (int i = 0 ; i < requestList.size() ; i++) {
                        if (requestList.get(i).getId() == request.getId() ) {
                            requestList.remove(i);
                            break;
                        }
                    }*/

                    Intent intent = new Intent(AssignedRequestActivity.this , MyRequestActivity.class);
                    intent.putExtra("Requests",requestList);
                    intent.putExtra("Type",type);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(AssignedRequestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
                    // ACTUAL TIME
        task1.execute("http://10.0.2.2/provider/deliverRequest.php");

    }
}
