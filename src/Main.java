// import bahanPangan.BahanPangan;
import user.Admin;
import user.Penjual;
import user.Pembeli;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputObj = new Scanner(System.in);
        boolean isRun = true;
        int choose = 0;
        while (isRun) {
            System.out.println("===== MENU =======");
            System.out.print("1. Login sebagai penjual\n2. Login sebagai pembeli\n3. Login sebagai admin\n4. Exit\nMasukkan pilihan anda (1-4): ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    Penjual.loginActionPenjual();
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
}