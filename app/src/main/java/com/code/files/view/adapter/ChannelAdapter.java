package com.code.files.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oxootv.spagreen.R;
import com.code.files.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.OriginalViewHolder> {

    private String type;
    private List<Channel> channels;
    private Context ctx;

    private OnItemClickListener mOnItemClickListener;

    private OriginalViewHolder viewHolder;


    public interface OnItemClickListener {
        void onItemClick(View view, Channel obj, int position, OriginalViewHolder holder);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ChannelAdapter(Context context, List<Channel> channels, String type) {
        this.channels = channels;
        ctx = context;
        this.type = type;
    }


    @NonNull
    @Override
    public OriginalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OriginalViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channel_tv_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final OriginalViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        int pos2 = position +1;
        Channel obj = channels.get(position);
        holder.channelNameTv.setText(pos2 + " . " + obj.getTvName());
      //  holder.channelImageTv.setImageURI(Uri.parse(obj.getThumbnailUrl()));
       // ImageView channelImageTv;
        Picasso.get()
                .load(obj.getThumbnailUrl())
               // .placeholder(R.drawable.poster_placeholder)
                 .centerCrop()
               // .centerInside()
                .resize(180,70)
               // .error(R.drawable.poster_placeholder)
                .into(holder.channelImageTv);
        //holder.getLayoutPosition();
        holder.channelNameTv.setFocusable(true);
        holder.channelNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, channels.get(position), position, holder);
                }
            }
        });

    }

    public int getItemPosition(String eventId) {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getLiveTvId().equals(eventId)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {

        public Button channelNameTv;
        public ImageView channelImageTv;
        LinearLayout itemLayout;

        public OriginalViewHolder(View v) {
            super(v);
            channelNameTv = v.findViewById(R.id.c_name_tv);
            channelImageTv = v.findViewById(R.id.c_image_tv);
            itemLayout = itemView.findViewById(R.id.item_cl);
        }
    }

}
