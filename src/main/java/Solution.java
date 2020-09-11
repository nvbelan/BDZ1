import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) throws IOException {
        LocalDate dateNow = LocalDate.now();
        List<Organization> organizationList = readJSon("json.json");

        for (Organization a : organizationList) {
            System.out.println(a.smallname + " Дата основания " + a.date.format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        }
        writeCreate(organizationList, dateNow);
        giveMeData(organizationList, "29.08.99");
        finalStation(organizationList,"RUB");

    }

    public static List<Organization> readJSon(String file) throws FileNotFoundException {

        Gson gson = new Gson();
        String s = "";
        FileReader reader = new FileReader(new File(file));
        Scanner scan = new Scanner(reader);
        while (scan.hasNext()) {
            s = s + scan.nextLine();
        }
        return gson.fromJson(s, new TypeToken<List<Organization>>() {
        }.getType());

    }

    public static void writeCreate(List<Organization> listN, LocalDate dateNow) {
        List<String> finList = listN.stream()
                .flatMap(x -> x.list.stream()
                        .filter(coin -> {
                            return dateNow.isAfter(coin.delinquency);
                        })
                        .flatMap(z -> Stream.of(String.format("%s %s %s", z.id, z.delinquency.toString(), z.fullname))))
                .collect(Collectors.toList());
        long count = finList.size();
        if (count > 0) {
            System.out.println("Просроченные ценные бумаги:");
            System.out.println(" Код / Дата / Полное название компании");
            finList.forEach(System.out::println);
            System.out.println("Всего: " + count);
        } else {
            System.out.println("Нет элементов");
        }
        System.out.println();
    }

    public static void giveMeData(List<Organization> listN, String userDate) {
        String dateFormat = "";
        userDate = userDate.replaceAll(",|/", ".");
        if (userDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            dateFormat = "dd.MM.yyyy";

        } else if (userDate.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            dateFormat = "dd.MM.yy";
        }
        LocalDate userD = LocalDate.parse(userDate, DateTimeFormatter.ofPattern(dateFormat));
        if (userD.getYear() > LocalDate.now().getYear()) {
            userD = userD.minusYears(100);
        }
        final LocalDate j = userD;
        listN.stream().filter(coin -> {
            return j.isBefore(coin.date);
        })
                .forEach(x -> System.out.println(x.fullName + " " + x.date.toString()));

    }

    public static void finalStation(List<Organization> listN, String money) {
        listN.stream()
                .flatMap(x -> x.list.stream().filter(coin -> {
                    return coin.codeMoney.equals(money);
                }))
                .forEach(y -> System.out.println(y.id + " " + y.codeMoney));
    }

}
