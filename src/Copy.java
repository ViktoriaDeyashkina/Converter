import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

public class Copy {
    static String path1 = "C:\\Users\\...\\банк.txt";
    static String path2 = "C:\\Users\\...\\почта.txt";
    static String path3 = "C:\\Users\\...\\возвраты банк.txt";
    static String path4 = "C:\\Users\\...\\возвраты почта.txt";
    static String path5 = "C:\\Users\\...\\возвраты.txt";

    static ArrayList<String> listBank = new ArrayList<>();
    static ArrayList<String> listPochta = new ArrayList<>();
    static ArrayList<String> listBankVoz = new ArrayList<>();
    static ArrayList<String> listPochtaVoz = new ArrayList<>();
    static ArrayList<String> vozvraty = new ArrayList<>();

    static XSSFWorkbook workbook;

    static int choice;

    static {
        System.out.println("Выберете: 1 - первичные переводы, 2 - возвраты");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            System.out.println("Ничего не выбрано");
        }
    }

    public static void main(String[] args) throws IOException {
        switch (choice) {
            case 1:
                readerFile(listBank, path1);
                readerFile(listPochta, path2);
                readerFile(listBankVoz, path3);
                readerFile(listPochtaVoz, path4);
                splitListsAll();
                Check.check(listBank, "ощибки банк");
                Check.check(listPochta, "ошибки почта");
                Check.check(listBankVoz, "ошибки банк повторы");
                Check.check(listPochtaVoz, "ошибки почта повторы");
                break;
            case 2:
                readerFile(vozvraty, path5);
                Vozvraty.splitLists();
                Check.check(vozvraty, "ошибки возвраты");
                break;
        }
    }

    public static void readerFile(ArrayList<String> list, String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(path));
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
            new FileReader(path).close();
            scanner.close();
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    public static void splitListsAll() throws IOException {
        splitList("example1@example.ru", "Город1", "Город2", "Город3" );
        splitList("example2@example.ru", "Город4", "Город5", "Город6");
        splitList("example3@example.ru", "Город7", "Город7", "Город9");

    }

    public static void splitList(String email, String... city) throws IOException {
        ArrayList<String> cityList1 = new ArrayList<>();
        ArrayList<String> cityList2 = new ArrayList<>();
        ArrayList<String> cityList3 = new ArrayList<>();
        ArrayList<String> cityList4 = new ArrayList<>();

        String city1 = city[0];

        workbook = new XSSFWorkbook();

        spliting(listBank, cityList1, city, "Банковские переводы");
        spliting(listPochta, cityList2, city, "Почтовые переводы");
        spliting(listBankVoz, cityList3, city, "Повторы банком");
        spliting(listPochtaVoz, cityList4, city, "Повторы почтой");

        if (workbook.getNumberOfSheets()>0){
            writeBook(city1);
        }


        createMails(email);

    }

    public static void spliting(ArrayList<String> startList, ArrayList<String> finishList, String[] city, String sheetName) {
        for (int i = 0; i < startList.size(); i++) {
            for (int j = 0; j < city.length; j++) {
                if (startList.get(i).contains(city[j])) {
                    finishList.add(startList.get(i));
                    startList.remove(startList.get(i));
                    i--;
                }
            }
        }
        if (!(finishList.isEmpty())) {
            new ExcelWorker(startList, finishList, sheetName).start();
        }
    }

    public static void writeBook(String city) {
        try (FileOutputStream out = new FileOutputStream(String.format("C:\\Users\\Vika\\Desktop\\Новая папка\\%s.xlsx", city))) {
                workbook.write(out);
        } catch (IOException e) {
            System.out.printf("Файл %s не создан%n", city);
        }
    }

    public static void createMails(String email) throws IOException {
        if (email.contains("@")){
            Desktop.getDesktop().mail(URI.create("mailto:" + email + "?subject=переводы&body=Высылаю%20список%20переводов,%20сделанных%20в%20этом%20месяце."));
        }
    }
}



