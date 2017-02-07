package com.yuseok.android.myutility;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment implements View.OnClickListener{


    Button btnLength, btnArea, btnWeight;
    LinearLayout layoutLength, layoutArea, layoutWeight;
    EditText et;


    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two, container, false);

        // Toast.makeText(this, "EditText: " + et.getText().toString(), Toast.LENGTH_SHORT).show();

        // EditText가져오기
        // Edit Text.getText().toString();
        et = (EditText) view.findViewById(R.id.editText3);

       /* // EditText사용안되게
        et.setEnabled(false); // < 모든 위젯
        et.setKeyListener(null); // < EditText만
        */

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {   }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        // 1. 위젯 가져오기
        btnLength = (Button) view.findViewById(R.id.btnLength);
        btnArea= (Button) view.findViewById(R.id.btnArea);
        btnWeight = (Button) view.findViewById(R.id.btnWeight);

        layoutLength = (LinearLayout) view.findViewById(R.id.layoutLength);
        layoutArea = (LinearLayout) view.findViewById(R.id.layoutArea);
        layoutWeight= (LinearLayout) view.findViewById(R.id.layoutWeight);

        // 2. 버튼 리스너 (위에 생서한) 등록
        btnLength.setOnClickListener(this);
        btnArea.setOnClickListener(this);
        btnWeight.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // 클릭이 일어나면 레이아웃을 모두 지운다
        layoutLength.setVisibility(View.GONE);
        layoutArea.setVisibility(View.GONE);
        layoutWeight.setVisibility(View.GONE);

        // 클릭된 버튼에 해당하는 레이아웃만 보여준다
        switch (v.getId()) {
            case R.id.btnLength:
                layoutLength.setVisibility(View.VISIBLE);
                break;
            case R.id.btnArea:
                layoutArea.setVisibility(View.VISIBLE);
                break;
            case R.id.btnWeight:
                layoutWeight.setVisibility(View.VISIBLE);
                break;
        }
    }
//    private void setLayout(LinearLayout target){
//
//    }
}
