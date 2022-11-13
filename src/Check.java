import java.io.*;
import java.util.ArrayList;

public class Check {
    public static void check(ArrayList<String> startList, String fileName) throws IOException {
        try {
            if (startList.size() > 1) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Vika\\Desktop\\Новая папка\\"+fileName+".txt"))));
                for (int i = 0; i < startList.size(); i++) {
                    out.write(startList.get(i) + "\n");
                    out.flush();
                }
                System.out.println("Создан файл - "+fileName);
                out.close();
            }
        }catch(IOException ex)
            {
                System.err.println(ex);
            }
        }
    }

