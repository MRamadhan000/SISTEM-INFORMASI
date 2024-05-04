package user;
import bahanPangan.BahanPangan;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Penjual {
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> passwords = new ArrayList<>();
    static {
        usernames.add("user1");
        passwords.add("pass123");
        usernames.add("dika");
        passwords.add("1");
    }
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
                    BahanPangan.displayHistoryPenjualan(Admin.getFilePathHistoryPenjualan(),"HISTORY");
                    break;
                case 6:
                    isRun = false;
                    System.out.println("Keluar Dari Menu Penjual");
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
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

    public static void loginActionPenjual() {
        System.out.print("Masukkan username anda: ");
        String username = inputObj.nextLine();
        System.out.print("Masukkan password anda: ");
        String password = inputObj.nextLine();
        if (loginValidasi(username, password)) {
            System.out.println("Login berhasil!");
         
            menu(username);
        } else {
            System.out.println("Username atau password salah!");
        }
    }
    
    public static void layaniPreOrder(String userId){
        inputObj.nextLine();
        System.out.print("Masukkan id yang ingin anda terima : ");
        String id = inputObj.nextLine();
        boolean isFound = BahanPangan.isHaveId(Admin.getFilePathDataPO(),id);
        if(isFound)
            BahanPangan.editDataPreOrder(Admin.getFilePathDataPO(),id,userId);
        else
            System.out.println(id + " TIDAK DITEMUKAN");

    }
    public static void tambahPangan(String idUser){

        String jenis;
        int choose;
        System.out.print("====== MENU INPUT BAHAN PANGAN ======\n1. Padi\n2. Jagung\n3. Kedelai\nPilih jenis yang anda inginkan : ");
        do {
            choose = inputObj.nextInt();
            if (choose < 1 || choose > 3)
                System.out.println("Masukan angka yang benar");
        }while (choose < 1 || choose > 3);
        jenis = Admin.getJenis(choose-1);
        System.out.println("Harga Minimum " + jenis +":"+ getMinsHarga(choose));
        System.out.println("Harga Maksimum " + jenis +":"+ getMaksHarga(choose));
        System.out.print("Masukkan harga " + jenis + " /Kg: ");
        double harga = inputObj.nextDouble();
        boolean isValid = cekHargaPasar(choose,harga);
        if(isValid){
            System.out.print("Masukkan jumlah keseluruhan: ");
            double jumlah = inputObj.nextDouble();
            BahanPangan bahanPangan = new BahanPangan(jenis,harga,jumlah, Penjual.generateID());
            BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathBahanPangan(),"Penjual",idUser);
            BahanPangan.appeandToTxt(bahanPangan, Admin.getFilePathHistoryPenjualan(), "Penjual", idUser);
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
