package user;

import bahanPangan.BahanPangan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Admin {
    private final static String filePathBahanPangan = "src/bahanPangan/dataBahanPangan.txt";
    private final static String filePathDataPO = "src/bahanPangan/dataPreOrder.txt";
    private final static String filePathDataUser = "src/bahanPangan/dataUser.txt";
<<<<<<< HEAD
    private final static String[] arrJenisPangan = { "Padi", "Jagung", "Kedelai" };
    private static double[] arrHargaPangan = { 8000, 7000, 10000 };// SET HARGA
=======
    private final static String filePathHistoryPenjualan = "src/bahanPangan/historyPenjualan.txt";
    private final static String[] arrJenisPangan = {"Padi","Jagung","Kedelai"};
    private static double[] arrHargaPangan = {8000,7000,10000};//SET HARGA
>>>>>>> 733b287d4223fefbc68e62f4aaa26e015abc1976
    private static String date;
    static Scanner inputObj = new Scanner(System.in);
    static String user = "admin";
    static String pass = "admin123";

    // CONTOH PENERAPANNYA
    // boolean isValid = isUser(Admin.getFilePathDataUser(),
    // usename,password,"Pembeli");
    public static void addUser() {
        Scanner input = new Scanner(System.in);
        String role;
        System.out.println("Pilih user yang akan ditambahkan \n 1. Pembeli \n 2. Penjual \n masukkan pilihan anda 1-2 :");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                role = "Pembeli";
                break;
            case 2:
                role = "Penjual";
                break;
            default:
                System.err.println("masukkan pilihan 1 atau 2 ");
                input.close();
                return;
        }
        String id = Penjual.generateID();
        System.out.print("id user anda "+ id);
        System.out.print("Masukkan password user : ");
        String password = input.nextLine();
        if (isUser(filePathDataUser, id, password, role)) {
            // Jika user tidak ditemukan, tambahkan user
            addUserAction(id, password, role);
            System.out.println("Data " + role + " berhasil ditambahkan!!!");
        } else {
            // Jika user ditemukan, lakukan sesuatu di sini
            System.out.println("Data yang anda tambahkan sudah ada tolong ");
        }
        input.close();
    }
    

    public static Boolean isUser(String filePath, String id, String password, String role) { // role = "Penjual"
                                                                                             // /"Pembeli"
        boolean isFoundRole = false;
        boolean isValidUser = false;
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (partsData[0].equals(role))
                    isFoundRole = true;
                if (partsData[0].equals("-"))
                    isFoundRole = false;
                if (isFoundRole && partsData[0].equals(id) && partsData[1].equals(password)) {
                    isValidUser = true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return isValidUser;
    }

    public static void addUserAction(String id, String password, String role) {
        String filePath = Admin.getFilePathDataUser();
        boolean isFoundRole = false;
        String line;
        ArrayList<String> data = new ArrayList<>();
        try {
            // Membaca file baris per baris
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // Membaca setiap baris
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (partsData[0].equals(role))
                    isFoundRole = true;
                else if (isFoundRole && !partsData[0].equals("Id")) {
                    String dataString = String.format("%-15s%-8s", id, password);
                    data.add(dataString);
                    isFoundRole = false;
                }
                data.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        BahanPangan.writeToFile(filePath, data);
    }

    public static boolean isFileExist() {
        boolean isHaveData = false;
        File file = new File(Admin.getFilePathBahanPangan());
        if (file.exists())
            return true;
        else
            return false;
    }

<<<<<<< HEAD
    public static void loginAdmin() {
=======
    public static void removeData(String filePath,String role){
        String line;
        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 2;i++) {
                line = reader.readLine();
                data.add(line);
            }
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if(role.equals("Penjual")){
                    if(partsData[4].equals("0.0"))
                        continue;
                }else
                    if(!partsData[4].equals("BELUM"))
                        continue;
                    data.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        BahanPangan.writeToFile(filePath,data);
    }

    public static void loginAdmin(){
>>>>>>> 733b287d4223fefbc68e62f4aaa26e015abc1976
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

    public static void menu() {
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("===== MENU ADMIN ======");
            System.out.print("1. Set harga pasar\n2. Tambah user\n3. Exit\nMasukan pilihan anda (1-4) : ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    setPasar();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    isRun = false;
                    inputObj.nextLine();
                    break;
                default:
                    System.out.println("Masukkan angka yang benar");
                    break;
            }
        }
    }

    public static void setPasar() {
        double harga;
        for (int i = 0; i < 3; i++) {
            System.out.print("Masukkan harga pasar untuk " + Admin.getJenis(i) + " : ");
            harga = inputObj.nextDouble();
            Admin.setArrHargaJenis(harga, i);
        }
        for (double x : arrHargaPangan) {
            System.out.println("Harga = " + x);
        }
    }

    public static void setDate() {
        Admin.date = Admin.getDate();
    }

    public static String getDate() {
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

    public static void setArrHargaJenis(double hargaJenis, int index) {
        Admin.arrHargaPangan[index] = hargaJenis;
    }

    public static String getFilePathBahanPangan() {
        return filePathBahanPangan;
    }

    public static String getFilePathDataPO() {
        return filePathDataPO;
    }

    public static String getFilePathDataUser() {
        return filePathDataUser;
    }

    public static String getFilePathHistoryPenjualan(){
        return filePathHistoryPenjualan;
    }

    

}
