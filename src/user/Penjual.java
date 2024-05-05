package user;
import bahanPangan.BahanPangan;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Penjual {
    private static Scanner inputObj = new Scanner(System.in);
    public static String generateID() {
        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        char letter = (char) (random.nextInt(26) + 'A');
        return String.format("%02d%02d%c", number1, number2, letter);
    }

    public static void menu(String userId){
        Admin.clear();
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("\n====== MENU PENJUAL =======");
            System.out.print("1. Tampilkan bahan pangan di Pasar\n2. Tambahkan bahan pangan\n3. Tampilkan Pre-Order yang tersedia\n4. Layani Pre-Order\n5. History Penjualan\n6. Keluar\nMasukan pilihan anda (1-6): ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    BahanPangan.displayDataPangan(Admin.getFilePathBahanPangan(),"BAHAN PANGAN");
                    break;
                case 2:
                    Penjual.tambahPangan(userId);
                    break;
                case 3:
                    BahanPangan.displayDataPO(Admin.getFilePathDataPO(),"PRE-ORDER");
                    break;
                case 4:
                    if (BahanPangan.isHaveData(Admin.getFilePathDataPO())){
                        BahanPangan.displayDataPO(Admin.getFilePathDataPO(),"PRE-ORDER");
                        Penjual.layaniPreOrder(userId);
                    }
                    else
                        System.out.println("TIDAK ADA PRE-ORDER YANG TERSEDIA");
                    break;
                case 5:
                    Penjual.displayHistory(userId);
                    break;
                case 6:
                    isRun = false;
                    System.out.println("Keluar dari Menu Penjual");
                    Admin.clear();
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void displayHistory(String userId){
        boolean isFound = false;
        String line,filePath = Admin.getFilePathHistoryPenjualan();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (partsData[5].equals(userId)){
                    isFound = true;
                    System.out.println("BAHAN PANGAN ANDA DENGAN ID " + partsData[0] +" TELAH DIBELI OLEH " + partsData[6]);
                    System.out.printf("=   %-13s=    %-15s=   %-15s=   %-25s=   %-15s=   %-11s=%n", "Jenis", "Harga PerKg","Jumlah Awal" ,"Jumlah Setelah Dibeli","Id Pembeli","Id Pangan");
                    System.out.printf("=   %-13s=    %-15s=   %-15s=   %-25s=   %-15s=   %-9s  =%n", partsData[1], partsData[2],partsData[3],partsData[4], partsData[6],partsData[0]);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
        if(!isFound)
            System.out.println("ANDA " + userId +" BELUM ADA MENDAFTARKAN BAHAN PANGAN ANDA");
    }


    // public static boolean loginValidasi(String username, String password) {
    //     int index = usernames.indexOf(username);
    //     if (index != -1) {
    //         if (passwords.get(index).equals(password)) {
    //             return true; 
    //         }
    //     }
    //     return false;
    // }

    public static void loginActionPenjual() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan id anda: ");
        String idUser = input.nextLine();
        System.out.print("Masukkan password anda: ");
        String password = input.nextLine();
        if (Admin.isUser(Admin.getFilePathDataUser(), idUser,password,"Penjual")) {
            System.out.println("Login berhasil!");
            menu(idUser);
        } else {
            System.out.println("Username atau password salah!");
        }
        input.close();
    }

    public static void layaniPreOrder(String userId){
        inputObj.nextLine();
        System.out.print("Masukkan id yang ingin anda terima : ");
        String idBahanPangan = inputObj.nextLine();
        boolean isFound = BahanPangan.isHaveId(Admin.getFilePathDataPO(),idBahanPangan);
        if(isFound)
            BahanPangan.editDataPreOrder(Admin.getFilePathDataPO(),idBahanPangan,userId);
        else
            System.out.println(idBahanPangan + " TIDAK DITEMUKAN");

    }

    public static void tambahPangan(String userId){
        String jenis;
        int choose;
        System.out.print("====== MENU INPUT BAHAN PANGAN ======\n1. Padi\n2. Jagung\n3. Kedelai\nPilih jenis yang anda inginkan : ");
        do {
            choose = inputObj.nextInt();
            if (choose < 1 || choose > 3)
                System.out.println("Masukan angka yang benar");
        }while (choose < 1 || choose > 3);
        jenis = Admin.getJenis(choose-1);
        System.out.println("Harga Minimum " + jenis +" : "+ BahanPangan.formatCurrencyIDR(getMinsHarga(choose)));
        System.out.println("Harga Maksimum " + jenis +" : "+ BahanPangan.formatCurrencyIDR(getMaksHarga(choose)));
        System.out.print("Masukkan harga " + jenis + " /Kg: ");
        double harga = inputObj.nextDouble();
        boolean isValid = cekHargaPasar(choose,harga);
        if(isValid){
            System.out.print("Masukkan jumlah keseluruhan: ");
            double jumlah = inputObj.nextDouble();
            BahanPangan bahanPangan = new BahanPangan(jenis,harga,jumlah, Penjual.generateID());
            BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathBahanPangan(),"Penjual",userId);
        }
        else
            System.out.println("MAAF HARGA YANG DITAWARKAN TIDAK SESUAI");
    }

    public static boolean cekHargaPasar(int choose,double harga){
        double hargaPasar = Admin.getHargaJenis(choose-1),hargaMaks,hargaMin;
        hargaMaks = 1.2 * hargaPasar;
        hargaMin = 0.8 * hargaPasar;
        if(harga >= hargaMin && harga <= hargaMaks)
            return true;
        return false;
    }

    public static double  getMaksHarga(int choose){
        double hargaPasar = Admin.getHargaJenis(choose-1),hargaMaks;
        hargaMaks = 1.2 * hargaPasar;
        return hargaMaks;
    }

    public static double  getMinsHarga(int choose){
        double hargaPasar = Admin.getHargaJenis(choose-1),hargaMin;
        hargaMin = 0.8 * hargaPasar;
        return hargaMin;
    }



}
