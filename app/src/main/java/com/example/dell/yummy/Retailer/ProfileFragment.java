package com.example.dell.yummy.Retailer;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.yummy.Constants;
import com.example.dell.yummy.DataSingleton;
import com.example.dell.yummy.MainActivity;
import com.example.dell.yummy.Manifest;
import com.example.dell.yummy.R;
import com.example.dell.yummy.model.RetailerDetails;
import com.example.dell.yummy.webservice.RetrofitNetworksCalls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final int CAMERA_PIC_REQUEST = 101;
    private TextView mBack;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mPocName;
    private EditText mMobile;
    private EditText mEmail;
    private ImageView mCamera;
    private ImageView mStoreIcon;
    private Bitmap mStoreImage = null;
    private RetailerDetails mRetailerDetails;
    private EditText mStoreId;
    private Button submit;
    private ProgressDialog mProgressDialog;
    private IRetailerFragmentListener listener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.NOTIFY_PROFILE_DETAILS_UPDATE:
                        dismissProgress();
                        break;
                    case Constants.NOTIFY_PROFILE_DETAILS_ERROR:
                        break;
                }
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mBack = view.findViewById(R.id.profile_back);
        mUserName = view.findViewById(R.id.profile_username);
        mPassword = view.findViewById(R.id.profile_password);
        mCamera = view.findViewById(R.id.profile_camera);
        mStoreIcon = view.findViewById(R.id.profile_image_view);
        mEmail = view.findViewById(R.id.profile_email);
        mPocName = view.findViewById(R.id.profile_poc_name);
        mMobile = view.findViewById(R.id.profile_mobile_number);
        mStoreId = view.findViewById(R.id.profile_store_id);
        submit = view.findViewById(R.id.submit_profile_details);
        mProgressDialog = new ProgressDialog(getActivity());
        RetrofitNetworksCalls calls = DataSingleton
                .getInstance().getRetrofitNetworksCallsObject();
        if (calls != null) {
            mRetailerDetails = calls.getmRetailerDetails();
            if (mRetailerDetails != null) {
                mUserName.setText(mRetailerDetails.getRetailName());
                mPocName.setText(mRetailerDetails.getRetailPoc());
                mMobile.setText(mRetailerDetails.getRetailPhone());
                mEmail.setText(mRetailerDetails.getRetailEmail());
                mPassword.setText("" + mRetailerDetails.getLoginPin());
                mStoreId.setText("" + mRetailerDetails.getRetailId());
                mStoreId.setKeyListener(null);
            }
        }

        IntentFilter intentFilter = new IntentFilter(Constants.NOTIFY_PROFILE_DETAILS_UPDATE);
        intentFilter.addAction(Constants.NOTIFY_PROFILE_DETAILS_ERROR);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(broadcastReceiver, intentFilter);

        mCamera.setOnClickListener(this);
        submit.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_camera:
                selectImage();
                break;
            case R.id.submit_profile_details:
                submitProfileDetails();
                break;
            case R.id.profile_back:
                if (listener != null) {
                    listener.onBackPress();
                }
                break;
        }
    }

    private void submitProfileDetails() {
        boolean updateStatus = false;
        String strPassword = mPassword.getText().toString().trim();
        String mobile = mMobile.getText().toString().trim();
        String pocName = mPocName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        byte[] byteArray = null;
        if (mStoreImage != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mStoreImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byteArray = stream.toByteArray();
            mRetailerDetails.setStoreIcon(byteArray);
            updateStatus = true;
        }
        if (TextUtils.isEmpty(strPassword)) {
            mPassword.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(pocName)) {
            mPocName.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            mMobile.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(Constants.FIELD_EMPTY_WARNING);
            return;
        }

        if (!strPassword.equals(mRetailerDetails.getLoginPin())) {
            mRetailerDetails.setLoginPin(Integer.parseInt(strPassword));
            updateStatus = true;
        }
        if (!mobile.equals(mRetailerDetails.getRetailPhone())) {
            mRetailerDetails.setRetailPhone(mobile);
            updateStatus = true;
        }
        if (!pocName.equals(mRetailerDetails.getRetailPoc())) {
            mRetailerDetails.setRetailPoc(pocName);
            updateStatus = true;
        }
        if (!email.equals(mRetailerDetails.getRetailEmail())) {
            mRetailerDetails.setRetailEmail(email);
            updateStatus = true;
        }
        RetrofitNetworksCalls calls = DataSingleton.getInstance()
                .getRetrofitNetworksCallsObject();
        if (calls != null && updateStatus) {
            calls.updateProfileDetails(getActivity(),mRetailerDetails);
            mProgressDialog.setMessage("Please wait.....");
            mProgressDialog.show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mStoreImage = null;
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent();
                    // Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    // Always show the chooser (if there are multiple options available)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            mStoreImage = (Bitmap) data.getExtras().get("data");
            if (mStoreImage != null) {

                mStoreIcon.setImageBitmap(mStoreImage);
            }

        } else if (requestCode == 2 && data != null) {

            Uri uri = data.getData();

            try {
                mStoreImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mStoreImage != null) {

                mStoreIcon.setImageBitmap(mStoreImage);
            }

        }

    }

    private void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void addListener(IRetailerFragmentListener listener) {
        this.listener = listener;
    }
}
