package novencia.pricer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Calendar {
    public long getDaysFrom(LocalDate forecastDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), forecastDate);
    }
}