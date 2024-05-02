package bahanPangan;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BahanPangan{
    private String jenis,id;
    private double hargaSatuan,jumlahBahan;

    public BahanPangan(String jenis,double hargaSatuan,double jumlahBahan,String id){
        this.jenis = jenis;
        this.hargaSatuan = hargaSatuan;
        this.jumlahBahan = jumlahBahan;
        this.id = id;
    }

    public static void displayData(String filePath, String keterangan){
        boolean isHave =BahanPangan.isHaveData(filePath);
        String line;
        if (isHave) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        }
        else
            System.out.println("TIDAK ADA " + keterangan + " YANG TERSEDIA");
    }

    public static boolean isHaveData(String filePath){
        String line;
        int loop = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                loop++;
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        if(loop > 2)
            return true;
        else
            return false;
    }

    public static boolean isHaveId(String filePath,String id){
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 2;i++)
                line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if(partsData[3].equals(id)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return false;
    }

    public static void removeData(String filePath, String id){
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
                if(!(partsData[3].equals(id)))
                    data.add(line);

            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        BahanPangan.writeToFile(filePath,data);
    }

    public static double[] getInfoPangan(String filePath,String id){
        String line;
        boolean isFound = true;
        double[] arr = new double[2];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 2;i++)
                line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if(partsData[3].equals(id)){
                    arr[0] = Double.parseDouble(partsData[1]);
                    arr[1] = Double.parseDouble(partsData[2]);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return arr;
    }

    public static void appeandToTxt(BahanPangan bahanPangan, String filePath) {
            boolean isFound = false;
            String dataString = String.format("%-15s%-20.0f%-15s%-10s", bahanPangan.getJenis(), bahanPangan.getHargaSatuan(), bahanPangan.getJumlahBahan(), bahanPangan.getId());
            ArrayList<String> data = new ArrayList<>();
            String line;
            try {
                //Membaca file baris per baris
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                // Membaca setiap baris
                while ((line = reader.readLine()) != null) {
                    data.add(line);
                    String[] partsData = line.split("\\s+");
                    if(partsData[0].equals(bahanPangan.getJenis())){
                        data.add(dataString);
                        isFound = true;
                    }
                }
                if(!isFound)
                    data.add(dataString);
                reader.close();
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
            BahanPangan.writeToFile(filePath,data);
    }

    public static void editData(String filePath,String id, double jumlahBeli) {
        String line;
        ArrayList<String> arr = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 2;i++){
                line = reader.readLine();
                arr.add(line);
            }
            while ((line = reader.readLine()) != null) {
                String[] partsData = line.split("\\s+");
                if (partsData[3].equals(id)){
                    double jumlahStok = Double.parseDouble(partsData[2]);
                    double hargaJual = Double.parseDouble(partsData[1]);
                    String dataString = String.format("%-15s%-20.0f%-15.0f%-10s", partsData[0], hargaJual, jumlahStok - jumlahBeli, partsData[3]);
                    arr.add(dataString);
                } else {
                    arr.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    BahanPangan.writeToFile(filePath,arr);
    }

    public static void writeToFile(String fileName, List<String> lines) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (String line : lines) {
                writer.write(line + System.lineSeparator()); // Menambahkan baris baru setelah setiap baris
            }
            writer.close();
            System.out.println("Data berhasil diedit dalam file.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis ke file: " + fileName);
            e.printStackTrace();
        }
    }

    public static String formatCurrencyIDR(double amount) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeID);
        return currencyFormatter.format(amount);
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getId() {
        return id;
    }


    public double getHargaSatuan() {
        return hargaSatuan;
    }


    public double getJumlahBahan() {
        return jumlahBahan;
    }
}
