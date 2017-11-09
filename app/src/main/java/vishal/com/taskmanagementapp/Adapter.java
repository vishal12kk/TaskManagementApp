package vishal.com.taskmanagementapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Date> dateList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, des, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.main_title_);
            des = (TextView) view.findViewById(R.id.description_);
            date = (TextView) view.findViewById(R.id.date_);
        }
    }


    public Adapter(List<Date> moviesList) {
        this.dateList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Date dt = dateList.get(position);
        holder.title.setText(dt.getMainTitle());
        holder.des.setText(dt.getDescription());
        holder.date.setText(dt.getDate());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}