package user;

import bahanPangan.BahanPangan;
import java.util.ArrayList;
import java.util.Scanner;

public class Pembeli {
    private static Scanner inputObj = new Scanner(System.in);
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> passwords = new ArrayList<>();
    static {
        usernames.add("pembeli1");
        passwords.add("pass1");
        usernames.add("pembeli2");
        passwords.add("pass2");
        usernames.add("pembeli3");
        passwords.add("pass3");
    }

    public static boolean loginValidasi(String username, String password) {
        int index = usernames.indexOf(username);
        if (index != -1) {
            if (passwords.get(index).equals(password)) {
                return true; 
            }
        }
        return false;
    }

    public static void loginActionPembeli() {
        System.out.print("Masukkan username anda: ");
        String username = inputObj.nextLine();
        System.out.print("Masukkan password anda: ");
        String password = inputObj.nextLine();
        if (loginValidasi(username, password)) {
            System.out.println("Login berhasil!");
            menu();
        } else {
            System.out.println("Username atau password salah!");
        }
    }

    public static void menu() {
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("===== MENU PEMBELI ======");
            System.out.print("1. Tampilkan bahan pangan yang tersedia\n2. Beli bahan pangan\n3. Buat pesanan\n4. Exit\nMasukan pilihan anda (1-4) : ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    BahanPangan.displayData(Admin.getFilePathBahanPangan(), "BAHAN PANGAN");
                    break;
                case 2:
                    Pembeli.beliBahanPangan();
                    break;
                case 3:
                    Pembeli.buatPreOrder();
                    break;
                case 4:
                    isRun = false;
                    inputObj.nextLine();
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void beliBahanPangan() {
        inputObj.nextLine();
        String id;
        BahanPangan.displayData(Admin.getFilePathBahanPangan(), "BAHAN PANGAN");
        System.out.print("Masukkan id yang ingin anda beli : ");
        id = inputObj.nextLine();
        boolean isFoundId = BahanPangan.isHaveId(Admin.getFilePathBahanPangan(), id);
        if (isFoundId) {
            double jumlahBeli, uang, arrPangan[] = BahanPangan.getInfoPangan(Admin.getFilePathBahanPangan(), id);
            double hargaJual = arrPangan[0], jumlahAwal = arrPangan[1];
            System.out.println("\n ======= JUMLAH YANG TERSEDIA " + jumlahAwal + " ==========");
            do {
                System.out.print("Masukkan jumlah yang ingin dibeli : ");
                jumlahBeli = inputObj.nextDouble();
                if (jumlahBeli > arrPangan[1])
                    System.out.println("Jumlah anda melebihi");
            } while (jumlahBeli > arrPangan[1]);
            System.out.println("\n ======= HARGA BAHAN YANG DIBELI " + BahanPangan.formatCurrencyIDR(hargaJual * jumlahBeli) + " ==========");
            do {
                System.out.print("Masukkan uang anda : ");
                uang = inputObj.nextDouble();
                if (uang < hargaJual * jumlahBeli)
                    System.out.println("Uang anda kurang");
                else if (uang > hargaJual * jumlahBeli)
                    System.out.println("Uang anda kelebihan");
            } while (uang != (hargaJual * jumlahBeli));
            System.out.println("PEMBELIAN BERHASIL");
            if (jumlahAwal - jumlahBeli < 1)
                BahanPangan.removeData(Admin.getFilePathBahanPangan(), id);
            else if (jumlahAwal - jumlahBeli > 0)
                BahanPangan.editData(Admin.getFilePathBahanPangan(), id, jumlahBeli);
        } else
            System.out.println(id + " TIDAK DITEMUKAN");
    }

    public static void buatPreOrder() {
        String jenis;
        int choose;
        System.out.print(
                "====== MENU INPUT BAHAN PANGAN ======\n1. Padi\n2. Jagung\n3. Kedelai\nPilih jenis yang anda inginkan : ");
        do {
            choose = inputObj.nextInt();
            if (choose < 1 || choose > 3)
                System.out.println("Masukan angka yang benar");
        } while (choose < 1 || choose > 3);
        jenis = Admin.getJenis(choose - 1);
        System.out.print("Masukkan harga " + jenis + " /Kg: ");
        double harga = inputObj.nextDouble();
        System.out.print("Masukkan jumlah keseluruhan: ");
        double jumlah = inputObj.nextDouble();
        BahanPangan bahanPangan = new BahanPangan(jenis, harga, jumlah, Penjual.generateID());
        BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathDataPO());
    }

}
