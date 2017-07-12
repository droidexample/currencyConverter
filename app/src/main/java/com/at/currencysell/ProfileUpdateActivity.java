package com.at.currencysell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.utils.AlbumStorageDirFactory;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseAlbumDirFactory;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.FroyoAlbumDirFactory;
import com.at.currencysell.utils.MultipartUtility;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;
import com.at.currencysell.utils.StorageUtils;
import com.at.currencysell.utils.WebUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {
    private LinearLayout ll_back_sign_up;
    Context mContext;

    private ImageView addPhoto;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int TAKE_PICTURE = 2;
    private String Apath = "1";
    private File file_image = null;
    private static String JPEG_FILE_PREFIX = "";
    private static final String JPEG_FILE_SUFFIX = ".png";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private EditText et_first_name;
    private EditText et_last_name;
    private EditText et_email;

    private String firstname;
    private String lastname;
    private String email;
    private String user_id;

    private LinearLayout ll_update;
    BusyDialog mBusyDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        mContext = this;

        initUI();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        if (!StorageUtils.isSDCardPresent()) {
            AlertMessage.showMessage(mContext, "Low memory size", "Your mobile have low memory.Please increase some .");
            return;
        }
        if (!StorageUtils.isSdCardWrittenable()) {
            AlertMessage.showMessage(mContext, "Low memory size", "Your mobile have low memory.Please increase some .");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    private void initUI(){
        ll_back_sign_up = (LinearLayout)this.findViewById(R.id.ll_back_sign_up);
        ll_back_sign_up.setOnClickListener(listener);
        addPhoto = (ImageView) findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(listener);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email);

        ll_update = (LinearLayout)this.findViewById(R.id.ll_update);
        ll_update.setOnClickListener(listener);

        user_id = PersistentUser.getUserID(mContext);

        try {
            JSONObject JsonUser = new JSONObject(PersistentUser.getUSERDATA(mContext));
            et_first_name.setText("" + JsonUser.getString("first_name"));
            et_last_name.setText("" + JsonUser.getString("last_name"));
            et_email.setText("" + JsonUser.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.ll_back_sign_up:
                    ProfileUpdateActivity.this.finish();
                    break;
                case R.id.add_photo:
                    alertshow();
                    break;
                case R.id.ll_update:
                    updateProfile();
                    break;

            }

        }
    };

    public void updateProfile() {


        firstname = et_first_name.getText().toString();
        lastname = et_last_name.getText().toString();
        email = et_email.getText().toString();


        if (Apath.equalsIgnoreCase("1")) {
            Toast.makeText(mContext, "Please  select an image", Toast.LENGTH_LONG).show();
            return;
        } else if (firstname.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your First Name", Toast.LENGTH_LONG).show();
            return;
        } else if (lastname.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your Last Name", Toast.LENGTH_LONG).show();
            return;
        }  else {

            //new UploadFileToServer().execute();
           doWebRequestForsignUp(firstname,lastname,email,user_id);


        }
    }
/*
    private class UploadFileToServer extends AsyncTask<Object, String, Object> {

        String response = "";


        @Override
        protected void onPreExecute() {
            // setting progress bar to zero


            if (!NetInfo.isOnline(mContext)) {
                AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
                return;
            }

            mBusyDialog = new BusyDialog(mContext, true, "Loading");
            mBusyDialog.show();

            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            try {

                String ulr = BaseUrl.HttpUrl + "registration";
                MultipartUtility body = new MultipartUtility(ulr);
                body.addFormField("first_name", "" + firstname);
                body.addFormField("last_name", "" + lastname);
                body.addFormField("email", "" + email);
                body.addFormField("password", "" + password);
                body.addFilePart("picture", Apath);
                body.addFilePart("api_key", BaseUrl.Api_key);
                body.addFormField("login_type", "" + login_type);
                body.addFormField("social_status", "" + social_status);
                response = body.finish();


                mBusyDialog.dismis();
            } catch (Exception e) {
                Log.w("Exception", e.getMessage());
                // TODO Auto-generated catch block
                mBusyDialog.dismis();

                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            mBusyDialog.dismis();

            Log.w("response", "are" + response);

            try {
                JSONObject JSONresponse = new JSONObject(response);

                int success = JSONresponse.getInt("success");
                if (success == 1) {
                    JSONObject userData = JSONresponse.getJSONObject("result");
                    PersistentUser.setUSERNAME(mContext, userData.getString("first_name"));
                    PersistentUser.setUserEmail(mContext, userData.getString("email"));
                    PersistentUser.setUSERPIC(mContext, userData.getString("picture"));
                    PersistentUser.setUserID(mContext, userData.getString("user_id"));
                    PersistentUser.setLogin(mContext);

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    ProfileUpdateActivity.this.finish();

                    Toast.makeText(mContext, "" + "Registered successfully", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(mContext, "" + JSONresponse.getInt("message"), Toast.LENGTH_LONG).show();

                }

            } catch (JSONException sd) {
                Toast.makeText(mContext, sd.toString(), Toast.LENGTH_LONG).show();
            }


        }

    }*/

    public void doWebRequestForsignUp(final String first,final String last,final String mail,final String user_id) {

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "update-profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mBusyDialog.dismis();
                Log.w("response", "are" + response);
                try {
                    JSONObject JSONresponse = new JSONObject(response);

                    int success = JSONresponse.getInt("success");
                    if (success == 1) {
                        JSONObject userData = JSONresponse.getJSONObject("result");
                        PersistentUser.setUSERNAME(mContext, userData.getString("first_name"));
                        PersistentUser.setUserEmail(mContext, userData.getString("email"));
                        PersistentUser.setUSERPIC(mContext, userData.getString("picture"));
                        PersistentUser.setUserID(mContext, userData.getString("user_id"));
                        PersistentUser.setLogin(mContext);

                        Intent intent = new Intent(mContext, HomeActivity.class);
                        startActivity(intent);
                        ProfileUpdateActivity.this.finish();


                        Toast.makeText(mContext, "" + JSONresponse.getString("message"), Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(mContext, "" + JSONresponse.getInt("message"), Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException sd) {
                    Toast.makeText(mContext, sd.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBusyDialog.dismis();
                Log.w("response", "are" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_key", BaseUrl.Api_key);
                params.put("first_name", first);
                params.put("last_name", last);
                params.put("email", mail);
                params.put("user_id",user_id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


    public void alertshow() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Choose Option").setCancelable(true).setPositiveButton("Take a photo",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Camera();

                    }

                }).setNegativeButton("From Gallery",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        Gallery();

                    }

                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void Gallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void Camera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                file_image = createImageFile();
            } catch (IOException ex) {

            }
            if (file_image != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file_image));
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }

    }

    private File createImageFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        final String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        final File albumF = getAlbumDir();
        final File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.e("directory", "failed to create directory");
                        return null;
                    }
                }
            }
        } else {
            Log.e(getString(R.string.app_name),
                    "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Apath = file_image.getAbsolutePath();
                    Log.w("ApathA", "" + Apath);
                    File imgFile = new File(Apath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        addPhoto.setImageBitmap(myBitmap);

                    }


                }
                break;
            case RESULT_LOAD_IMAGE:

                if (resultCode == Activity.RESULT_OK && null != data) {
                    Uri selectedImageUri = data.getData();
                    File file = new File(getRealPathFromURI(selectedImageUri));
                    Apath = file.getAbsolutePath();
                    Log.w("ApathB", "" + Apath);
                    File imgFile = new File(Apath);
                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        addPhoto.setImageBitmap(myBitmap);

                    }

                    break;

                } else {
                    Toast.makeText(ProfileUpdateActivity.this, "Error", Toast.LENGTH_LONG).show();
                }

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }
}
