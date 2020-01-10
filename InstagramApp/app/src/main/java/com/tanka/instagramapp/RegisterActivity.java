package com.tanka.instagramapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import API.UserAPI;
import Model.User;
import ServerResponse.ImageResponse;
import ServerResponse.SignUpResponse;
import Url.Url;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import strictmode.StrictModeClass;

import static Url.Url.imagePath;

public class RegisterActivity extends AppCompatActivity {
    private EditText fname, lname, uname, pwd;
    private Button signup;
    private TextView signin;
//    private CircleImageView imgProfile;
//    String imagePath;
//    private String imageName = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.firstName);
        lname = findViewById(R.id.lastName);
        uname = findViewById(R.id.username);
        pwd = findViewById(R.id.password);
//        imgProfile = findViewById(R.id.imgProfile);
        signup = findViewById(R.id.btnLogIn);
        signin = findViewById(R.id.textSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        imgProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BroweImage();
//
//            }
//        });

    }


//     private void BroweImage(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, 0);
//     }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (data == null) {
//                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
//            }
//        }
//        Uri uri = data.getData();
//        imgProfile.setImageURI(uri);
//        imagePath = getRealPathFromUri(uri);
//    }

//    private String getRealPathFromUri(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(getApplicationContext(),
//                uri, projection, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(colIndex);
//        cursor.close();
//        return result;
//    }
//    private void saveImageOnly() {
//        File file = new File(imagePath);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
//                file.getName(), requestBody);
//
//        UserAPI usersAPI = Url.getInstance().create(UserAPI.class);
//        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);
//
//        StrictModeClass.StrictMode();
//        //Synchronous methid
//        try {
//            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
//            imageName = imageResponseResponse.body().getFilename();
//            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    private void Signup() {
        String firstName = fname.getText().toString();
        String lastName = lname.getText().toString();
        String username = uname.getText().toString();
        String password = pwd.getText().toString();

        User users = new User(firstName, lastName, username, password);
        UserAPI userAPI = Url.getInstance().create(UserAPI.class);
        Call<SignUpResponse> signUpCall = userAPI.registerUser(users);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;

                }
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}


