package novencia.pricer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MiniPricerTest {

    private static final LocalDate FORECAST_DATE = LocalDate.now();
    private Calendar calendar;
    private MiniPricer miniPricer;
    private RandomVolatilitySign randomeVolatilitySign;

    @Before
    public void setup() {
        calendar = mock(Calendar.class);
        randomeVolatilitySign = mock(RandomVolatilitySign.class);
        when(randomeVolatilitySign.next()).thenReturn(1);
        miniPricer = new MiniPricer(calendar, randomeVolatilitySign);
    }

    @Test
    public void should_forecast_price_for_tomorrow() {
        forecastDateIsFarFromNowByDays(1L);
        Double actualPrice = 100D;
        Double averageVolatility = 10D;


        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, FORECAST_DATE);
        assertThat(estimatePrice, closeTo(110, 0.01));
    }

    @Test
    public void should_forecast_price_for_five_days_later() {
        forecastDateIsFarFromNowByDays(5L);
        Double actualPrice = 8D;
        Double averageVolatility = 50D;

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, FORECAST_DATE);
        assertThat(estimatePrice, closeTo(60.75, 0.01));
    }

    @Test
    public void should_randomly_add_remove_volatility_or_do_nothing() {
        forecastDateIsFarFromNowByDays(3L);
        when(randomeVolatilitySign.next()).thenReturn(1,0,-1);

        Double actualPrice = 8D;
        Double averageVolatility = 50D;

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, FORECAST_DATE);
        assertThat(estimatePrice, closeTo(6, 0.01));
    }

    private void forecastDateIsFarFromNowByDays(long l) {
        when(calendar.getWorkingDaysFrom(FORECAST_DATE)).thenReturn(l);
    }

}