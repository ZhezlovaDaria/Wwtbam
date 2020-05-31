package com.example.laba6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    int n=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //берем позицию вопроса
        n=Integer.parseInt(getIntent().getStringExtra("position"));

        //проверяем использованы ли подсказки (для продолжения)
        if (Q_Full.helps[0])
        {(findViewById(R.id.imageButton)).setEnabled(false);
            ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.call1);}
        else
            {
                (findViewById(R.id.imageButton)).setEnabled(true);
                ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.call);
            }
        if (Q_Full.helps[1])
        {(findViewById(R.id.imageButton2)).setEnabled(false);
            ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.f50_501);}
        else {
            (findViewById(R.id.imageButton2)).setEnabled(true);
            ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.f50_50);
        }
        if (Q_Full.helps[2])
        {(findViewById(R.id.imageButton3)).setEnabled(false);
            ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.help1);}
        else
            {
                (findViewById(R.id.imageButton3)).setEnabled(true);
                ((ImageButton)(findViewById(R.id.imageButton3))).setImageResource(R.drawable.help);
            }
        //записываем вопрос
        SetQA();

    }

    //метод записи вопроса, данных и ответов
    private void SetQA()
    {
        ((TextView)findViewById(R.id.title)).setText("Вы на "+(n+1)+" вопросе из 10");
        ((TextView)findViewById(R.id.q)).setText(Q_Full.GameQue.get(n).GetQ());
        ((Button)findViewById(R.id.a1)).setText(Q_Full.GameQue.get(n).GetAns()[0]);
        ((Button)findViewById(R.id.a2)).setText(Q_Full.GameQue.get(n).GetAns()[1]);
        ((Button)findViewById(R.id.a3)).setText(Q_Full.GameQue.get(n).GetAns()[2]);
        ((Button)findViewById(R.id.a4)).setText(Q_Full.GameQue.get(n).GetAns()[3]);
        ((Button)findViewById(R.id.a1)).setEnabled(true);//вешаем на кнопки тру - вдруг была использована 50 на 50
        ((Button)findViewById(R.id.a2)).setEnabled(true);
        ((Button)findViewById(R.id.a3)).setEnabled(true);
        ((Button)findViewById(R.id.a4)).setEnabled(true);
    }

    //Проверка правильности ответа
    public void Check(View v)
    {
        int k=Q_Full.GameQue.get(n).GetNoA(); //номер правильного ответа
        String a=((Button)v).getText().toString(); //текст с кнопки
        if (a.equals(Q_Full.GameQue.get(n).GetAns()[k-1])) //сравниваем текст правильного ответа с текстом кнопки
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);//если ответ верный, то сообщаем, что все хорошо
            builder.setTitle("Поздравляем!")
                    .setMessage("Вы правильно ответили на вопрос!")
                    .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем окно
                            dialog.cancel();
                        }
                    });
            builder.show();
            n++;//следуюзий ворос
            if (n<Q_Full.GameQue.size()) //не кончились ли вопросы
            SetQA();//вешаем следующий вопрос
            else GameEnd();//если кончились - вызываем завершение с победой
        }
        else
        {
            //произрыш
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Конец!")
                    .setMessage("Вы проиграли!")
                    .setPositiveButton("В главное меню", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем окно
                            dialog.cancel();
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            intent.putExtra("p","true");
                            startActivity(intent);
                            finish();
                        }
                    });
            builder.show();
            Q_Full.q1=-1;//для сброса сохраненной игры


        }

    }
//победа
    private void GameEnd()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Поздравляем!")
                .setMessage("Вы прошли игру!")
                .setPositiveButton("В главное меню", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем окно
                        dialog.cancel();
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        intent.putExtra("p","true");
                        startActivity(intent);
                        finish();
                    }
                });
        builder.show();
        Q_Full.q1=-1;//для сброса сохранненой игры

    }
//подсказка 50 на 50
    public  void Help_50_50(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Пятьдесят на пятьдесят")
                .setMessage("Убраны два неверных варианта ответа")
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
        int k=Q_Full.GameQue.get(n).GetNoA();//узнаем номер верного ответа и убираем парочку других
        if (k==1||k==2)
        {
            ((Button)findViewById(R.id.a3)).setEnabled(false);//делаем кнопки недоступными
            ((Button)findViewById(R.id.a4)).setEnabled(false);
            ((Button)findViewById(R.id.a3)).setText("");//и убираем текст
            ((Button)findViewById(R.id.a4)).setText("");
        }
        else
        {
            ((Button)findViewById(R.id.a1)).setEnabled(false);
            ((Button)findViewById(R.id.a2)).setEnabled(false);
            ((Button)findViewById(R.id.a1)).setText("");
            ((Button)findViewById(R.id.a2)).setText("");
        }
        (findViewById(R.id.imageButton2)).setEnabled(false);//делаем недоступной подсказку
        ((ImageButton)(findViewById(R.id.imageButton2))).setImageResource(R.drawable.f50_501);//отмечаем это другой картинкой
        Q_Full.helps[1]=true;//ставим флаг что подсказка использована
    }
    //подсказка звонок другу
    public  void Help_Friend(View v)
    {
        int k=Q_Full.GameQue.get(n).GetNoA();//узнаем номер верного ответа, текст которого сообщает нам друг в алертдиалоге
        String a=Q_Full.GameQue.get(n).GetAns()[k-1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Звонок другу")
                .setMessage("Ваш друг подсказывает вам, что правильный ответ: "+a)
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
        (findViewById(R.id.imageButton)).setEnabled(false);//делаем недоступной подсказку
        ((ImageButton)(findViewById(R.id.imageButton))).setImageResource(R.drawable.call1);//отмечаем это другой картинкой
        Q_Full.helps[0]=true;//ставим флаг что подсказка использована

    }
    //подсказка помощь зала
    public  void Help_help(View v)
    {
        int k=Q_Full.GameQue.get(n).GetNoA()-1;//узнаем верный ответ
        int[] per=new int[4];
        int[] per1=new int[3];
        per1[0]=25;//наша подставная статистика для неправильных ответов)
        per1[1]=15;
        per1[2]=20;
        int c=0;
        for (int i=0;i<per.length;i++)
        {
            if (i!=k)
            {
                per[i]=per1[c];//подставляем то "голосование" неправильным ответам
                c++;
            }
            else per [i]=40;//и правильному даем 40, сообщаем об это
        }
        String text="Зал проголосовал следующим образом: \n"+" 1: "+per[0]+"%\n 2: "+per[1]+"%\n 3: "+per[2]+"%\n 4: "+per[3]+"%";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Помощь зала")
                .setMessage(text)
                .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
        (findViewById(R.id.imageButton3)).setEnabled(false);//делаем недоступной подсказку
        ((ImageButton)(findViewById(R.id.imageButton3))).setImageResource(R.drawable.help1);//отмечаем это другой картинкой
        Q_Full.helps[2]=true;//ставим флаг что подсказка использована
    }
    //для кнопки выход
    public  void Exit(View v)
    {
        Q_Full.q1=n;//сохраняемм позицию
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.putExtra("p","true");//флаг, что нам нужно сохранить список игровой сессии
        startActivity(intent);
        finish();
    }
}
