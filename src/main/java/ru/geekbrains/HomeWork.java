package ru.geekbrains;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HomeWork {
    static final int SIZE = 10;
    static final int HALF = SIZE/2;

    public static void main(String[] args){

       System.out.println(Arrays.equals(noFlow(),withFlow()));// выполнение с 2 массивами
        System.out.print("Спосб 2\n");
        System.out.println(Arrays.equals(noFlow(),withFlowOneArray()));// выполнение с 1 массивом
    }

    public static float[] noFlow(){
        System.out.print("Выполение без потока\n");
        float [] array = new float[SIZE];
        long a = System.currentTimeMillis();
        System.out.print("Время начала выполнения "+a+"\n");
        fillArray(array, SIZE,0);

        calculateArray(array,SIZE,0,0);
        long b = System.currentTimeMillis()-a;
        System.out.print("Время работы "+b+"\n");


        return array;
    }

    public static void sM (float [] array,int length,int startPoint,int bias){
        fillArray(array, length,startPoint);
        calculateArray(array,length,bias,startPoint);
    }

    public static void fillArray (float [] array,int length,int startPoint){
       for (int i = startPoint; i < length; i++) {
            array[i] = 1.0f;
        }


    }
    public static void  calculateArray(float [] array,int length, int j,int startPoint){
        for (int i=startPoint; i < length; i++)

            array[i] = (float) (array[i] * Math.sin(0.2f + (i+j) / 5) * Math.cos(0.2f + (i+j) / 5) * Math.cos(0.4f + (i+j) / 2));
    }

    public static float[] withFlow(){
        System.out.print("Выполение c потоками\n");
        float [] arrayA = new float[HALF];
        float [] arrayB = new float[HALF];
        float [] result = new float[SIZE];

        Thread myRunnable = new Thread(()->sM(arrayB,HALF,0,HALF));
        myRunnable.start();
        long a = System.currentTimeMillis();
        System.out.print("Время начала выполнения "+a+"\n");
        fillArray(arrayA, HALF,0);
        calculateArray(arrayA,HALF,0,0);

        System.arraycopy(arrayA,0,result,0,HALF);
        System.arraycopy(arrayB,0,result,HALF,arrayB.length);
        long b = System.currentTimeMillis()-a;
        System.out.print("Время работы "+b+"\n");

       // System.out.println(Arrays.toString(result));

        return result;
    }

    public static float[] withFlowOneArray(){
        System.out.print("Выполение c потоками\n");
        float [] result = new float[SIZE];
        long a = System.currentTimeMillis();

        Thread myRunnablePart1 = new Thread(()->sM(result,HALF,0,0));
        Thread myRunnablePart2 = new Thread(()->sM(result,SIZE,HALF,0));
        myRunnablePart1.start();
        myRunnablePart2.start();

        System.out.print("Время начала выполнения "+a+"\n");

        long b = System.currentTimeMillis()-a;
        System.out.print("Время работы "+b+"\n");

        // System.out.println(Arrays.toString(result));

        return result;
    }



}
