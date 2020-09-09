package com.example.eduapp.posts.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eduapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostBottomSheet extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener{

    public PostBottomSheet(){

    }

    static final int PICK_CAPTURE = 1;
    ImageView imageView;
    Button button;
    FloatingActionButton fab;
    StorageReference fs;
    FirebaseFirestore ff;
    FirebaseAuth fa;
    TextInputEditText title,desc;
    Spinner spinner;
    String class_name;
    Uri imageUri;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create,container,false);
        progressBar = view.findViewById(R.id.cre_prog);
        fs = FirebaseStorage.getInstance().getReference().child("Images");
        fa = FirebaseAuth.getInstance();
        title = view.findViewById(R.id.post_title);
        desc = view.findViewById(R.id.post_desc);
        ff = FirebaseFirestore.getInstance();
        button = view.findViewById(R.id.post_image_picker);

        spinner = view.findViewById(R.id.post_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.classes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        fab = view.findViewById(R.id.post_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText() == null || title.getText().toString().isEmpty()){
                    title.setError("required");
                    title.requestFocus();
                }else if(desc.getText() == null || desc.getText().toString().isEmpty()){
                    desc.setError("required");
                    desc.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    final StorageReference sr = fs.child(UUID.randomUUID().toString());
                    Toast.makeText(getContext(),"Upload Started",Toast.LENGTH_SHORT).show();
                    final Map<String,Object> map = new HashMap<>();
                    map.put("class",class_name);
                    map.put("title",title.getText().toString());
                    map.put("description",desc.getText().toString());
                    map.put("userid",fa.getUid());
                    DocumentReference docref = ff.collection("Users").document(fa.getUid());
                    docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc.get("name") != null) {
                                    map.put("name",doc.get("name"));
                                }
                                if (doc.get("class") != null) {
                                    map.put("class",doc.get("class"));
                                }
                                map.put("time", System.currentTimeMillis());
                                if(imageUri == null){
                                    progressBar.setVisibility(View.GONE);
                                    map.put("imageurl","");
                                    ff.collection("Posts").add(map);
                                    Toast.makeText(getContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }else{
                                    sr.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                        @Override
                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                            if (!task.isSuccessful()) {
                                                throw task.getException();
                                            }
                                            // Continue with the task to get the download URL
                                            return sr.getDownloadUrl();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if(task.isSuccessful()){
                                                map.put("imageurl",task.getResult().toString());
                                                ff.collection("Posts").add(map);
                                                Toast.makeText(getContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                dismiss();
                                            }
                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(getContext(),"Upload Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
//            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        class_name = (String)adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
