package com.yosefmoq.moamenproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yosefmoq.moamenproject.activities.AddEditPostActivity;
import com.yosefmoq.moamenproject.activities.Post;
import com.yosefmoq.moamenproject.utils.Session;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Post> mDataSet;

    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, tvLikeCount;
        ImageView ivPostImage;
        LinearLayout llLike, llEdit;

        // We'll use this field to showcase matching the holder from the test.

        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tvPost);
            ivPostImage = v.findViewById(R.id.ivPostImage);
            llLike = v.findViewById(R.id.llLike);
            llEdit = v.findViewById(R.id.llEdit);
            tvLikeCount = v.findViewById(R.id.tvLikeCount);
        }

    }


    public RecyclerAdapter(Context context, List<Post> dataSet) {
        mDataSet = dataSet;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycler, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Post post = mDataSet.get(position);
        viewHolder.tvLikeCount.setText(post.getLike_count() + "");
        viewHolder.textView.setText(post.getText());
        if (post.getUser_id() == Session.getInstance(mContext).getLocalSave().getUserInfo().getId()) {
            viewHolder.llEdit.setVisibility(View.VISIBLE);
        } else {
            viewHolder.llEdit.setVisibility(View.GONE);
        }
        viewHolder.llLike.setOnClickListener(view -> {
            viewHolder.tvLikeCount.setText(String.valueOf(post.getLike_count() + 1));
            post.setLike_count(post.getLike_count() + 1);
            FirebaseFirestore.getInstance().collection("posts").document(post.getId() + "").set(post);
        });
        viewHolder.llEdit.setOnClickListener(view -> {
            mContext.startActivity(new Intent(mContext, AddEditPostActivity.class).putExtra("type", 1).putExtra("postId", post.getId()));
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void update(List<Post> posts) {
        this.mDataSet.clear();
        this.mDataSet.addAll(posts);
        notifyDataSetChanged();
    }
}
