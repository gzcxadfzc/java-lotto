package gmbs.model;

import gmbs.model.vo.LottoNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TicketTest {

    private static List<LottoNumber> createLottoNumbers(List<Integer> numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        numbers.forEach((number) -> lottoNumbers.add(new LottoNumber(number)));
        return lottoNumbers;
    }

    @ParameterizedTest
    @DisplayName("당첨 숫자와 보너스 숫자를 비교하여 올바른 prize를 반환하는지 확인한다")
    @MethodSource("prizeCheckData")
    void checkPrize(Ticket ticket, Prize expected) {
        Ticket winningTicket = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 4, 5, 6)));
        LottoNumber bonus = new LottoNumber(7);
        Winner winningNumber = new Winner(winningTicket, bonus);

        //when
        Prize actual = ticket.checkPrize(winningNumber);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> prizeCheckData() {
        Ticket firstPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 4, 5, 6)));
        Ticket secondPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 4, 5, 7)));
        Ticket thirdPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 4, 5, 16)));
        Ticket fourthPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 4, 15, 16)));
        Ticket fifthPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 3, 14, 15, 16)));
        Ticket noPrize = new Ticket(() -> createLottoNumbers(List.of(1, 2, 7, 14, 15, 16)));
        return Stream.of(
                Arguments.of(firstPrize, Prize.FIRST),
                Arguments.of(secondPrize, Prize.SECOND),
                Arguments.of(thirdPrize, Prize.THIRD),
                Arguments.of(fourthPrize, Prize.FOURTH),
                Arguments.of(fifthPrize, Prize.FIFTH),
                Arguments.of(noPrize, Prize.LOSER)
        );
    }
}