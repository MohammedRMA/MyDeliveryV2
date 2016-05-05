package ksu.mydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Mohammed on 5/3/2016.
 */
public class ProviderBaseAdapter extends BaseAdapter {


        private static ArrayList<Provider> providersArrayList;
        private Context context;
        private LayoutInflater mInflater;

        public ProviderBaseAdapter(Context context, ArrayList<Provider> results) {
            providersArrayList = results;
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return providersArrayList.size();
        }

        public Object getItem(int position) {
            return providersArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_providers_list, null);
                holder = new ViewHolder();
                holder.lblName = (TextView) convertView.findViewById(R.id.lblName);
                holder.lblDeliveredRequests = (TextView) convertView.findViewById(R.id.lblDeliveredRequests);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.lblName.setText(providersArrayList.get(position).getfName()+" "+providersArrayList.get(position).getlName());
            holder.lblDeliveredRequests.setText(String.valueOf(providersArrayList.get(position).getDeliverdRequests() ));

            return convertView;
        }

        static class ViewHolder {
            TextView lblName;
            TextView lblDeliveredRequests;

        }

    public void finish() {

    }


}
