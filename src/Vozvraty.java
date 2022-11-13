import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class Vozvraty {

    static String string;

    public static void splitLists () throws IOException {
        split("example1@example.ru", "Город1", "Город2", "Город3" );
        split("example2@example.ru", "Город4", "Город5", "Город6");
        split("example3@example.ru", "Город7", "Город7", "Город9");

    }
    public static void split (String email, String...city) throws IOException {
        ArrayList<String> cityList = new ArrayList<>();
        for (int i = 0; i < Copy.vozvraty.size(); i++) {
            for (int j = 0; j < city.length; j++) {
                if (Copy.vozvraty.get(i).contains(city[j])) {
                    cityList.add(Copy.vozvraty.get(i));
                    Copy.vozvraty.remove(Copy.vozvraty.get(i));
                    i--;
                }
            }
        }
        if (!(cityList.isEmpty())){
            createText(cityList, email);
        }
    }

    public static void createText (ArrayList<String> list, String email) throws IOException {

        StringBuilder bd = new StringBuilder();
        String [] str;
        for (int i=0; i< list.size(); i++){
            bd.append("%0D%0A");
            str = list.get(i).split("\t");
            for (int j=0; j<str.length; j++){
                bd.append(str[j]);
                bd.append("%20");
            }
        }
        string = bd.toString();

        createMail(email);
    }

    public static void createMail (String email) throws IOException {
        String text = "mailto:"+email+"?subject=возвраты&body=Вернулись%20переводы%0D%0A"+string;
        Desktop.getDesktop().mail(URI.create(text));
    }
}
