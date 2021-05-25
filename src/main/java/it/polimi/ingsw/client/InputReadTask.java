package it.polimi.ingsw.client;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InputReadTask{
    private final ExecutorService readInput;

    public InputReadTask(){
        readInput = Executors.newSingleThreadExecutor();

    }




    public Future<String> readLine(String question)  {
        return readInput.submit(() -> {
            Scanner in = new Scanner(System.in);
           System.out.println(question);
            String input = in.nextLine();
            return input;
        });
    }

}


