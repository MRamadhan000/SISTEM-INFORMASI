package user;

import bahanPangan.BahanPangan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Pembeli {
    private static Scanner inputObj = new Scanner(System.in);

    public static void loginActionPembeli() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan id anda: ");
        String idUser = input.nextLine();
        System.out.print("Masukkan password anda: ");
        String password = input.nextLine();
        if (Admin.isUser(Admin.getFilePathDataUser(), idUser,password,"Pembeli")) {
            System.out.println("Login berhasil!");
            menu(idUser);
        } else {
            System.out.println("Username atau password salah!");
            // input.close();
            return;
        }
        input.close();
    }


    public static void menu(String userId) {
        Admin.clear();
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("===== MENU PEMBELI ======");
            System.out.print("1. Tampilkan bahan pangan yang tersedia\n2. Beli bahan pangan\n3. Buat pesanan\n4. Tampilkan Notifikasi\n5. Exit\nMasukan pilihan anda (1-5) : ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    BahanPangan.displayDataPangan(Admin.getFilePathBahanPangan(), "BAHAN PANGAN");
                    break;
                case 2:
                    Pembeli.beliBahanPangan(userId);
                    break;
                case 3:
                    Pembeli.buatPreOrder(userId);
                    break;
                case 4:
                    Pembeli.displayNotif(Admin.getFilePathDataPO(),userId);
                    break;
                case 5:
                System.out.println("Keluar dari Menu Penjual");
                Admin.clear();
                Main.display();
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void beliBahanPangan(String userId) {
        inputObj.nextLine();
        String id;
        BahanPangan.displayDataPangan(Admin.getFilePathBahanPangan(), "BAHAN PANGAN");
        System.out.print("Masukkan id yang ingin anda beli : ");
        id = inputObj.nextLine();
        boolean isFoundId = BahanPangan.isHaveId(Admin.getFilePathBahanPangan(), id);
        if (isFoundId) {
            double jumlahBeli, uang;
            String arrData = BahanPangan.getInfoPangan(Admin.getFilePathBahanPangan(), id);
            String[] partsData = arrData.split("\\s+");
            double hargaJual = Double.parseDouble(partsData[2]), jumlahAwal = Double.parseDouble(partsData[4]);
            System.out.println("\n ======= JUMLAH YANG TERSEDIA " + jumlahAwal + " ==========");
            do {
                System.out.print("Masukkan jumlah yang ingin dibeli : ");
                jumlahBeli = inputObj.nextDouble();
                if (jumlahBeli > jumlahAwal)
                    System.out.println("Jumlah anda melebihi");
            } while (jumlahBeli > jumlahAwal);
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
            BahanPangan.appeandToHistory(arrData,userId,jumlahAwal,jumlahAwal-jumlahBeli);
            BahanPangan.editDataPangan(Admin.getFilePathBahanPangan(),id,jumlahBeli);
        } else
            System.out.println(id + " TIDAK DITEMUKAN");
    }

    // public static void buatPreOrder(String userId) {
    //     String jenis;
    //     int choose;
    //     System.out.print(
    //             "====== MENU INPUT BAHAN PANGAN ======\n1. Padi\n2. Jagung\n3. Kedelai\nPilih jenis yang anda inginkan : ");
    //     do {
    //         choose = inputObj.nextInt();
    //         if (choose < 1 || choose > 3)
    //             System.out.println("Masukan angka yang benar");
    //     } while (choose < 1 || choose > 3);
    //     jenis = Admin.getJenis(choose - 1);
    //     System.out.print("Masukkan harga " + jenis + " /Kg: ");
    //     double harga = inputObj.nextDouble();
    //     System.out.print("Masukkan jumlah keseluruhan: ");
    //     double jumlah = inputObj.nextDouble();
    //     BahanPangan bahanPangan = new BahanPangan(jenis, harga, jumlah, Penjual.generateID());
    //     BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathDataPO(),"Pembeli",userId);
    // }

    public static void buatPreOrder(String userId) {
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
        System.out.println("Harga Minimum " + jenis +" : "+ BahanPangan.formatCurrencyIDR(Penjual.getMinsHarga(choose)));
        System.out.println("Harga Maksimum " + jenis +" : "+ BahanPangan.formatCurrencyIDR(Penjual.getMaksHarga(choose)));
        System.out.print("Masukkan harga " + jenis + " /Kg: ");
        double harga = inputObj.nextDouble();
        boolean isValid = Penjual.cekHargaPasar(choose,harga);
        if(isValid){
            System.out.print("Masukkan jumlah keseluruhan: ");
            double jumlah = inputObj.nextDouble();
            BahanPangan bahanPangan = new BahanPangan(jenis, harga, jumlah, Penjual.generateID());
            BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathDataPO(),"Pembeli",userId);
        }else
            System.out.println("HARGA YANG DITAWARKAN TIDAK SESUAI");
    }

    public static void displayNotif(String filePath,String userId){
        boolean isFoundId = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (partsData[5].equals(userId) &&!partsData[4].equals("BELUM")){
                    isFoundId = true;
                    System.out.println("PESANAN ANDA DENGAN ID " + partsData[0] +"  TELAH DITERIMA OLEH " + partsData[4]);
                    System.out.printf("=   %-13s=    %-15s=   %-15s=   %-15s=   %-11s=%n", "Jenis", "Harga PerKg","Jumlah" ,"Penerima","Id Pangan");
                    System.out.printf("=   %-13s=    %-15s=   %-15s=   %-15s=   %-9s  =%n", partsData[1], partsData[2],partsData[3],partsData[4], partsData[0]);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        if(!isFoundId)
            System.out.println("TIDAK ADA NOTIFIKASI");
    }

}
