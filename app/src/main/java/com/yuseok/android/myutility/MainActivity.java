package com.yuseok.android.myutility;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/*
 * GPS사용 순서
  * 1. Manifest에 FINE,COARSE 권한 추가
  * 2. Runtime Permission 소스코드 추가
  * 3. GPS Location Manager 정의
  * 4. GPS가 켜져있는지 확인, 꺼져있다면 GPS화면으로 이동
  *
  * 5. Listener 등록
  * 6. Listener 실행
  * 7. Listener 해제
 */

public class MainActivity extends AppCompatActivity {

    // 탭 및 페이저 속성 정의
    final int TAB_COUNT = 4;
    OneFragment one;
    TwoFragment two;
    ThreeFragment three;
    FourFragment four;

    // 위치정보 관리자
    private LocationManager manager;
    public LocationManager getLocationManager() {
        return manager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // 메서드 추적 시작
        Debug.startMethodTracing("trace_result");

        // 프래그먼트 init
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();

        // 탭 layout정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        // 탭 생성 및 타이틀 입력
        tabLayout.addTab(tabLayout.newTab().setText("계산기")); // 가져온 위젯에서 탭 형태를 생산
        tabLayout.addTab(tabLayout.newTab().setText("단위 환산"));
        tabLayout.addTab(tabLayout.newTab().setText("검색"));
        tabLayout.addTab(tabLayout.newTab().setText("현재 위치"));

        // 프래그먼트 페이저 작성
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Adapter생성
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 1. 페이저 리스너 - 페이저가 변경되었을때 탭을 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 2. 탭이 변경되었을때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }
        //메서드 추적 종료
        Debug.stopMethodTracing();
    }


    // PagerAdapter정의
    class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = one;
                    break;
                case 1:
                    fragment = two;
                    break;
                case 2:
                    fragment = three;
                    break;
                case 3:
                    fragment = four;
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

    // request Code부여
    private final int REQ_CODE = 100;

    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 애너테이션
    private void checkPermission() {
        // 1.1 런타임 권한체크
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED

                ) {
            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION};
            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);
        } else {
            init();
        }

    }

    // 2. 권한체크 후 콜백 < 사용자가 확인후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            // 2.1 배열에 넘긴 런타임권한을 체크해서 승인이 됬으면
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 2.2 프로그램 실행
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택 : 1 종료, 2 권한체크 다시 물어보기
                finish();
            }
        }
    }


    private void init() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // GPS센서가 켜져있는지 확인
        // 꺼져있다면 GPS를 켜는 페이지로 이동
        if (!gpsCheck()) {
            // 팝업창 만들기
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // 1. 팝업창 제목
            alertDialog.setTitle("GPS켜기");
            // 2. 팝업창 메시지
            alertDialog.setMessage("GPS가 꺼져있습니다. \n 설정창으로 이동하시겠습니가?");
            // OK를 누르게 되면 설정창으로 이동합니다.
            // 3. YES버튼 생성
            alertDialog.setPositiveButton("YSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(intent);
                }
            });
            // Cancle 하면 종료 합니다.
            // 4. No 버튼 생성
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // 5. show 함수로 팝업창을 화면에 띄운다.
            alertDialog.show();
        }
    }

    // GPS가 꺼져있는지
    private boolean gpsCheck() {
        // 롤리팝 이상버전에서는 LocationManager로 GPS꺼짐 여부 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 롤리팝 이하 버전에서는 LOCATION_PROVIDERs_ALLOWD로 체크
        }else {
            String gps = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(gps.matches(".*gps*")) {
                return true;
            } else {
                return false;
            }
        }
    }
}