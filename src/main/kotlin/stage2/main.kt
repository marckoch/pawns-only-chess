package stage2

const val BLACK_PIECE = 'B'
const val WHITE_PIECE = 'W'
const val NO_PIECE = ' '
const val BOARD_SIZE = 8

const val VALID_INPUT = "[a-h][1-8][a-h][1-8]"

fun main() {
    val board = buildInitialBoard()

    println("Pawns-Only Chess")

    println("First Player's name:")
    val firstPlayer = readLine()!!

    println("Second Player's name:")
    val secondPlayer = readLine()!!

    print(board)

    var activePlayer = firstPlayer

    while (true) {
        println("${activePlayer}'s turn:")

        val input = readLine()!!
        if (input == "exit") {
            println("Bye!")
            return
        } else if (isInvalid(input)) {
            println("Invalid Input")
            continue
        }

        // continue with other player
        activePlayer = if (activePlayer == firstPlayer) secondPlayer else firstPlayer
    }
}

fun isInvalid(input: String): Boolean {
    return !VALID_INPUT.toRegex().matches(input)
}

fun buildInitialBoard(): MutableList<MutableList<Char>> {
    val board = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { NO_PIECE } }

    board[1] = MutableList(BOARD_SIZE) { WHITE_PIECE }
    board[6] = MutableList(BOARD_SIZE) { BLACK_PIECE }

    return board
}

fun print(board: List<List<Char>>) {
    val dividerLine = "  +---+---+---+---+---+---+---+---+"

    println(dividerLine)

    for (rowNumber in BOARD_SIZE downTo 1) {
        val row = board[rowNumber - 1]
        println("$rowNumber | " + row.joinToString(" | ") + " |")
        println(dividerLine)
    }

    println("    a   b   c   d   e   f   g   h")
}