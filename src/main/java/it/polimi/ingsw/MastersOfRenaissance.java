package it.polimi.ingsw;
import it.polimi.ingsw.Gui.GUIRunnable;

import java.util.InputMismatchException;
import java.util.Scanner;

/** @author  Alessandra Atria **/
public class MastersOfRenaissance {
    /**
 * Method main selects CLI, GUI or Server based on the arguments provided.
 *
 * @param args of type String[]
 */
public static void main(String[] args){
    System.out.println("Hi there! Welcome to Masters of Renaissance!\nWhat do you want to launch?");
    System.out.println("0. SERVER\n1. CLIENT (CLI INTERFACE)\n2. CLIENT (GUI INTERFACE)");
    System.out.println("\n>Type the number of the desired option!");
    System.out.print(">");
    Scanner scanner = new Scanner(System.in);
    int input = 0;
    try {
        input = scanner.nextInt();
    } catch (InputMismatchException e) {
        System.err.println("Numeric format requested, please run the executable again with one of these options:\n1.server\n2.client");
        System.exit(-1);
    }
    switch (input) {
        case 0: ServerApp.main(null);
        break;
        case 1 : ClientApp.main(null);
        break;
        case 2 :
            System.out.println("You selected the GUI interface, have fun!\nStarting...");
            GUIRunnable.main(args);
        break;
        default: System.err.println("Invalid argument, please run the executable again with one of these options:\n1.server\n2.client");
        break;
    }
}
}


