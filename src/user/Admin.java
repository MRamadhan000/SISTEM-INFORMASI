package user;

import bahanPangan.BahanPangan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Admin {
    private final static String filePathBahanPangan = "src/bahanPangan/dataBahanPangan.txt";
    private final static String filePathDataPO = "src/bahanPangan/dataPreOrder.txt";
    private final static String filePathDataUser = "src/bahanPangan/dataUser.txt";
// <<<<<<< HEAD
    private final static String[] arrJenisPangan = { "Padi", "Jagung", "Kedelai" };
    // private static double[] arrHargaPangan = {8000,7000,10000};// SET HARGA
    private static double[] arrHargaPangan = {getHarga(),getHarga(),getHarga()};// SET HARGA
// =======
    private final static String filePathHistoryPenjualan = "src/bahanPangan/historyPenjualan.txt";
    // private final static String[] arrJenisPangan = {"Padi","Jagung","Kedelai"};
    // private static double[] arrHargaPangan = {8000,7000,10000};//SET HARGA
// >>>>>>> 733b287d4223fefbc68e62f4aaa26e015abc1976
    private static String date;
    private static final Scanner inputObj = new Scanner(System.in);
    static String user = "admin";
    static String pass = "admin123";

    // CONTOH PENERAPANNYA
    // boolean isValid = isUser(Admin.getFilePathDataUser(),
    // usename,password,"Pembeli");
    public static void addUser() {
        String role;
        String id;
        String password;

        do {
            System.out.println("Pilih user yang akan ditambahkan:");
            System.out.println("1. Pembeli");
            System.out.println("2. Penjual");
            System.out.print("Masukkan pilihan anda (1-2): ");
            int choice = inputObj.nextInt();
            inputObj.nextLine(); 

            switch (choice) {
                case 1:
                    role = "Pembeli";
                    break;
                case 2:
                    role = "Penjual";
                    break;
                default:
                    System.err.println("Masukkan pilihan 1 atau 2");
                    continue; 
            }

            do {
                id = Penjual.generateID();
            } while (!isIdUnique(id, getFilePathDataUser())); 
            System.out.println("ID user baru Anda adalah: " + id);

            System.out.print("Masukkan password user: ");
            password = inputObj.nextLine();

            addUserAction(id, password, role);
            System.out.println("Data " + role + " berhasil ditambahkan!!!");

            break; 
        } while (true);
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

    public static boolean isIdUnique(String id, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id)) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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

    
    public static void removeData(String filePath,String role){
        String line;
        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 2; i++) {
                line = reader.readLine();
                data.add(line);
            }
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (role.equals("Penjual")) {
                    if (partsData[4].equals("0.0"))
                        continue;
                } else if (!partsData[4].equals("BELUM"))
                    continue;
                data.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        BahanPangan.writeToFile(filePath, data);
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

    public static void cleanData() {
        System.out.print("1. Bersihkan data bahan pangan\n2. Bersihlan data pre-order\nMasukan pilihan anda : ");
        int choose = inputObj.nextInt();
        if (choose == 1)
            removeData(Admin.getFilePathBahanPangan(), "Penjual");
        else
            removeData(Admin.getFilePathDataPO(), "Pembeli");
    }

    public static void menu() {
        Admin.clear();
        int choose;
        boolean isRun = true;
        while (isRun) {
            System.out.println("===== MENU ADMIN ======");
            System.out.print("1. Set harga pasar\n2. Tambah user\n3. Bersihkan data \n4. Exit\nMasukan pilihan anda (1-4) : ");
            choose = inputObj.nextInt();
            switch (choose) {
                case 1:
                    setPasar();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    cleanData();
                    break;
                case 4:
                    isRun = false;
                    System.out.println("Keluar dari Menu Admin");
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
        double pembulatan;
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            // System.out.print("Masukkan harga pasar untuk " + Admin.getJenis(i) + " : ");
            harga = 5000 + (rand.nextDouble() * 5000);
            pembulatan = Math.round(harga / 1000) * 1000;
            Admin.setArrHargaJenis(pembulatan, i);
        }
        for (double x : arrHargaPangan) {
            System.out.println("Harga = " + x);
        }
    }

    public static double getHarga(){
        double harga;
        double pembulatan;
        Random rand = new Random();
        harga = 5000 + (rand.nextDouble() * 5000);
        pembulatan = Math.round(harga / 1000) * 1000;
        return pembulatan;

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

    public static String getFilePathHistoryPenjualan() {
        return filePathHistoryPenjualan;
    }

    public static void clear() {
        try {
            Thread.sleep(1000); // delay for 5 seconds
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
