package novencia.pricer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MiniPricerIntegrationTest {

    private static final LocalDate A_MONDAY = LocalDate.of(2018, 3, 19);
    private static final LocalDate _13_OF_DECEMBER = LocalDate.of(2018, 12, 31);

    private MiniPricer miniPricer;
    private Calendar calendar;

    @Before
    public void setup() {
        calendar = spy(new Calendar());
        miniPricer = new MiniPricer(calendar);
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

    private void todayIs(LocalDate date) {
        when(calendar.today()).thenReturn(date);
    }

}
