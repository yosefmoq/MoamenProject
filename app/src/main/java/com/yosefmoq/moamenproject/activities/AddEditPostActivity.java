package com.yosefmoq.moamenproject.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.nguyenhoanglam.imagepicker.model.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;
import com.yosefmoq.moamenproject.R;

import java.util.ArrayList;

import javax.annotation.Nullable;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class AddEditPostActivity extends AppCompatActivity {
    SweetAlertDialog sweetAlertDialog;
    EditText etPostText;
    Button btnAdd;
    ImageView ivAdd,ivImage;
    String imageLink;
    FirebaseFirestore firebaseFirestore;
    private StorageReference mStorageRef;
    private static final int PERMISSION_CODE_READ_WRITE_EXTERNAL_STORAGE = 236;
    ArrayList<Image> images= new ArrayList<>();
    int type;
    Post postForEdit;
    int postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_post);
        firebaseFirestore = FirebaseFirestore.getInstance();
        findView();
        type =getIntent().getIntExtra("type",0);
        if(type == 0){
            btnAdd.setText("Add");
        }else {
            btnAdd.setText("Edit");

            sweetAlertDialog.show();
            firebaseFirestore.collection("posts").document(getIntent().getIntExtra("postId",0)+"").get().addOnCompleteListener(runnable -> {
                sweetAlertDialog.hide();
                postForEdit = runnable.getResult().toObject(Post.class);
                Picasso.get().load(postForEdit.getImage()).into(ivImage);
                etPostText.setText(postForEdit.getText());
                postId = postForEdit.getId();
            });

        }
        findClick();
    }

    private void findClick() {
        ivAdd.setOnClickListener(view -> {
            goToGallery();
        });
        ivImage.setOnClickListener(view -> {
            ivAdd.performClick();
        });
        btnAdd.setOnClickListener(view -> {
            sweetAlertDialog.show();

            if(type==0) {
                if (images.size() == 0) {
                    Toast.makeText(this, "please choose image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etPostText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "please Enter post text", Toast.LENGTH_SHORT).show();
                    return;
                }
                StorageReference riversRef = mStorageRef.child("images/"+System.currentTimeMillis()+".jpg");

                riversRef.putFile(images.get(0).getUri())
                        .addOnSuccessListener(taskSnapshot -> {
                            riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                imageLink =uri.toString();
                                Post post = new Post();
                                FirebaseFirestore.getInstance().collection("posts").get().addOnCompleteListener(runnable -> {
                                    int id =runnable.getResult().getDocuments().size()+1;
                                    post.setId(id);
                                    post.setImage(imageLink);
                                    post.setLike_count(0);
                                    post.setText(etPostText.getText().toString());
                                    FirebaseFirestore.getInstance().collection("posts").document(id+"").set(post).addOnCompleteListener(runnable1 -> {
                                        sweetAlertDialog.hide();
                                        finish();
                                    });
                                });
                            });
                        }).addOnFailureListener(e -> {

                });
            }else {
                if(images.size()>0){
                    StorageReference riversRef = mStorageRef.child("images/"+System.currentTimeMillis()+".jpg");

                    riversRef.putFile(images.get(0).getUri())
                            .addOnSuccessListener(taskSnapshot -> {
                                riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    imageLink =uri.toString();
                                    Post post = new Post();
                                    FirebaseFirestore.getInstance().collection("posts").get().addOnCompleteListener(runnable -> {
                                        postForEdit.setImage(imageLink);
                                        postForEdit.setLike_count(0);
                                        postForEdit.setText(etPostText.getText().toString());
                                        FirebaseFirestore.getInstance().collection("posts").document(postForEdit.getId()+"").set(post).addOnCompleteListener(runnable1 -> {
                                            sweetAlertDialog.hide();
                                            finish();
                                        });
                                    });
                                });
                            }).addOnFailureListener(e -> {

                    });

                }else{
                    postForEdit.setText(etPostText.getText().toString());
                    firebaseFirestore.collection("posts").document(postForEdit.getId()+"").set(postForEdit).addOnCompleteListener(task -> {
                        sweetAlertDialog.hide();
                        finish();
                    });
                }
            }



        });
    }

    private void findView() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        etPostText = findViewById(R.id.etPostText);
        btnAdd     = findViewById(R.id.btnAdd);
        ivAdd      = findViewById(R.id.ivAdd);
        ivImage    = findViewById(R.id.ivImage);

    }
    private boolean checkGetImagePermission() {
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return resultMic == PackageManager.PERMISSION_GRANTED;
    }
    private void goToGallery() {
        if (checkGetImagePermission()) {
            ImagePicker.with(this)
                    .setFolderMode(false)
                    .setMultipleMode(true)
                    .setMaxSize(1)
                    .setShowCamera(true)
                    .setSelectedImages(images)
                    .setLimitMessage("لا يمكنك إختيار أكثر من صورة")
                    .start();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "السماح بالتقاط صور من الاستيديو",
                    PERMISSION_CODE_READ_WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            images = data.getParcelableArrayListExtra("ImagePickerImages");
            ivImage.setImageURI(images.get(0).getUri());

        }
    }
}
