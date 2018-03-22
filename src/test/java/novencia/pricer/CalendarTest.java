package novencia.pricer;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalendarTest {

    @Test
    public void should_calculate_how_many_days_a_date_is_from_now() {
        Calendar calendar = new Calendar();
        long daysFromNow = calendar.getDaysFrom(LocalDate.now().plusDays(5));
        assertThat(daysFromNow, is(5L));
    }

}