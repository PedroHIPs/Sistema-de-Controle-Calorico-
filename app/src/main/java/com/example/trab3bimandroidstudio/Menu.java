package com.example.trab3bimandroidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    private  double x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=ev.getX();
                y1=ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=ev.getX();
                y2=ev.getY();
                double t = (y1 - y2);
                double r = (x1 - x2);
                if(t<-500){
                    Intent inte = new Intent(Menu.this,MainActivity.class);
                    finish();
                    startActivity(inte);
                }
                if(t>500){
                    Intent inte = new Intent(Menu.this,ConsumoCRUD.class);
                    finish();
                    startActivity(inte);
                }
                if((r>250)&&(t<500)&&(t>-500)){
                    Intent inte = new Intent(Menu.this,Relatorio.class);
                    finish();
                    startActivity(inte);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
