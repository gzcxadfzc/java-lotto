package gmbs.model;

import gmbs.model.generator.UserInputLottoGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserInputLottoGeneratorTest {

    private static final int MIN = 1;
    private static final int MAX = 45;

    @ParameterizedTest
    @DisplayName("자연수가 아닌 문자가 포함된 리스트로 객체 생성시 예외 발생시킨다")
    @MethodSource("invalidExpressions")
    void exceptionByInvalidExpression(List<String> userInput) {
        assertThatThrownBy(() -> new UserInputLottoGenerator(userInput)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[error] is not number");
    }

    private static Stream<Arguments> invalidExpressions() {
        return Stream.of(
                Arguments.of(List.of("1", "a", "3", "4")),
                Arguments.of(List.of("1", "2f", "3", "4")),
                Arguments.of(List.of("1", "2l", "3", "4")),
                Arguments.of(List.of("1", "-1", "3", "4"))
        );
    }

    @ParameterizedTest
    @DisplayName("중복된 문자가 있는 리스트로 객체 생성시 예외 발생시킨다")
    @MethodSource("overlap")
    void exceptionByOverlap(List<String> userInput) {
        assertThatThrownBy(() -> new UserInputLottoGenerator(userInput)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[error] has overlap");
    }

    private static Stream<Arguments> overlap() {
        return Stream.of(
                Arguments.of(List.of("1", "1", "3", "4", "5", "6")),
                Arguments.of(List.of("1", "1", "3", "4", "5", "6", "7")),
                Arguments.of(List.of("1", "1", "3", "4"))
        );
    }

    @ParameterizedTest
    @DisplayName("길이가 6이 아닌 리스트로 객체 생성시 예외 발생시킨다")
    @MethodSource("invalidLength")
    void exceptionByInvalidLength(List<String> userInput) {
        assertThatThrownBy(() -> new UserInputLottoGenerator(userInput)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[error] invalid length");
    }

    private static Stream<Arguments> invalidLength() {
        return Stream.of(
                Arguments.of(List.of("1", "2", "3", "4", "5", "6", "7")),
                Arguments.of(List.of("1", "2", "3", "4", "5"))
        );
    }

    @ParameterizedTest
    @DisplayName("리스트의 요소가 모두 1 ~ 45 범위가 아닌 리스트로 객체 생성 시 예외 발생시킨다")
    @MethodSource("invalidRange")
    void exceptionByInvalidRange(List<String> userInput) {
        assertThatThrownBy(() -> new UserInputLottoGenerator(userInput)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[error] invalid number range");
    }

    private static Stream<Arguments> invalidRange() {
        return Stream.of(
                Arguments.of(List.of(Integer.toString(MIN - 1), "1", "3", "4", "5", "6")),
                Arguments.of(List.of("1", "2", "3", "4", "5", Integer.toString(MAX + 1)))
        );
    }
}
