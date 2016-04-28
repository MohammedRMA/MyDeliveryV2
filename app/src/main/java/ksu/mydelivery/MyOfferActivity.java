package ksu.mydelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MyOfferActivity extends AppCompatActivity {

    private ArrayList<Offer> offerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_offer);

        offerList = new ArrayList<>();

        offerList = (ArrayList<Offer>) getIntent().getSerializableExtra("Offers");


       final ListView lvOffers= (ListView) findViewById(R.id.lvMyOffer);
        lvOffers.setAdapter(new OfferBaseAdapter(this, offerList));

        lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lvOffers.getItemAtPosition(position);
                Offer offer = (Offer) o;

                Intent intent = new Intent(MyOfferActivity.this, EditOfferActivity.class);
                intent.putExtra("Offers", offerList);
                intent.putExtra("Offer", offer);
                startActivity(intent);
                finish();
            }
        });


    }


}