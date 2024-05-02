package user;

import bahanPangan.BahanPangan;
import java.util.ArrayList;
import java.util.Scanner;

public class Pembeli {
    private static Scanner inputObj = new Scanner(System.in);

    public static void menu(){
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("===== MENU PEMBELI ======");
            System.out.print("1. Tampilkan bahan pangan yang tersedia\n2. Beli bahan pangan\n3. Buat pesanan\n4. Exit\nMasukan pilihan anda (1-4) : ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    BahanPangan.displayData(Pedagang.getFilePathBahanPangan(),"BAHAN PANGAN");
                    break;
                case 2:
                    Pembeli.beliBahanPangan();
                    break;
                case 3:
                    Pembeli.buatPreOrder();
                    break;
                case 4:
                    isRun = false;
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void beliBahanPangan(){
        inputObj.nextLine();
        String id;
        BahanPangan.displayData(Pedagang.getFilePathBahanPangan(),"BAHAN PANGAN");
        System.out.print("Masukkan id yang ingin anda beli : ");
        id = inputObj.nextLine();
        boolean isFoundId = BahanPangan.isHaveId(Pedagang.getFilePathBahanPangan(),id);
        System.out.println("id = " + isFoundId);
        if(isFoundId){
            double jumlahBeli,uang,arrPangan[] = BahanPangan.getInfoPangan(Pedagang.getFilePathBahanPangan(),id);
            double hargaJual = arrPangan[0],jumlahAwal = arrPangan[1];
            System.out.println(" ======= JUMLAH YANG TERSEDIA " + jumlahAwal + " ==========");
            do {
                System.out.print("Masukkan jumlah yang ingin dibeli : ");
                jumlahBeli = inputObj.nextDouble();
                if(jumlahBeli > arrPangan[1])
                    System.out.println("Jumlah anda melebihi");
            }while (jumlahBeli > arrPangan[1]);
            System.out.println(" ======= HARGA BAHAN YANG DIBELI " + hargaJual*jumlahBeli + " ==========");
            do {
                System.out.print("Masukkan uang anda : ");
                uang = inputObj.nextDouble();
                if(uang < hargaJual*jumlahBeli)
                    System.out.println("Uang anda kurang");
                 else if(uang > hargaJual*jumlahBeli)
                    System.out.println("Uang anda kelebihan");
            }while (uang != (hargaJual*jumlahBeli));
            System.out.println("PEMBELIAN BERHASIL");
            if(jumlahAwal- jumlahBeli < 1)
                Pembeli.removeItem(id);
            else if(jumlahAwal - jumlahBeli > 0)
                Pembeli.editData(id, jumlahBeli);
        }
        else
            System.out.println(id + " TIDAK DITEMUKAN");
    }

    public static void buatPreOrder(){
        String jenis;
        int choose;
        System.out.print("====== MENU INPUT BAHAN PANGAN ======\n1. Padi\n2. Jagung\n3. Kedelai\nPilih jenis yang anda inginkan : ");
        do {
            choose = inputObj.nextInt();
            if (choose < 1 || choose > 3)
                System.out.println("Masukan angka yang benar");
        }while (choose < 1 || choose > 3);
        jenis = Admin.getJenis(choose-1);
        System.out.print("Masukkan harga " + jenis + " /Kg: ");
        double harga = inputObj.nextDouble();
        System.out.print("Masukkan jumlah keseluruhan: ");
        double jumlah = inputObj.nextDouble();
        BahanPangan bahanPangan = new BahanPangan(jenis,harga,jumlah,Pedagang.generateID());
        ArrayList<String> data = BahanPangan.appeandToTxt(bahanPangan,Pedagang.getFilePathDataPO());
        BahanPangan.writeToFile(Pedagang.getFilePathDataPO(),data);
    }
    public static void removeItem(String id){
        ArrayList<String> data = BahanPangan.removeData(Pedagang.getFilePathBahanPangan(),id);
        BahanPangan.writeToFile(Pedagang.getFilePathBahanPangan(),data);
    }

    public static void editData(String id, double jumlah){
        ArrayList<String> arr = BahanPangan.editData(Pedagang.getFilePathBahanPangan(),id,jumlah);
        BahanPangan.writeToFile(Pedagang.getFilePathBahanPangan(),arr);
    }

}
