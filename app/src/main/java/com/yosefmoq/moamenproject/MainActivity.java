package com.yosefmoq.moamenproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yosefmoq.moamenproject.activities.AddEditPostActivity;
import com.yosefmoq.moamenproject.activities.Post;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    FirebaseFirestore firebaseFirestore;
    RecyclerAdapter recyclerAdapter;
    RecyclerView rvPosts;
    ArrayList<Post> posts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initClicks();
        recyclerAdapter= new RecyclerAdapter(this,posts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(recyclerAdapter);

    }

    private void initViews() {
        rvPosts = findViewById(R.id.list);
        firebaseFirestore = FirebaseFirestore.getInstance();

        floatingActionButton = findViewById(R.id.floatingActionButton);

    }

    private void initClicks() {
        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(this, AddEditPostActivity.class).putExtra("type",0));
        });

        firebaseFirestore.collection("posts").addSnapshotListener((value, error) -> {
            ArrayList<Post> posts = new ArrayList<>();
            List<DocumentSnapshot> documents =value.getDocuments();
            for (DocumentSnapshot d : documents){
                posts.add(d.toObject(Post.class));
            }
            recyclerAdapter.update(posts);
        });
    }


}