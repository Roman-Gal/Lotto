# Java Lotto Simulator

A console simulation of a 6-of-49 lottery, written in Java with no external dependencies. Choose a ticket and watch the simulator find a matching draw, then generate ten additional draws.

## What it demonstrates

- Validation of six distinct integers in the range 1–49.
- A reusable pseudorandom generator and a compact 49-bit representation of each ticket.
- Order-independent comparison without allocating a collection for every draw.
- Sorted winning numbers displayed as terminal tiles.
- A count of the draws required to reach a match.

## Run

Install JDK 11 or newer and run the source file from the repository root.

```sh
java -Dfile.encoding=UTF-8 src/LOTOTREJA.java
```

Enter six numbers separated by spaces or newlines, for example `1 2 3 4 5 49`. The program retries invalid values and ends cleanly if input closes before the ticket is complete.

The existing `LOTOTREJA` entry-point name is retained so previous launch commands continue to work.

## Example output

```text
Виграшна комбінація
╭────╮ ╭────╮ ╭────╮ ╭────╮ ╭────╮ ╭────╮
│ 01 │ │ 02 │ │ 03 │ │ 04 │ │ 05 │ │ 49 │
╰────╯ ╰────╯ ╰────╯ ╰────╯ ╰────╯ ╰────╯
Кількість спроб: 65871
```

The attempt count and following draws vary between runs. There are 13,983,816 possible six-number combinations, so a run may take millions of draws.

## Windows PowerShell

Use UTF-8 for the console before starting the application.

```powershell
chcp 65001 > $null
[Console]::InputEncoding = [System.Text.UTF8Encoding]::new($false)
[Console]::OutputEncoding = [System.Text.UTF8Encoding]::new($false)
$OutputEncoding = [Console]::OutputEncoding
java '-Dfile.encoding=UTF-8' .\src\LOTOTREJA.java
```

## Compile and run

```sh
javac -encoding UTF-8 --release 11 -d out src/LOTOTREJA.java
java -Dfile.encoding=UTF-8 -cp out LOTOTREJA
```

The application can also run in VS Code with the Extension Pack for Java or in IntelliJ IDEA. Its interface remains in Ukrainian.

## Code structure

All lottery logic is in `src/LOTOTREJA.java`. Input, draw generation and terminal rendering are separate methods. `long` stores both the ticket bits and the attempt counter.
