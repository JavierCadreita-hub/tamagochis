package org.cuatrovientos;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

/*
El programa principal es el “cuidador” de X Tamagotchis. Por lo tanto al arrancarse deberá ponerles las pilas y lanzarlos al
mundo.
*/
        ArrayList<Tamagochi> tamagochis = new ArrayList<>();
        ArrayList<Thread> hilos = new ArrayList<>();

        int opcion = 0;

        //Este bucle crea los tamagochis y arranca los hilos.
        for (int i = 0; i < 4; i++) {
            Tamagochi tama = new Tamagochi("nombre" + i);
            Thread hilo = new Thread(tama);

            tamagochis.add(tama);
            hilos.add(hilo);

            hilo.start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}



