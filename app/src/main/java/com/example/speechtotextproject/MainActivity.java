package com.example.speechtotextproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener
{
    EditText et;
    Button b1,b2;
    TextView tv;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        tv=findViewById(R.id.tv);

        tts=new TextToSpeech(getApplicationContext(),this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String msg=et.getText().toString();
                if(msg.equals(""))
                {
                    tts.speak("PLEASE ENTER YOUR MESSAGE",TextToSpeech.QUEUE_ADD,null,null);
                }
                else
                {
                    tts.speak(msg,TextToSpeech.QUEUE_ADD,null,null);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(ii,121);//it is used to leave current screen temporary and goto another api to take result and back to current activity
            }
        });

    }



    @Override
    public void onInit(int status)
    {


    }


    @Override
    public void onActivityResult(int reqCode,int result,Intent data)
    {
        if(result==RESULT_OK && reqCode==121)
        {
            ArrayList<String> msg=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            int i;
            String text="";
            for(i=0;i<msg.size();i++)
            {
                text=text+msg.get(i)+"\n";
            }
            tv.setText(text);

            if(text.indexOf("open browser")!=-1)
            {
                Intent browInt=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browInt);
            }


            else if(text.indexOf("open YouTube")!=-1)
            {
                Intent browInt=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
                startActivity(browInt);
            }

            else if(text.indexOf("call Priyanka")!=-1)
            {
                Intent browInt=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"7984383459"));
                startActivity(browInt);
            }
        }
    }
}