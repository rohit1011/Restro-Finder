package firebasepratice.com.firebasepracticetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import firebasepratice.com.firebasepracticetest.Objects.BookObject;

public class AddBookActivity extends AppCompatActivity {
    private String[] country = { "India", "USA", "China", "Japan", "Other"};

    private TextInputEditText bookName,author,ISBN,publication,genre,quantity;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button accept,cancel;
    private ImageView imageView,photoview;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private ConstraintLayout constraintLayout;
    private Spinner spinner;
    private String Genre;
    private Boolean validation = true;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookName = findViewById(R.id.book_name);
        author = findViewById(R.id.phone_number);
        ISBN = findViewById(R.id.isbn);
        publication = findViewById(R.id.publication);
        accept = findViewById(R.id.accept);
        cancel = findViewById(R.id.cancel);
        imageView = findViewById(R.id.upload_photo);
        photoview = findViewById(R.id.upload_photo1);
        genre = findViewById(R.id.genre);
        quantity = findViewById(R.id.available_number);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        constraintLayout = findViewById(R.id.constraint);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.dialog_spinnerlayout);
        adapter.setDropDownViewResource(R.layout.dialog_spinnerlayout);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] products = getResources().getStringArray(R.array.country_arrays);
                Genre = products[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                validation =false;
                spinner.requestFocus();
                ((TextView)spinner.getSelectedView()).setError("Error message");

            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (ISBN.getText().toString().length() < 1) {
                        ISBN.setError("ISBN Required");
                        ISBN.requestFocus();
                        validation = false;
                    }
                if (bookName.getText().toString().length() < 1) {
                    bookName.setError("Book Name Required");
                    bookName.requestFocus();
                    validation = false;
                }
                if (publication.getText().toString().length() < 1) {
                    publication.setError("Publication Required");
                    publication.requestFocus();
                    validation = false;
                }
                if (author.getText().toString().length() < 1) {
                    author.setError("Author Required");
                    author.requestFocus();
                    validation = false;
                }
                if (quantity.getText().toString().length()<1){
                        quantity.setError("Quantity required");
                        quantity.requestFocus();
                        validation=false;
                }

                if (Genre.equals("Select the Genre")){
                        validation=false;
                    spinner.requestFocus();
                    ((TextView)spinner.getSelectedView()).setTextColor(Color.RED);
                    ((TextView)spinner.getSelectedView()).setError("Error message");
                }

                if (hasImage(imageView)==false){
                    Snackbar snackbar = Snackbar
                            .make(constraintLayout, "Please add Book Image", Snackbar.LENGTH_LONG)
                            .setAction("Add Image", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chooseImage();
                        }
                    });

                    snackbar.show();
                    validation = false;


                }

            if (validation) {uploadImage();}

            }
        });
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chooseImage();
    }
});
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
});

    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            progressDialog.setCancelable(false);
                        }
                    })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.i("TAG","onsucc"+downloadUri);
                        Toast.makeText(AddBookActivity.this, "Upload Successful ", Toast.LENGTH_SHORT).show();
                        BookObject bookObject = new BookObject(bookName.getText().toString()
                                ,author.getText().toString()
                                ,ISBN.getText().toString()
                                ,publication.getText().toString()
                                ,downloadUri.toString()
                                ,Genre
                                ,quantity.getText().toString());
                        databaseReference.child("Book Details").child(ISBN.getText().toString()).setValue(bookObject);
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddBookActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBookActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                photoview.setVisibility(View.GONE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
