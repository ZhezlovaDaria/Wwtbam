package com.example.laba6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            String s=getIntent().getStringExtra("p");//если мы вышли из игровой сессии и хотим сохранить
            if (s.equals("true")){
            saveListGame();}
            else {saveList();}//вышли из создания вопроса и надо сохранить общий список вопросов
        }
        catch (Exception e)
        {
            e.printStackTrace();//так как при запуске нет передаваемых параметров, то ловим исключение
        }
        openList();//открываем список вопросов пользователей
    }
    //начало новой игры
    public void GameStart(View v)
    {
        Q_Full.q1=0;//первая позиция
        Q_Full.helps=new boolean[3];//сброс подсказок
        Q_Full.GameQue= Q_Full.getRandomElements(Q_Full.Que);//10 случайных вопросов
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("position","0");//передача позиции
        startActivity(intent);
        finish();
    }
    //продолжить игру
    public void GameCont(View v)
    {

        openListGame();//открываем сохраненную игровую сессию
        if (Q_Full.q1==-1)//а если её нет, то говорим пользователю, что надо бы начать новую

        {    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ошибка!")
                .setMessage("Нет сохраненной игры, начните новую")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем окно
                        dialog.cancel();
                    }
                });
        builder.show();}
        else {//если сохранненая игра есть, перенаправляем
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("position",String.valueOf(Q_Full.q1));
        startActivity(intent);
        finish();}
    }
    //переходим на экран добавления вопроса
    public void AddQ(View v)
    {
        Intent intent = new Intent(MainActivity.this, AddQActivity.class);
        startActivity(intent);
    }

    //сериализация списка добавленных вопросов.
    private void saveList()
    {
        FileOutputStream fos;
        try {//открываем в поток файл для записи
            fos = openFileOutput("allq1.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Q_Full.QueY);//записываем в файл и закрываем его
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //десериализация списка добавленных вопросов.
    private void openList()
    {
        try{//открываем в поток наш файл
            Q_Full.Fill();
            FileInputStream fis = openFileInput("allq1.txt");
            if (fis!=null) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Question> task1 = (ArrayList<Question>) ois.readObject();
                Q_Full.QueY=task1;
                for(int i=0; i<task1.size();i++)
                {
                    Q_Full.Que.add(Q_Full.QueY.get(i));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    //сериализация списка вопросов текущей игры.
    private void saveListGame()
    {
        FileOutputStream fos;
        try {//открываем в поток файл для записи
            fos = openFileOutput("gameq.txt", Context.MODE_PRIVATE);//вопросы
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Q_Full.GameQue);//записываем в файл и закрываем его
            oos.close();

            fos = openFileOutput("gameh.txt", Context.MODE_PRIVATE);//подсказки
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Q_Full.helps);
            oos.close();

            fos = openFileOutput("gamen.txt", Context.MODE_PRIVATE);//позиция
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Q_Full.q1);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //десериализация списка вопросов текущей игры.
    private void openListGame()
    {
        try{//открываем в поток наш файл

            FileInputStream fis1 = openFileInput("gamen.txt");//позиция
            if (fis1!=null) {
                ObjectInputStream ois1 = new ObjectInputStream(fis1);
                Q_Full.q1 = (int) ois1.readObject();
                ois1.close();}
            FileInputStream fis2 = openFileInput("gameh.txt");//подсказки
            if (fis1!=null) {
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                Q_Full.helps = (boolean[]) ois2.readObject();
                ois2.close();}
            FileInputStream fis = openFileInput("gameq.txt");//выбранные вопросы
            if (fis!=null) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList<Question> task1 = (ArrayList<Question>) ois.readObject();
                Q_Full.GameQue=task1;
                ois.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
