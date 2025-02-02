package gmbs.model;

import gmbs.model.generator.LottoGenerator;
import gmbs.model.vo.LottoNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ticket {

    private final List<LottoNumber> numbers;

    public Ticket(LottoGenerator generator) {
        this.numbers = generator.getNumbers();
    }

    public boolean hasValue(LottoNumber value) {
        return numbers.contains(value);
    }

    public Prize checkPrize(Ticket winningNumbers, LottoNumber bonusNumber) {
        int match = checkMatches(winningNumbers);
        boolean hasBonus = hasValue(bonusNumber);
        return Prize.find(match, hasBonus);
    }

    private int checkMatches(Ticket anotherTicket) {
        int matchCount = 0;
        List<LottoNumber> anotherNumbers = anotherTicket.getLottoNumbers();
        for (LottoNumber anotherNumber : anotherNumbers) {
            if (this.numbers.contains(anotherNumber)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    public List<LottoNumber> getLottoNumbers() {
        return numbers;
    }

    public List<Integer> getLottoNumberValues() {
        List<Integer> numberValues = new ArrayList<>();
        for (LottoNumber number : numbers) {
            numberValues.add(number.getValue());
        }
        return numberValues;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Ticket ticket = (Ticket) o;
        return numbers.equals(ticket.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }
}
