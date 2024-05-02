import user.Admin;
import user.Pedagang;
import user.Pembeli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputObj = new Scanner(System.in);
        boolean isRun = true;
        int choose;
        while (isRun) {
            System.out.println("===== MENU =======");
            System.out.print(
                    "1. Login sebagai pedagang\n2. Login sebagai pembeli\n3. Login sebagai admin\n4. Exit\nMasukkan pilihan anda (1-4): ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    Pedagang.loginActionPedagang();
                    break;
                case 2:
                    Pembeli.loginActionPembeli();
                    break;
                case 3:
                    Admin.loginAdmin();
                    break;
                case 4:
                    isRun = false;
                    break;
                default:
                    System.out.println("INVALID INPUT ");
                    break;
            }
        }

    }

    public static void addAutoPangan() {

    }

}