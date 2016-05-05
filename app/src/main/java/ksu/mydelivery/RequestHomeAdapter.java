package ksu.mydelivery;




    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.TextView;

    import java.util.ArrayList;

    /**
     * Created by Mohammed on 4/26/2016.
     */
    public class RequestHomeAdapter {
        private static ArrayList<Request> requestsArrayList;
        private String dueTime;

        private LayoutInflater mInflater;

        public RequestHomeAdapter(Context context, ArrayList<Request> results) {
            requestsArrayList = results;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return requestsArrayList.size();
        }

        public Object getItem(int position) {
            return requestsArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_request_list, null);
                holder = new ViewHolder();
                holder.lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
                holder.lblType = (TextView) convertView.findViewById(R.id.lblType);
                holder.lblPrice = (TextView) convertView.findViewById(R.id.lblPrice);
                holder.lblDueTime = (TextView) convertView.findViewById(R.id.lblDueTime);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.lblTitle.setText(requestsArrayList.get(position).getTitle());
            holder.lblType.setText(requestsArrayList.get(position).getType());
            holder.lblPrice.setText(String.valueOf(requestsArrayList.get(position).getPrice()));
            dueTime = requestsArrayList.get(position).getDueTime();
            if (dueTime != null) {
                String[] split = dueTime.split(":");
                String part1 = split[0]; // The number before ' : '
                dueTime = part1 + ":00";
            }
            holder.lblDueTime.setText(dueTime);

            return convertView;
        }


        static class ViewHolder {
            TextView lblTitle;
            TextView lblType;
            TextView lblPrice;
            TextView lblDueTime;
        }
    }



