import user.Admin;
import user.Penjual;
import user.Pembeli;
import java.util.Scanner;

public class Main {
    private static final Scanner inputObj = new Scanner(System.in);
    public static void main(String[] args) {
        Admin.clear();
        boolean isRun = true;
        int choose = 0;
        while (isRun) {
            System.out.println("===== MENU =======");
            System.out.print("1. Login sebagai penjual\n2. Login sebagai pembeli\n3. Login sebagai admin\n4. Exit\nMasukkan pilihan anda (1-4): ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    Penjual.loginActionPenjual();
                    Admin.clear();
                    break;
                case 2:
                    Pembeli.loginActionPembeli();
                    Admin.clear();
                    break;
                case 3:
                    Admin.loginAdmin();
                    Admin.clear();
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