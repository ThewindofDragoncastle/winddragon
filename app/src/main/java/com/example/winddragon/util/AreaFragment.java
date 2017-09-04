package com.example.winddragon.util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winddragon.MainPage;
import com.example.winddragon.MyLog;
import com.example.winddragon.R;
import com.example.winddragon.db.City;
import com.example.winddragon.db.County;
import com.example.winddragon.db.Province;
import com.example.winddragon.weather_Activity;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/8/30.
 */

public class AreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;

    private ProgressDialog progressDialog;
    private TextView textView;
    private Button backbutton;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> data=new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province currentProvince;
    private City currentCity;
    private County currentCounty;
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.choose_area,container,false);
        textView=(TextView)view.findViewById(R.id.title_text);
        backbutton=(Button)view.findViewById(R.id.back);
        listView=(ListView)view.findViewById(R.id.list_view);
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
             if (currentLevel==LEVEL_PROVINCE)
             {
                 currentProvince=provinceList.get(position);
                 queryCities();

             }
             else if(currentLevel==LEVEL_CITY)
             {
                 currentCity=cityList.get(position);
                 queryCounties();
             }
             else if(currentLevel==LEVEL_COUNTY)
             {
                 String weatherId = countyList.get(position).getCountyCode();
                 if(getActivity() instanceof MainPage) {
                     Intent intent = new Intent(getActivity(), weather_Activity.class);
                     intent.putExtra("weather_id", weatherId);
                     MyLog.i("weather_activity:", weatherId);
                     startActivity(intent);
                     getActivity().finish();
                 }
                 else if(getActivity() instanceof weather_Activity)
                 {
                   weather_Activity activity=(weather_Activity)getActivity();
                     activity.drawerLayout.closeDrawers();
                     activity.swipeRefreshLayout.setRefreshing(true);
                     activity.requestweather(weatherId);
                 }
             }

         }
     });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_CITY)
                {
                    queryProvinces();
                }
                else if (currentLevel==LEVEL_COUNTY)
                {
                    queryCities();
                }
            }
        });
        queryProvinces();
    }
    private void queryProvinces()
    {
        textView.setText("中国China");
        backbutton.setVisibility(backbutton.GONE);
        provinceList= DataSupport.findAll(Province.class);
        if(provinceList.size()>0)
        {
            data.clear();
            for(Province province:provinceList)
            {
                data.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
            MyLog.i("ArealFragment:","数据库查询。"+"当前等级："+ currentLevel);
        }
        else
        {
            String address="http://guolin.tech/api/china";
            MyLog.i("ArealFragment:","网络查询。"+"当前等级："+ currentLevel+"地址"+address
                    );
            queryFromServer(address,"province");
        }
    }
    private void queryCities()
    {
        textView.setText(currentProvince.getProvinceName()+" China");
        backbutton.setVisibility(View.VISIBLE);
        cityList=DataSupport.where("ProvinceId=?",String.valueOf(currentProvince.getId())).find(City.class);
   if (cityList.size()>0)
   {
       data.clear();
       for (City city:cityList)
       {
           data.add(city.getCityName());
       }
       arrayAdapter.notifyDataSetChanged();
       listView.setSelection(0);
       currentLevel=LEVEL_CITY;
       MyLog.i("ArealFragment:","数据库查询。"+"当前等级："+ currentLevel);
   }
   else
   {
       String address="http://guolin.tech/api/china/"+currentProvince.getProvinceCode();
       MyLog.i("ArealFragment:","网络查询。"+"当前等级："+ currentLevel+
               "地址："+address);
       queryFromServer(address,"city");
   }
    }
    private void queryCounties()
    {
        textView.setText(currentCity.getCityName()+" China");
        backbutton.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("CityId=?",String.valueOf(currentCity.getId())).find(County.class);
        if (countyList.size()>0)
        {
            data.clear();
            for (County county:countyList)
            {
                data.add(county.getCountyName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            MyLog.i("ArealFragment:","数据库查询。"+"当前等级："+ currentLevel);
            currentLevel=LEVEL_COUNTY;
        }
        else
        {
            String address="http://guolin.tech/api/china/"+currentProvince.getProvinceCode()+ File.separator+currentCity.getCityCode();
            MyLog.i("ArealFragment:","网络查询。"+"当前等级："+ currentLevel);
            queryFromServer(address,"county");
        }
    }
    private void queryFromServer(String adress, final String currenttarget)
    {
       showProDog();
        HttpUtil.sendRequest(adress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProdog();
                        Toast.makeText(getContext(),"很抱歉，连接服务器失败。请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String text=response.body().string();
                Boolean result=false;
                if("province".equals(currenttarget))
                {
                    result=Utility.handleProvinces(text);
                }
                else if ("city".equals(currenttarget))
                {
                    result=Utility.handleCitys(text,currentProvince.getId());
                }
                else if("county".equals(currenttarget))
                {
                    result=Utility.handleCounty(text,currentCity.getId());
                }
                if (result)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProdog();
                            if("province".equals(currenttarget))
                            {
                                queryProvinces();
                            }
                            else if ("city".equals(currenttarget))
                            {
                                queryCities();
                            }
                            else if("county".equals(currenttarget))
                            {
                                queryCounties();
                            }
                        }
                    });

                }
            }
        });
    }
    private void showProDog()
    {
        if(progressDialog==null)
        {
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProdog()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }
}
