package com.pjy.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageView com[] = new ImageView[3];
    ImageView user[] = new ImageView[3];
    Button select_S, select_P, select_R;
    Button btn_Start, btn_Exit;

    int count = 0; //그림을 움직이기 위한 변수
    int rot = 0; //그림을 움직이기 위한 변수
    int comRand = 0; //컴퓨터 난수발생

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        com[0] = findViewById(R.id.comR);
        com[1] = findViewById(R.id.comS);
        com[2] = findViewById(R.id.comP);

        user[0] = findViewById(R.id.userS);
        user[1] = findViewById(R.id.userR);
        user[2] = findViewById(R.id.userP);

        btn_Start = findViewById(R.id.btn_Start);
        btn_Exit = findViewById(R.id.btn_Exit);

        select_S = findViewById(R.id.btn_S);
        select_R = findViewById(R.id.btn_R);
        select_P = findViewById(R.id.btn_P);

        btn_Start.setOnClickListener(btn_game);
        btn_Exit.setOnClickListener(btn_game);

        select_S.setOnClickListener(selectButton);
        select_R.setOnClickListener(selectButton);
        select_P.setOnClickListener(selectButton);

    }//onCreate()

    View.OnClickListener btn_game = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           int id = view.getId();

           if ( id == R.id.btn_Start ){
               //핸들러 호출
                handler.sendEmptyMessage(0);
           } else if ( id == R.id.btn_Exit ) {
               finish();
           }
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            moving();
            handler.sendEmptyMessageDelayed(0,100);
        }
    };

    //이미지 움직이게 하는 함수
    void moving(){
        count++;

        //count % 3 -> count를 3으로 나눈 나머지
        //count가 0일때 rot가0이 되어서
        //rot가 0이면 com[0], user[0] 그림을 보여주고
        //rot가 1이면 com[1], user[1] 그림을 보여주게 하기 위해
        rot = count % 3;

        //rot의 값에 해당하는 컴퓨터와 사용자의 그림을 모두 보여주는 역할
        //com[rot], user[rot]가 나오게
        visible(rot, rot);

        if( count == 3 )
            count = 0;

    }//moving()

    //유저와 컴퓨터 이미지의 숨김처리를 하는 메서드
    //c와 u 값을 활용하여 선택된 컴퓨터 이미지와 사용자 이미지를 보이게 처리하고,
    //나머지 이미지들은 모두 숨기는 역할
    void visible(int c, int u){
        com[c].setVisibility(View.VISIBLE);
        user[u].setVisibility(View.VISIBLE);

       for( int i=0; i<com.length; i++ ){
            if( i != c )
                com[i].setVisibility(View.INVISIBLE);

            if( i != u )
                user[i].setVisibility(View.INVISIBLE);
       }
    }//visible()

    View.OnClickListener selectButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //comRand 변수에 0, 1, 2 중 하나의 랜덤한 값을 저장하는 역할
            comRand = new Random().nextInt(3);

            //이전에 실행 중인 핸들러 메시지가 있으면 제거하고, 새로운 메시지를 실행하게함
            handler.removeMessages(0);

            //유저의 결과

            //uResult 변수는 사용자가 선택한 가위, 바위, 보 버튼을 인덱스로 나타내는 역할
            //기본적으로 0으로 초기화되며,
            //사용자가 가위, 바위, 보 버튼 중 하나를 선택하면 그에 맞는 값으로 변경됨
            int uResult = 0;

            //view는 사용자가 클릭한 버튼, 사용자가 가위 버튼을 클릭했다면 uResult에 0을 할당
            //바위 버튼 클릭시 uResult에 1을 할당하고, 보 버튼 클릭시 uResult에 2를 할당
            if(view == select_S)
                uResult = 0;
            else if(view == select_P)
                uResult = 1;
            else
                uResult = 2;

            //visible() 메서드를 호출해, 컴퓨터와 사용자의 이미지를 보이거나 숨기는 역할
            visible(comRand, uResult);
        }
    };

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }*/
}