import user.Pedagang;
import user.Pembeli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputObj = new Scanner(System.in);
        boolean isRun = true;
        int choose;
        while (isRun){
            System.out.println("===== MENU =======");
            System.out.print("1. Login sebagai pedagang\n2. Login sebagai pembeli\n3. Exit\nMasukkan pilihan anda (1-2): ");
            choose = inputObj.nextInt();
            switch (choose){
                case 1:
                    Pedagang.menu();
                    break;
                case 2:
                    Pembeli.menu();
                    break;
                case 3:
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT ");
                    break;
            }
        }

    }
    public static void addAutoPangan(){

    }


}