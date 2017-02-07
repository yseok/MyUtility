package com.yuseok.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment implements View.OnClickListener {

    // 1. 위젯 변수 선언
    TextView Result, pre;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnMulti, btnDivide, btnC, btnR;

    public OneFragment() {
        // Required empty public constructor
    }

    // View를 담아놓은 Hilder
    View view = null; // Holder의 기능

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Holder의 기능으로 선언된 view에 저장된 값이 null이면 밑에 과정이 진행되고
        // null이 아니면 이미 저장된 값이 불러진다.

        // 강사님 :  뷰를 재사용하기 위해 Holder형태로 만들어준다.
        if(view != null)
            return view;

        // 처음 실행할 때 xml을 inflater로 가져와 객체화 하는 작업이 실행되고
        // return view; 리턴되어 뷰가 이동되면 값이 저장되지않고 사라진다.

        view = inflater.inflate(R.layout.fragment_one, container, false);

        // 2. 실제 위젯 주입
        Result = (TextView) view.findViewById(R.id.Result);
        pre = (TextView) view.findViewById(R.id.pre);
        btn0 = (Button) view.findViewById(R.id.btn0);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);

        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        btn7 = (Button) view.findViewById(R.id.btn7);
        btn8 = (Button) view.findViewById(R.id.btn8);
        btn9 = (Button) view.findViewById(R.id.btn9);

        btnPlus = (Button) view.findViewById(R.id.btnPlus);
        btnMinus = (Button) view.findViewById(R.id.btnminus);
        btnMulti = (Button) view.findViewById(R.id.btnMulti);
        btnDivide = (Button) view.findViewById(R.id.btnDivide);

        btnC = (Button) view.findViewById(R.id.btnC);
        btnR = (Button) view.findViewById(R.id.btnR);

        // 3. 리스너 등록
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDivide.setOnClickListener(this);

        btnC.setOnClickListener(this);
        btnR.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn0:
                addpre("0");
                break;
            case R.id.btn1:
                addpre("1");
                break;
            case R.id.btn2:
                addpre("2");
                break;
            case R.id.btn3:
                addpre("3");
                break;
            case R.id.btn4:
                addpre("4");
                break;
            case R.id.btn5:
                addpre("5");
                break;
            case R.id.btn6:
                addpre("6");
                break;
            case R.id.btn7:
                addpre("7");
                break;
            case R.id.btn8:
                addpre("8");
                break;
            case R.id.btn9:
                addpre("9");
                break;
            case R.id.btnPlus:
                addpre("+");
                break;
            case R.id.btnminus:
                addpre("-");
                break;
            case R.id.btnMulti:
                addpre("*");
                break;
            case R.id.btnDivide:
                addpre("/");
                break;
            case R.id.btnR:
                eval(pre.getText().toString());
                break;
            case R.id.btnC:
                setPreview("");
                setResult("");
                break;
        }
    }



    // 문자열을 수식으로 계산하기
    private void eval(String value){
        String r = "";

        // 1. 문자열을 정규식으로 * / + - 을 이용해서 배열로 자른다
        String splited[] = value.split("(?<=[*/+-])|(?=[*/+-])");

        // 2. 동적배열을 사용하기 위해 ArrayList 담는다.
        //    이유는 연산이 일어나는 값이 연산자를 기준으로 앞뒤로 존재하는데,
        //    연산후에 삭제하기 위해서 동적배열을 사용한다.
        ArrayList<String> list = new ArrayList<>();

        // 3. 처리 중간에 배열을 자유롭게 삭제하기 위해 담는다.
        for(String item : splited)
            list.add(item);

        int index = 0;

        // 4. 연산자 우선순위가 높은 * , / 를 먼저 처리한다.
        //    배열을 돌면서 연산자를 기준으로 값을 꺼낸다
        for( index=0 ; index < list.size() ;){
            // 4.1 item 변수에 값을 담은 후
            String item = list.get(index);

            double one = 0;
            double two = 1;
            double sum = 2;
            boolean check = true;
            // 4.2 값이 곱하기 일경우
            if(item.equals("*")) {
                // 4.2.1 연산자 앞의 숫자를 꺼내고
                one = Double.parseDouble(list.get(index-1));
                // 4.2.2 연산자 뒤의 숫자를 꺼낸다
                two = Double.parseDouble(list.get(index+1));
                // 4.3.3 두 숫자를 곱한다.
                sum = one * two;
                Log.d("CalculatorActivity","check [***] index="+index+", sum="+sum);
                // 곱하기에 걸렸다는 표식을 해준다
                check = true;
                // 4.3 값이 나누기일 경우
            }else if(item.equals("/")){
                // 4.3.1 연산자 앞의 숫자를 꺼내고
                one = Double.parseDouble(list.get(index-1));
                // 4.3.2 연산자 뒤의 숫자를 꺼낸다.
                two = Double.parseDouble(list.get(index+1));
                // 4.3.3 값을 더한다.
                sum = one / two;
                Log.d("CalculatorActivity","check [///] index="+index+", sum="+sum);
                check = true;
                // 4.4 연산자에 걸리지 않으면 체크 플래그를 false 전환해서 반복문을 진행하게 한다.
            }else{
                check = false;
            }

            // 4.5 앞에서 * 또는 / 에 걸리면
            if(check) {
                // 4.5.1 현재 내 연산자 위치에 결과값을 저장하고
                list.set(index, sum + "");
                // 4.5.2 이미 연산된 뒤의 값을 먼저 제거하고
                list.remove(index + 1);
                // 4.5.3 이미 연산된 앞의 값을 제거한다.
                list.remove(index - 1);
                // 4.6 앞에서 체크되지 않았으면 index 만 증가해서 다음 값을 비교한다.
            }else {
                index++;
            }
        }

        Log.d("CalculatorActivity","check [index]="+index);

        index = 0;

        // 5. + - 를 검사한다.
        for( index=0 ; index < list.size() ;){
            String item = list.get(index);
            double one = 0;
            double two = 1;
            double sum = 2;
            boolean check = true;
            if(item.equals("+")) {
                one = Double.parseDouble(list.get(index-1));
                two = Double.parseDouble(list.get(index+1));
                sum = one + two;
                Log.d("CalculatorActivity","check [+++] index="+index+", sum="+sum);
                check = true;
            }else if(item.equals("-")){
                one = Double.parseDouble(list.get(index-1));
                two = Double.parseDouble(list.get(index+1));
                sum = one - two;
                Log.d("CalculatorActivity","check [---] index="+index+", sum="+sum);
                check = true;
            }else{
                check = false;
            }

            if(check) {
                list.set(index, sum + "");
                list.remove(index + 1);
                list.remove(index - 1);
                index--;
            }else {
                index++;
            }
        }

        // 최종적으로 list 의 0번째 값을 꺼내면 결과를 확인할 수 있다.
        setResult("Result : "+list.get(0));
    }

    private void setResult(String string){
        Result.setText(string);
    }

    private void addpre(String string){
        setPreview(pre.getText() + string);
    }

    private void setPreview(String string){
        pre.setText(string);
    }
}
