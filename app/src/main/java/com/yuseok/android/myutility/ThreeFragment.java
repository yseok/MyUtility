package com.yuseok.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_three, container, false);

        // 로직
        // 1. 사용할 위젯을 가져온다.
        WebView webView = (WebView) view.findViewById(R.id.webView);


        // 2. script 사용 설정 (필수)
        webView.getSettings().setJavaScriptEnabled(true);
        // 줌사용
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);

        // 3. 웹뷰 클라이언트를 지정... (안하면 내장 웹브라우저가 팝업된다.)
        webView.setWebViewClient(new WebViewClient());
        // 3.1 https등을 처리하기 위한 핸들러~ :둘다 세팅할것 : 프로토콜에 따라 클라이언트가 선택되는것으로 파악됨...
        webView.setWebChromeClient(new WebChromeClient());

        // 최초 로드시 google.com 이동
        webView.loadUrl("http://google.com");

        return  view;
    }
}
