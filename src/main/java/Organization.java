import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Organization {
    List<Coinpaper> list = new ArrayList<>();
    String smallname;
    String fullName;
    String location;
    String telephone;
    String INN;
    String OGRN;
    LocalDate date;

    public Organization(String smallname, String fullName, String location, String telephone, String INN, String OGRN, LocalDate date) {
        this.smallname = smallname;
        this.fullName = fullName;
        this.location = location;
        this.telephone = telephone;
        this.INN = INN;
        this.OGRN = OGRN;
        this.date = date;
    }
}

