package com.example.whitebooks.lianxin_android.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whitebooks.lianxin_android.R;
import com.example.whitebooks.lianxin_android.utilclass.MyApplication;
import com.example.whitebooks.lianxin_android.utilclass.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by whitebooks on 17/2/10.
 */

public class Update_Userinfo_Frag extends Fragment {
    private View view;
    private TextView editAvater;
    private CircleImageView avater;
    private TextView oldUsername;
    private EditText newUsername;
    private EditText phoneNumber;
    private TextView departmentName;
    private Spinner department;
    private Button save;
    private Button cancel;
    private String newName;
    private String newPhoneNumber;
    private String selectDepartment;
    private static final  int CHOOSE_FHOTO = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.update_user_ifo_frag,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        editAvater =(TextView) view.findViewById(R.id.edit_avater_textview);
        avater = (CircleImageView) view.findViewById(R.id.user_info_avater);
        oldUsername = (TextView)view.findViewById(R.id.old_username);
        newUsername = (EditText)view.findViewById(R.id.new_user_name);
        phoneNumber = (EditText)view.findViewById(R.id.edit_mobile_number);
        departmentName = (TextView)view.findViewById(R.id.departmentname);
        department = (Spinner)view.findViewById(R.id.edit_department);
        save = (Button)view.findViewById(R.id.edit_ok_button);
        cancel = (Button)view.findViewById(R.id.edit_cancel_button);

        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        oldUsername.setText(myUser.getUsername());
        phoneNumber.setText(myUser.getMobilePhoneNumber());
        departmentName.setText(myUser.getDepartment());
        newUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               newName = s.toString();
            }
        });
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPhoneNumber = s.toString();
            }
        });
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectDepartment = (String)department.getSelectedItem();
                if (selectDepartment == "点击选取"){

                }else {
                    departmentName.setText(selectDepartment);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (newName != null){
                myUser.setUsername(newName);}
                if (newPhoneNumber != null && newPhoneNumber != myUser.getMobilePhoneNumber()){
                myUser.setMobilePhoneNumber(newPhoneNumber);}
                if (selectDepartment != null && selectDepartment != "点击选取"){
                myUser.setDepartment(departmentName.getText().toString());}
                myUser.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            Toast.makeText(MyApplication.getContext(),"更新成功",Toast.LENGTH_SHORT).show();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main, new NewsFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else {
                            Toast.makeText(MyApplication.getContext(),"更新失败,请确认更新信息的准确性",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main, new NewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        editAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent ,CHOOSE_FHOTO);

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_FHOTO:
                if (Build.VERSION.SDK_INT >= 19){
                     handleImageOnkitKat(data);
                }else {
                     handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    public void handleImageOnkitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(),uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();
        }
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("image", Context.MODE_PRIVATE).edit();
        editor.putString("path",imagePath);
        editor.apply();
        displayImage(imagePath);

    }
    public void handleImageBeforeKitKat(Intent data){
        Uri uri =data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);

    }

    public String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void displayImage(String imagePath){
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            avater.setImageBitmap(bitmap);
        }else{
            Toast.makeText(MyApplication.getContext(),"无法获得照片",Toast.LENGTH_SHORT).show();
        }
    }
}
