package com.example.laba6;

import java.io.Serializable;

public class Question implements Serializable {

    private String Q;//вопрос
    private String[] Ans=new String[4];//ответы
    private int NoA;//номер верного

    public Question (){}
    public Question (String q, String a1,String a2,String a3,String a4, int n )
    {
        Q=q;
        Ans[0]=a1;
        Ans[1]=a2;
        Ans[2]=a3;
        Ans[3]=a4;
        NoA=n;
    }

    public String GetQ() {return  Q;}

    public String[] GetAns() {return  Ans;}

    public int GetNoA() {return  NoA;}

    public void SetQ(String q) {Q=q;}

    public void SetAns(String[] a) {Ans=a;}

    public void SetNoA(int n) {NoA=n;}

}
