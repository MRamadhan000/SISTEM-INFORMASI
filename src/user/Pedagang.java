package user;
import bahanPangan.BahanPangan;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Pedagang {

    private final static String filePathBahanPangan = "src/bahanPangan/dataBahanPangan.txt";
    private final static String filePathDataPO = "src/bahanPangan/dataPreOrder.txt";
    private static Scanner inputObj = new Scanner(System.in);
    public static String generateID() {
        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        char letter = (char) (random.nextInt(26) + 'A');
        return String.format("%02d%02d%c", number1, number2, letter);
    }

    public static void menu(){
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("\n====== MENU PEDAGANG =======");
            System.out.print("1. Tampilkan bahan pangan di Pasar\n2. Tambahkan bahan pangan\n3. Tampilkan Pre-Order yang tersedia\n4. Layani Pre-Order\n5. Keluar\nMasukan pilihan anda (1-5): ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    BahanPangan.displayData(filePathBahanPangan,"BAHAN PANGAN");
                    break;
                case 2:
                    Pedagang.tambahPangan();
                    break;
                case 3:
                    BahanPangan.displayData(filePathDataPO, "PRE-ORDER");
                    break;
                case 4:
                    if (BahanPangan.isHaveData(filePathDataPO)){
                        BahanPangan.displayData(filePathDataPO,"PRE-ORDER");
                        Pedagang.layaniPreOrder();
                    }
                    else
                        System.out.println("TIDAK ADA PRE-ORDER YANG TERSEDIA");
                    break;
                case 5:
                    isRun = false;
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void layaniPreOrder(){
        inputObj.nextLine();
        System.out.println("Masukkan id yang ingin anda terima : ");
        String id = inputObj.nextLine();
        boolean isFound = BahanPangan.isHaveId(Pedagang.getFilePathDataPO(),id);
        if(isFound){
            ArrayList<String> data = BahanPangan.removeData(Pedagang.getFilePathDataPO(),id);
            BahanPangan.writeToFile(Pedagang.getFilePathDataPO(),data);
        }else
            System.out.println(id + " TIDAK DITEMUKAN");

    }
    public static void tambahPangan(){
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
        boolean isValid = cekHargaPasar(choose,harga);
        if(isValid){
            System.out.print("Masukkan jumlah keseluruhan: ");
            double jumlah = inputObj.nextDouble();
            BahanPangan bahanPangan = new BahanPangan(jenis,harga,jumlah,Pedagang.generateID());
            ArrayList<String> data = BahanPangan.appeandToTxt(bahanPangan,Pedagang.getFilePathBahanPangan());
            BahanPangan.writeToFile(Pedagang.getFilePathBahanPangan(),data);
        }
        else
            System.out.println("MAAF HARGA YANG DITAWARKAN TIDAK SESUAI");
    }

    public static boolean cekHargaPasar(int choose,double harga){
        double hargaPasar = Admin.getHargaJenis(choose-1),hargaMaks,hargaMin;
        hargaMaks = (120.0/100) * hargaPasar;
        hargaMin = (20.0/100) * hargaPasar;
        if(harga >= hargaMin && harga <= hargaMaks)
            return true;
        return false;
    }
    public static String getFilePathBahanPangan() {
        return filePathBahanPangan;
    }

    public static String getFilePathDataPO(){
        return filePathDataPO;
    }


}
