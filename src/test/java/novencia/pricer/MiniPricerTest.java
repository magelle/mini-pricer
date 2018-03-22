package novencia.pricer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class MiniPricerTest {

    private MiniPricer miniPricer;

    @Before
    public void setup() {
        miniPricer = new MiniPricer(new Calendar());
    }

    @Test
    public void should_forecast_price_for_tomorrow() {
        Double actualPrice = 100D;
        Double averageVolatility = 10D;
        LocalDate forecastDate = LocalDate.now().plusDays(1);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(110, 0.01));
    }

    @Test
    public void should_forecast_price_for_five_days_later() {
        Double actualPrice = 8D;
        Double averageVolatility = 50D;
        LocalDate forecastDate = LocalDate.now().plusDays(5);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(60.75, 0.01));
    }

}
