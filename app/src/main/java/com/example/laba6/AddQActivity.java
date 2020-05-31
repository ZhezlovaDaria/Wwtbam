package com.example.laba6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQActivity extends AppCompatActivity {


    String[] prior = {"Первый", "Второй", "Третий", "Четвертый"};
    int pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q);


        // адаптер для выпадающего списка номера верного ответа
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prior);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Верный ответ");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позицию ответа
                pr=position+1;
                Toast.makeText(getBaseContext(), "Верный ответ №" + pr, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
//сохраняем вопрос
    public void save(View v) {
        Question Q=new Question();
        Q.SetQ(((EditText) findViewById(R.id.EnQ)).getText().toString());//выцепляем вопрос, вешаем в созданный "вопрос"
        String[] Ans=new String[4];
        Ans[0]= ((EditText) findViewById(R.id.EnA1)).getText().toString();//выстаскиваем ответ
        Ans[1]= ((EditText) findViewById(R.id.EnA2)).getText().toString();
        Ans[2]= ((EditText) findViewById(R.id.EnA3)).getText().toString();
        Ans[3]= ((EditText) findViewById(R.id.EnA4)).getText().toString();
        Q.SetAns(Ans);//вешаем их в "вопрос" тоже
        Q.SetNoA(pr);//как и номер ответа
        Q_Full.QueY.add(Q);//добавляем это в список вопросов пользователя
        Intent intent = new Intent(AddQActivity.this, MainActivity.class);
        intent.putExtra("p","false");//флаг, что надо сохранить общий список
        startActivity(intent);
        finish();
    }
}
