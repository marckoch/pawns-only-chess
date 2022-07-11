package stage1

const val BLACK_PIECE = 'B'
const val WHITE_PIECE = 'W'
const val NO_PIECE = ' '
const val BOARD_SIZE = 8

fun main() {
    val board = buildInitialBoard()

    print(board)
}

fun buildInitialBoard(): MutableList<MutableList<Char>> {
    val board = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { NO_PIECE } }

    board[1] = MutableList(BOARD_SIZE) { WHITE_PIECE }
    board[6] = MutableList(BOARD_SIZE) { BLACK_PIECE }

    return board
}

fun print(board: List<List<Char>>) {
    val dividerLine = "  +---+---+---+---+---+---+---+---+"

    println("Pawns-Only Chess")
    println(dividerLine)

    for (rowNumber in BOARD_SIZE downTo 1) {
        val row = board[rowNumber - 1]
        println("$rowNumber | " + row.joinToString(" | ") + " |")
        println(dividerLine)
    }

    println("    a   b   c   d   e   f   g   h")
}