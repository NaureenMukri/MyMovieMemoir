package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MemoirMap extends Fragment {

    String personid;

    public MemoirMap() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_memoir_map, container, false);
        Intent intent = getActivity().getIntent();
        personid = intent.getStringExtra("personid");

        Button mapButton = view.findViewById(R.id.btnMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("personid", personid);
                startActivity(intent);
            }
        });
        return view;
    }
}
