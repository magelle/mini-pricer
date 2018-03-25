package novencia.pricer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MiniPricerIntegrationTest {

    private static final LocalDate A_MONDAY = LocalDate.of(2018, 3, 19);
    private static final LocalDate _13_OF_DECEMBER = LocalDate.of(2018, 12, 31);

    private MiniPricer miniPricer;
    private Calendar calendar;
    private RandomVolatilitySign randomeVolatilitySign;

    @Before
    public void setup() {
        calendar = spy(new Calendar());
        randomeVolatilitySign = mock(RandomVolatilitySign.class);
        when(randomeVolatilitySign.next()).thenReturn(1);
        miniPricer = new MiniPricer(calendar, randomeVolatilitySign);
    }

    @Test
    public void should_forecast_price_for_tomorrow() {
        todayIs(A_MONDAY);

        Double actualPrice = 100D;
        Double averageVolatility = 10D;
        LocalDate forecastDate = A_MONDAY.plusDays(1);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(110, 0.01));
    }

    @Test
    public void should_forecast_price_for_five_days_later() {
        todayIs(A_MONDAY);
        Double actualPrice = 8D;
        Double averageVolatility = 50D;
        LocalDate forecastDate = A_MONDAY.plusDays(5);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(60.75, 0.01));
    }

    @Test
    public void should_not_take_account_of_weekend() {
        todayIs(A_MONDAY);
        Double actualPrice = 4D;
        Double averageVolatility = 50D;
        LocalDate forecastDate = A_MONDAY.plusDays(7);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(30.375, 0.01));
    }

    @Test
    public void should_not_take_account_of_public_holiday_such_as_first_january() {
        todayIs(_13_OF_DECEMBER);
        Double actualPrice = 4D;
        Double averageVolatility = 50D;
        LocalDate forecastDate = _13_OF_DECEMBER.plusDays(2);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(6, 0.01));
    }

    @Test
    public void should_randomly_add_remove_volatility_or_do_nothing() {
        todayIs(A_MONDAY);
        when(randomeVolatilitySign.next()).thenReturn(1,0,-1);

        Double actualPrice = 8D;
        Double averageVolatility = 50D;
        LocalDate forecastDate = A_MONDAY.plusDays(3);

        Double estimatePrice = miniPricer.forecastPrice(actualPrice, averageVolatility, forecastDate);
        assertThat(estimatePrice, closeTo(6, 0.01));
    }

    private void todayIs(LocalDate date) {
        when(calendar.today()).thenReturn(date);
    }

}
