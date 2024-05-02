package user;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Admin {
    private final static String[] arrJenisPangan = {"Padi","Jagung","Kedelai"};
    private static double[] arrHargaPangan = {8000,7000,10000};//SET HARGA
    private static String date;
    static Scanner inputObj = new Scanner(System.in);
    static String user = "admin";
    static String pass = "admin123";
    public static boolean isFileExist(){
        boolean isHaveData = false;
        File file = new File(Pedagang.getFilePathBahanPangan());
        if(file.exists())
            return true;
        else
            return false;
    }

    public static void loginAdmin(){
        System.out.print("Username: ");
        String username = inputObj.nextLine();
        System.out.print("Password: ");
        String password = inputObj.nextLine();
        if (username.equals(user) && password.equals(pass)) {
            System.out.println("Login admin anda berhasil!!");
            menu();
        } else {
            System.out.println("Sandi atau password salah!!");
        }
    }
    public static void menu(){
        //menu
    }

    public static void setPasar(){
        double harga;
        for (int i = 0; i < 3; i++){
            System.out.print("Masukkan harga pasar untuk " + Admin.getJenis(i) + " : ");
            harga =inputObj.nextDouble();
            Admin.setArrHargaJenis(harga,i);
        }
        for (double x : arrHargaPangan){
            System.out.println("Harga = " + x);
        }
    }

    public static void setDate(){
        Admin.date = Admin.getDate();
    }

    public static String getDate(){
        Date today = new Date();
        // Membuat objek SimpleDateFormat untuk mengatur format tanggal
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(today); // Menggunakan objek formatter untuk mengonversi tanggal menjadi string
    }

    public static String getJenis(int index) {
        return arrJenisPangan[index];
    }

    public static double getHargaJenis(int index) {
        return arrHargaPangan[index];
    }

    public static void setArrHargaJenis(double hargaJenis,int index) {
        Admin.arrHargaPangan[index] = hargaJenis;
    }

}
