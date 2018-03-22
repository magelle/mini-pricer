package novencia.pricer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class CalendarTest {

    private Calendar calendar;

    @Before
    public void setup() {
        calendar = new Calendar();
    }

    @Test
    public void should_says_today() {
        assertThat(calendar.today(), is(LocalDate.now()));
    }

    @Test
    public void should_calculate_how_many_workingdays_a_date_is_from_now() {
        long daysFromNow = calendar.getWorkingDaysFrom(LocalDate.now().plusDays(7));
        assertThat(daysFromNow, is(5L));
    }

    @Test
    public void should_take_account_public_holiday_when_getting_working_days() {
        Calendar calendar = spy(this.calendar);
        LocalDate _31_december = LocalDate.of(2018, 12, 31);
        when(calendar.today()).thenReturn(_31_december);

        long daysFromNow = calendar.getWorkingDaysFrom(_31_december.plusDays(2));

        assertThat(daysFromNow, is(1L)); // only january the second
    }

}