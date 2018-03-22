package novencia.pricer;

import java.time.LocalDate;

public class MiniPricer {

    private final Calendar calendar;

    public MiniPricer(Calendar calendar) {
        this.calendar = calendar;
    }

    public Double forecastPrice(Double actualPrice, Double averageVolatility, LocalDate forecastDate) {
        long days = calendar.getWorkingDaysFrom(forecastDate);
        double estimateVariation = Math.pow((1 + averageVolatility / 100), days);
        return actualPrice * estimateVariation;
    }

}
