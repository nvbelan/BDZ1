import java.time.LocalDate;

public class Coinpaper {
        String id;
        LocalDate delinquency;
        String codeMoney;
        String fullname;

        public Coinpaper(String id, LocalDate delinquency, String codeMoney) {
            this.id = id;
            this.delinquency = delinquency;
            this.codeMoney = codeMoney;
        }
    }

