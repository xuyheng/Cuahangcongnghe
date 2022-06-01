package com.example.cuahangcongnghe.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangcongnghe.R;
import com.example.cuahangcongnghe.adapter.LoaispAdapter;
import com.example.cuahangcongnghe.model.Loaisp;
import com.example.cuahangcongnghe.ultil.CheckConnection;
import com.example.cuahangcongnghe.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisanpham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            GetDuLieuLoaisp();
        } else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối.");
            finish();
        }

//        ActionViewFlipper();
    }
    private void GetDuLieuLoaisp(){
        RequestQueue requestQueue = Volley.newRequestQueue (getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i =0; i< response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisanpham = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisanpham));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Liên Hệ", "https://img.icons8.com/material-two-tone/24/undefined/phone--v1.png"));
                    mangloaisp.add(4,new Loaisp(0,"Thông Tin", "https://img.icons8.com/carbon-copy/100/undefined/dratini.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

//    private void ActionViewFlipper(){
//        ArrayList<String> mangquangcao = new ArrayList<>();
//        mangquangcao.add("banner.jpg");
//        mangquangcao.add("banner1.jpg");
//        mangquangcao.add("https://thietkehaithanh.com/wp-content/uploads/2019/01/THIETKEHAITHANH-HINH2-800x380.jpg");
//        mangquangcao.add("https://haandesign.pro/wp-content/uploads/2021/06/sales-65.jpg");
//        for (int i = 0; i < mangquangcao.size(); i++){
//            ImageView imageView = new ImageView(getApplicationContext());
//            Picasso.get().load(mangquangcao.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(5000);
//        viewFlipper.setAutoStart(true);
//        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext() ,R.anim.slide_in_right);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext() ,R.anim.slide_out_right);
//        viewFlipper.setInAnimation(animation_slide_in);
//        viewFlipper.setOutAnimation(animation_slide_out);
//
//    }

    @SuppressLint("RestrictedApi")
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recycleview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        //đọc dữ liệu trang chủ đầu tiên
        mangloaisp.add(0, new Loaisp(0,"Trang Chủ", "https://img.icons8.com/windows/32/undefined/home-page.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
    }
}