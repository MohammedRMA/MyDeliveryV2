package ksu.mydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ShareActivity extends ActionBarActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_share);

            Button btn_share=(Button)findViewById(R.id.shareit);
            btn_share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    shareIt();
                }
            });
        }
        private void shareIt() {
//sharing implementation here
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MyDelivery App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Download MyDelivery Application, clicke here to visit https://mydelivery.app.com/ ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
    }