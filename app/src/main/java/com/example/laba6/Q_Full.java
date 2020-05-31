package com.example.laba6;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Q_Full {

    static public ArrayList<Question> Que=new ArrayList<Question>();//общий список вопросов
    static public ArrayList<Question> QueY=new ArrayList<Question>();//список добавленных пользователем вопросов вопросов
    static public ArrayList<Question> GameQue=new ArrayList<Question>();//вопросы для игровой сессии
    static int q1=-1;//позиция игровой сессии
    static boolean [] helps=new boolean[3];//доступность подсказок

    static public void Fill(){//вопросы по умолчанию
        Que.clear();
        Que.add(new Question("Столица Японии", "Нью-Йорк", "Токио", "Пекин", "Сеул",2));
        Que.add(new Question("Самая солнечная фигура в геометрии", "Окружность", "Биссектриса", "Луч", "Радиус",3));
        Que.add(new Question("Осенние равноденствие проходит", "20 сентября", "22 сентября", "24 сентября", "21 сентября",2));
        Que.add(new Question("Сколько лет пролежал на печи известный богатырь Илья Муромец", "33", "24", "30", "18",1));
        Que.add(new Question("Какой атракцион является 'радостью для нас'", "Качели", "Карусель", "Горки", "Колесо обозрения",2));
        Que.add(new Question("Сколько конституций было в СССР", "1", "2", "3", "Ни одной",3));
        Que.add(new Question("Какое созввездие сущесвует", "Ленивая кошка", "Большая медведица", "Косой заяц", "Серый волк",2));
        Que.add(new Question("Какой фрукт или овощ на руси называли 'голова садовая'", "Свекла", "Слива", "Яблоко", "Капуста",4));
        Que.add(new Question("Что такое полба", "Овощь", "Фрукт", "Крупа", "Каша",3));
        Que.add(new Question("Единица измерения электрического заряда", "Кулон", "Колье", "Кольцо", "Серьга",1));
        Que.add(new Question("Какое поле появляется вокруг любого предмета?", "Гравитационное", "Магнитное", "Электрическое", "Электромагнитное",1));
        GameQue=Que;
    }
    //выбираем 10 случайных вопросов в случайном порядке
    static public ArrayList<Question> getRandomElements(final ArrayList<Question> list) {
        final int amount = 10;
        ArrayList<Question> returnList = new ArrayList<Question>(list);

        Collections.shuffle(returnList); // тут делаем рандом
        if (returnList.size() > amount) {
            // тут отрезаем не нужную часть
            list.subList(returnList.size() - amount, returnList.size()).clear();
            returnList.removeAll(list);
        }
        return returnList;
    }


}
