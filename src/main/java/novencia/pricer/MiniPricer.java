package novencia.pricer;

import java.time.LocalDate;
import java.util.stream.DoubleStream;

public class MiniPricer {

    private final Calendar calendar;
    private RandomVolatilitySign randomeVolatilitySign;

    public MiniPricer(Calendar calendar, RandomVolatilitySign randomeVolatilitySign) {
        this.calendar = calendar;
        this.randomeVolatilitySign = randomeVolatilitySign;
    }

    public Double forecastPrice(Double actualPrice, Double averageVolatility, LocalDate forecastDate) {
        long workingDayCount = calendar.getWorkingDaysFrom(forecastDate);
        Double globalVolatility = computeGlobalVolatility(averageVolatility, workingDayCount);
        return actualPrice * globalVolatility ;
    }

    private Double computeGlobalVolatility(Double averageVolatility, long workingDayCount) {
        return DoubleStream.iterate(0, n -> n + 3)
                    .limit(workingDayCount)
                    .map(d -> randomVolatilityFromAverage(averageVolatility))
                    .map(this::toRate)
                    .map(dailyVolatilityRate -> 1 + dailyVolatilityRate)
                    .reduce((a, b) -> a * b).orElse(1D);
    }

    private double toRate(double dailyVolatility) {
        return dailyVolatility / 100;
    }

    private double randomVolatilityFromAverage(Double averageVolatility) {
        return randomeVolatilitySign.next() * averageVolatility;
    }

}
