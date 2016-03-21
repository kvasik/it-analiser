package com.example.vlad.analyse;

        import android.app.Activity;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class SecondActivity extends Activity implements View.OnClickListener {
    Button btn[] = new Button[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        btn[0] = (Button)(findViewById(R.id.btn1));
        btn[0].setOnClickListener(this);
        btn[1] = (Button)(findViewById(R.id.btn2));
        btn[1].setOnClickListener(this);
        btn[2] = (Button)(findViewById(R.id.btn3));
        btn[2].setOnClickListener(this);
        btn[3] = (Button)(findViewById(R.id.btn4));
        btn[3].setOnClickListener(this);
        btn[4] = (Button)(findViewById(R.id.btn5));
        btn[4].setOnClickListener(this);
        btn[5] = (Button)(findViewById(R.id.btn6));
        btn[5].setOnClickListener(this);
        btn[6] = (Button)(findViewById(R.id.btn7));
        btn[6].setOnClickListener(this);
        btn[7] = (Button)(findViewById(R.id.btn8));
        btn[7].setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn1:
                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Google_(компания)"));
                startActivity(browserIntent1);
                break;
            case R.id.btn2:
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Твиттер"));
                startActivity(browserIntent2);
                break;
            case R.id.btn3:
                Intent browserIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Facebook"));
                startActivity(browserIntent3);
                break;
            case R.id.btn4:
                Intent browserIntent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Apple"));
                startActivity(browserIntent4);
                break;
            case R.id.btn5:
                Intent browserIntent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/EBay"));
                startActivity(browserIntent5);
                break;
            case R.id.btn6:
                Intent browserIntent6 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Oracle"));
                startActivity(browserIntent6);
                break;
            case R.id.btn7:
                Intent browserIntent7 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ru.wikipedia.org/wiki/Nvidia"));
                startActivity(browserIntent7);
                break;
            case R.id.btn8:
                Intent browserIntent8 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Yandex"));
                startActivity(browserIntent8);
                break;
    }
}
}
