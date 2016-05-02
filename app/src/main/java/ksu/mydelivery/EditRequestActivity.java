package ksu.mydelivery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class EditRequestActivity extends AppCompatActivity {

    private Request request;
    private  String title, description , type , src , dest, price , dueTime ,contactInfo , requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request);


      /*  request = (Request) getIntent().getSerializableExtra("Request");
        ((EditText)findViewById(R.id.txtTitle)).setText(request.getTitle());
        ((EditText)findViewById(R.id.txtDescription)).setText(request.getDescription());
        switch (request.getType().toString()) {
            case "Food":  ((Spinner) findViewById(R.id.spnType)).setSelection(1);
                break;
            case "Breakable":  ((Spinner) findViewById(R.id.spnType)).setSelection(2);
                break;
            case "Other":  ((Spinner) findViewById(R.id.spnType)).setSelection(3);
                break;
            default:  ((Spinner) findViewById(R.id.spnType)).setSelection(0);
        }

        ((EditText)findViewById(R.id.txtSrcAddress)).setText(request.getSrcAddress());
        ((EditText)findViewById(R.id.txtDestAddress)).setText(request.getDestAddress());
        ((EditText)findViewById(R.id.txtPrice)).setText(String.valueOf(request.getPrice()));
        ((EditText)findViewById(R.id.txtDueTime)).setText(request.getDueTime());
        ((EditText)findViewById(R.id.txtContactInfo)).setText(request.getContactInfo());
        ((TextView)findViewById(R.id.lblSubmitTime)).setText("Submitted At: "+request.getSubmitTime());*/

        ImageButton btnUpdate = (ImageButton) findViewById(R.id.imgbtnSave) ;
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                UpdateRequest();
            }
        });

        ImageButton btnDelete = (ImageButton) findViewById(R.id.imgbtnDelete) ;
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                DeleteRequest();
            }
        });

    }

    public void UpdateRequest() {


        title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
        description = ((EditText) findViewById(R.id.txtDescription)).getText().toString();
        Spinner spnType = ((Spinner) findViewById(R.id.spnType));
        type   = spnType.getSelectedItem().toString();
        src = ((EditText) findViewById(R.id.txtSrcAddress)).getText().toString();
        dest = ((EditText) findViewById(R.id.txtDestAddress)).getText().toString();
        price = ((EditText) findViewById(R.id.txtPrice)).getText().toString();
        dueTime = ((EditText) findViewById(R.id.txtDueTime)).getText().toString();
        contactInfo = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
//        requestID = String.valueOf(request.getId());

        if (title.equals("") || type.equals("--Type--") || src.equals("") || dest.equals("") || price.equals("") || dueTime.equals("") || contactInfo.equals("") ) {
            (findViewById(R.id.txtTitle)).requestFocus();
            ((EditText) (findViewById(R.id.txtTitle))).setError("FIELD CANNOT BE EMPTY");

        }

        else{

            HashMap postData = new HashMap();

            postData.put("txtRequestID", "1");
            postData.put("txtTitle", title);
            postData.put("txtDescription", description);
            postData.put("txtType", type);
            postData.put("txtSrcAddress", src);
            postData.put("txtDestAddress", dest);
            postData.put("txtPrice", price );
            postData.put("txtDueTime", dueTime);
            postData.put("txtContactInfo", contactInfo);
            postData.put("txtRequestStatus", "New" );



            final PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditRequestActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Toast.makeText(EditRequestActivity.this,s, Toast.LENGTH_LONG).show();
                    if (s.contains("success")) {
                        Toast.makeText(EditRequestActivity.this,"Request Updated Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(EditRequestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/user/updateRequest.php");
        }

    }

    public void DeleteRequest() {

       // requestID = String.valueOf(request.getId());


        HashMap postData = new HashMap();

        postData.put("txtRequestID", "1");


        final PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditRequestActivity.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Toast.makeText(EditRequestActivity.this,s, Toast.LENGTH_LONG).show();
                if (s.contains("success")) {
                    Toast.makeText(EditRequestActivity.this,"Request Removed Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(EditRequestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        task1.execute("http://10.0.2.2/user/removeRequest.php");

    }

}
