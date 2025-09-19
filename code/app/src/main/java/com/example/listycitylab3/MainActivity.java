package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements AddCityFragment.AddCityDialogListener,
        EditCityFragment.EditCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    // this is to add a city that is typed to the data set
    // used in AddCityFragment.java
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city, String cityName, String provinceName) {
        city.setName(cityName);
        city.setProvince(provinceName);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default city and province values
        String[] cities = {"Edmonton", "Vancouver", "Montreal"};
        String[] provinces = {"AB", "BC", "QC"};

        //adding the cities and provinces to dataList
        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        // city_list exists in activity_main.xml
        // this is the list of cities/provinces that shows up under the header
        // and before the the footer
        cityList = findViewById(R.id.city_list);
        // converting the array of cities to be copied into the list of cities
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // this is where the the floating action button (fab) connects to the AddCityFragment
        // and therefore create the dialogue pop-up
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                new AddCityFragment().show(getSupportFragmentManager(), "Add City");
            }
        });

        // repeat above for clicking on a city from the list
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long cityId) {
                City city = (City) parent.getItemAtPosition(position);
                EditCityFragment fragment = new EditCityFragment();
                fragment.getCity(city);
                fragment.show(getSupportFragmentManager(), "Edit City");
            }
        });
    }
}