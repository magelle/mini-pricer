package novencia.pricer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.function.Predicate;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Arrays.asList;

public class Calendar {

    private static final Collection<DayOfWeek> WEEKEND = asList(SATURDAY, SUNDAY);
    private static final Collection<DayMonth> PUBLIC_HOLIDAYS = asList(
            new DayMonth(1, 1),
            new DayMonth(1, 5),
            new DayMonth(5, 8)
    );

    public LocalDate today() {
        return LocalDate.now();
    }

    public long getWorkingDaysFrom(LocalDate forecastDate) {
        return countDaysUpTo(forecastDate, this::isWorkingDays);
    }

    private boolean isWorkingDays(LocalDate date) {
        return isWeekDay(date) && !isPublicHoliday(date);
    }

    private boolean isWeekDay(LocalDate date) {
        return !isWeekend(date);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return WEEKEND.contains(dayOfWeek);
    }

    private boolean isPublicHoliday(LocalDate date) {
        DayMonth dayMonth = new DayMonth(date.getDayOfMonth(), date.getMonthValue());
        return PUBLIC_HOLIDAYS.contains(dayMonth);
    }

    private int countDaysUpTo(LocalDate forecastDate, Predicate<LocalDate> predicate) {
        int daysCount = 0;
        for (LocalDate date = today(); date.isBefore(forecastDate); date = date.plusDays(1))
            if(predicate.test(date))
                daysCount++;
        return daysCount;
    }

    private static class DayMonth {
        private int day; // from 1 to 31
        private int month; // from 1 to 12


        private DayMonth(int day, int month) {
            this.day = day;
            this.month = month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DayMonth dayMonth = (DayMonth) o;
            return day == dayMonth.day &&
                    month == dayMonth.month;
        }
    }
}