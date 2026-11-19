import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.OptionalLong;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Simulates independent six-of-49 lottery draws for a user-selected ticket.
 * Each number occupies one bit, keeping the simulation free of per-draw lists.
 */
public class LOTOTREJA {
    private static final int TICKET_SIZE = 6;
    private static final int MAX_NUMBER = 49;
    private static final int DRAWS_AFTER_WIN = 10;

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            OptionalLong ticket = readTicket(input);
            if (ticket.isEmpty()) {
                System.out.println("Ввід завершено до заповнення квитка.");
                return;
            }

            simulate(ticket.getAsLong(), new Random());
        }
    }

    private static OptionalLong readTicket(Scanner input) {
        System.out.printf("Введи %d різних чисел від 1 до %d.%n", TICKET_SIZE, MAX_NUMBER);

        long ticket = 0;
        int numbersEntered = 0;
        while (numbersEntered < TICKET_SIZE) {
            System.out.printf("Число %d з %d:%n", numbersEntered + 1, TICKET_SIZE);
            if (!input.hasNext()) {
                return OptionalLong.empty();
            }
            if (!input.hasNextInt()) {
                input.next();
                System.out.println("Потрібно ввести ціле число.");
                continue;
            }

            int number = input.nextInt();
            if (number < 1 || number > MAX_NUMBER) {
                System.out.printf("Число має бути від 1 до %d.%n", MAX_NUMBER);
                continue;
            }

            long bit = numberBit(number);
            if ((ticket & bit) != 0) {
                System.out.println("Це число вже є у квитку.");
                continue;
            }

            ticket |= bit;
            numbersEntered++;
        }
        return OptionalLong.of(ticket);
    }

    private static void simulate(long ticket, Random random) {
        long attempts = 0;
        long draw;
        do {
            draw = drawNumbers(random);
            attempts++;
        } while (draw != ticket);

        printWinningTicket(ticketNumbers(draw), attempts);
        System.out.printf("%nНаступні %d розіграшів:%n", DRAWS_AFTER_WIN);
        for (int i = 0; i < DRAWS_AFTER_WIN; i++) {
            System.out.println(ticketNumbers(drawNumbers(random)));
        }
    }

    private static void printWinningTicket(List<Integer> numbers, long attempts) {
        StringJoiner balls = new StringJoiner(" ");
        for (int number : numbers) {
            balls.add(String.format(Locale.ROOT, "│ %02d │", number));
        }

        System.out.printf("%nВиграшна комбінація%n");
        System.out.println(String.join(" ", Collections.nCopies(numbers.size(), "╭────╮")));
        System.out.println(balls);
        System.out.println(String.join(" ", Collections.nCopies(numbers.size(), "╰────╯")));
        System.out.printf("Кількість спроб: %d%n", attempts);
    }

    private static long drawNumbers(Random random) {
        long draw = 0;
        while (Long.bitCount(draw) < TICKET_SIZE) {
            draw |= numberBit(random.nextInt(MAX_NUMBER) + 1);
        }
        return draw;
    }

    private static long numberBit(int number) {
        // Each of the 49 possible numbers maps to a distinct bit in a long.
        return 1L << (number - 1);
    }

    private static List<Integer> ticketNumbers(long ticket) {
        List<Integer> numbers = new ArrayList<>(TICKET_SIZE);
        for (int number = 1; number <= MAX_NUMBER; number++) {
            if ((ticket & numberBit(number)) != 0) {
                numbers.add(number);
            }
        }
        return numbers;
    }
}
