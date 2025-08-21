package intae.chapter02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @Test
    @DisplayName("첫 번째, 10 번째 영화, 월요일 10시 ~ 11시 59분, 수요일 10시 ~ 20시 59분에 상영하는 영화는 800원 할인 된다.")
    void AmountDiscountPolicy() {
        // given

        Money won800 = Money.wons(800);
        Money won10_000 = Money.wons(10_000);

        Movie movie = new Movie("아바타",
                Duration.ofMinutes(120),
                won10_000,
                new AmountDiscountPolicy(
                        won800,
                        new SequenceCondition(1),
                        new SequenceCondition(10),
                        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
                )
        );

        LocalDateTime monday11 = LocalDateTime.of(2025, 8, 18, 11, 0);
        LocalDateTime tuesday = LocalDateTime.of(2025, 8, 19, 11, 0);

        Screening screening1 = new Screening(movie, 2, monday11);
        Screening screening2 = new Screening(movie, 2, tuesday);
        Screening screening3 = new Screening(movie, 10, tuesday);

        // when
        Reservation reserve1 = screening1.reserve(new Customer(), 1);
        Reservation reserve2 = screening2.reserve(new Customer(), 1);
        Reservation reserve3 = screening3.reserve(new Customer(), 1);

        // then
        assertThat(reserve1.getFee()).isEqualTo(won10_000.minus(won800).times(1));
        assertThat(reserve2.getFee()).isEqualTo(won10_000.times(1));
        assertThat(reserve3.getFee()).isEqualTo(won10_000.minus(won800).times(1));

    }
}