package com.code.files.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.oxootv.spagreen.R;
import com.code.files.model.Program;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.OriginalViewHolder> implements Filterable {

    private List<Program> programs;
    private List<Program> items;
    private List<Program> contactList;
    private List<Program> programFiltered;
    private Context context;
    private int row_index = -1;

    private int lastPosition = -1;
    private boolean on_attach = true;

    private OnItemClickListener mOnItemClickListener;

    private OriginalViewHolder viewHolder;

    public interface OnItemClickListener {
        void onItemClick(View view, Program obj, int position, OriginalViewHolder holder);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public ProgramAdapter(List<Program> programs, Context context) {
        this.programs = programs;
        this.context = context;
    }


    @NonNull
    @Override
    public OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OriginalViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Long calendar = Calendar.getInstance().getTimeInMillis();
        //   calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis());
     //   String currentTime= DateFormat.format("yyyy-MM-dd H:i:s",calendar).toString();
        Program obj = programs.get(position);
        holder.programTypeTv.setText(obj.getTitle());
        holder.programTimeTv.setText(obj.getTime());
        Long sPro = Long.valueOf(obj.getProgramStart());
        Long ePro = Long.valueOf(obj.getProgramEnd());

            if (calendar >= sPro * 1000 && calendar <= ePro * 1000){
                //holder.programTimeTv.setText("LIVE" + obj.getTime());
               // holder.itemLayout.setBackground(context.getResources().getDrawable(R.drawable.button_focus_live));
                //holder.itemLayout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.button_focus_live, null));
                holder.textLive.setVisibility(View.VISIBLE);
            }
            else
                holder.textLive.setVisibility(View.GONE);
               // holder.itemLayout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.button_focus, null));


       // if (obj.getTime().equalsIgnoreCase(String.valueOf(currentTime)) )
          //  holder.itemLayout.setBackgroundColor(R.color.splashScreenColor);
        //  holder.channelImageTv.setImageURI(Uri.parse(obj.getThumbnailUrl()));
        // ImageView channelImageTv;
//        Picasso.get()
//                .load(obj.getThumbnailUrl())
//                // .placeholder(R.drawable.poster_placeholder)
//                .centerCrop()
//                // .centerInside()
//                .resize(180,70)
//                // .error(R.drawable.poster_placeholder)
//                .into(holder.channelImageTv);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, programs.get(position), position, holder);
                 //   notifyDataSetChanged();

                }
            }
        });
        if(row_index==position){
            holder.itemLayout.setBackgroundColor(Color.parseColor("#567845"));
           // holder.tv1.setTextColor(Color.parseColor("#ffffff"));
        }





    }
    public void setBackOnPlay(OriginalViewHolder holder, int position){
        Program obj = programs.get(position);
      //  OriginalViewHolder holder = null;
        holder.itemLayout.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.button_focus_live, null));
    }
    public int getItemPosition(String eventId) {
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).getId().equals(eventId)) {
                return i;
            }
        }
        return -1;
    }
    public int getProgramPosition(String eventId, String envenId1) {
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).getProgramStart().equals(eventId) && programs.get(i).getTitle().equals(envenId1)) {
                return i;
            }
        }
        return -1;
    }
    public void updateData(List<Program> viewModels) {
        items.clear();
        items.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }


//    @Override
//    public Filter getFilter() {
//        return new UserFilter();
//    }
//    private static class UserFilter extends Filter {
//
//        private final ProgramAdapter adapter;
//
//        private final List<Program> originalList;
//
//        private final List<Program> filteredList;
//
//        private UserFilter(ProgramAdapter adapter, List<Program> originalList) {
//            super();
//            this.adapter = adapter;
//            this.originalList = new LinkedList<>(originalList);
//            this.filteredList = new ArrayList<>();
//        }
//
//
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            filteredList.clear();
//            final FilterResults results = new FilterResults();
//
//            if (constraint.length() == 0) {
//                filteredList.addAll(originalList);
//            } else {
//                final String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for (final Program row : originalList) {
//                    if (row.getTime().contains(filterPattern)) {
//                        filteredList.add(row);
//                    }
//                }
//            }
//            results.values = filteredList;
//            results.count = filteredList.size();
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            adapter.programFiltered.clear();
//            adapter.programFiltered.addAll((ArrayList<Program>) results.values);
//            adapter.notifyDataSetChanged();
//        }
//    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    programFiltered = programs;
                } else {
                    List<Program> filteredList = new ArrayList<>();
                    for (Program row : programs) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTime().equals(charString)) {
                            filteredList.add(row);
                        }
                    }

                    programFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = programFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                programFiltered.clear();
                programFiltered = (List<Program>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class OriginalViewHolder extends RecyclerView.ViewHolder {

        TextView programTypeTv, programTimeTv, textLive;
        LinearLayout itemLayout;

        public OriginalViewHolder(View v) {
            super(v);
            programTimeTv = itemView.findViewById(R.id.program_time_tv);
            programTypeTv = itemView.findViewById(R.id.program_type_tv);
            textLive = itemView.findViewById(R.id.live_tv);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
    public interface OnProgramClickListener{
        void onProgramClick(Program program);
    }



}
