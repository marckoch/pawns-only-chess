# Jetbrains Academy - Pawns Only Chess

My solutions for the Jetbrains Academy Problem 'Pawns only chess'

https://hyperskill.org/projects/182

The solution is build up step by step over several stages. 
Stage 1 is the first and simple one. The following stages 
build up on the previous stages and get more and more advanced.
There are five stages in total.

Because each stage is completely independent of the previous one,
IntelliJ might show some warnings about duplicated code between 
the stages.

## Stage 1

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/182/stages/922/implement)

Prints the initial board

just execute this:

    gradle -PmainClass=stage1.MainKt run --console=plain
    
    Pawns-Only Chess
      +---+---+---+---+---+---+---+---+
    8 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
    7 | B | B | B | B | B | B | B | B |
      +---+---+---+---+---+---+---+---+
    6 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
    5 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
    4 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
    3 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
    2 | W | W | W | W | W | W | W | W |
      +---+---+---+---+---+---+---+---+
    1 |   |   |   |   |   |   |   |   |
      +---+---+---+---+---+---+---+---+
        a   b   c   d   e   f   g   h

## Stage 2

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/182/stages/923/implement)

Add game loop and validate user input

just execute this:

    gradle -PmainClass=stage2.MainKt run --console=plain

## Stage 3

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/182/stages/924/implement)

Add movement of pieces and print board after each move

just execute this:

    gradle -PmainClass=stage3.MainKt run --console=plain

## Stage 4

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/182/stages/925/implement)

Add capturing of pieces

just execute this:

    gradle -PmainClass=stage4.MainKt run --console=plain

## Stage 5

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/182/stages/926/implement)

Add check for winning condition

just execute this:

    gradle -PmainClass=stage5.MainKt run --console=plain
