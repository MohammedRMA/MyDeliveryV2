package ksu.mydelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import java.util.HashMap;

public class AddRequestActivity extends AppCompatActivity {

    private  String title, description , type , src , dest, price , dueTime , contactInfo , userID;
     private RegularUser user ;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAddRequest) ;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                AddRequest();
            }
        });

          user = (RegularUser) getIntent().getSerializableExtra("RegularUser");
    }

    public void AddRequest () {

        title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
        description = ((EditText) findViewById(R.id.txtDescr)).getText().toString();
        Spinner spnType = ((Spinner) findViewById(R.id.spnType));
        type   = spnType.getSelectedItem().toString();
        src = ((EditText) findViewById(R.id.txtSource)).getText().toString();
        dest = ((EditText) findViewById(R.id.txtDest)).getText().toString();
        price = ((EditText) findViewById(R.id.txtPrice)).getText().toString();
        dueTime = ((EditText) findViewById(R.id.txtDueTime)).getText().toString();
        contactInfo = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
        userID = String.valueOf(user.getID());

        if (title.equals("") || type.equals("--Type--") || src.equals("") || dest.equals("") || price.equals("") || dueTime.equals("") || contactInfo.equals("") ) {
            (findViewById(R.id.txtTitle)).requestFocus();
            ((EditText) (findViewById(R.id.txtTitle))).setError("FIELD CANNOT BE EMPTY");

        }

        else {


            HashMap postData = new HashMap();
            postData.put("txtTitle", title);
            postData.put("txtDescription", description);
            postData.put("txtType", type);
            postData.put("txtSrcAddress", src);
            postData.put("txtDestAddress", dest);
            postData.put("txtPrice", price );
            postData.put("txtDueTime", dueTime);
            postData.put("txtContactInfo", contactInfo);
           postData.put("txtUserIDAdded", userID );





            final PostResponseAsyncTask task1 = new PostResponseAsyncTask(AddRequestActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Toast.makeText(AddRequestActivity.this,s, Toast.LENGTH_LONG).show();
                    if (s.contains("success")) {
                        Toast.makeText(AddRequestActivity.this,"Request Added Successfully", Toast.LENGTH_LONG).show();
                       finish();
                    }
                    else {
                        Toast.makeText(AddRequestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

                task1.execute("http://10.0.2.2/user/addRequest.php");

        }

    }
}
