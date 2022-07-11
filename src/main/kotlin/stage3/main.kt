package stage3

const val BLACK_PIECE = 'B'
const val WHITE_PIECE = 'W'
const val NO_PIECE = ' '
const val BOARD_SIZE = 8

const val VALID_INPUT = "[a-h][1-8][a-h][1-8]"

class Player(val name: String, val token: Char, val color: String)

fun main() {
    val board = buildInitialBoard()

    println("Pawns-Only Chess")

    println("First Player's name:")
    val firstPlayer = readLine()!!

    println("Second Player's name:")
    val secondPlayer = readLine()!!

    val player1 = Player(firstPlayer, WHITE_PIECE, "white")
    val player2 = Player(secondPlayer, BLACK_PIECE, "black")

    print(board)

    var activePlayer = player1

    while (true) {
        println("${activePlayer.name}'s turn:")

        val input = readLine()!!
        if (input == "exit") {
            println("Bye!")
            return
        } else if (isInvalid(input)) {
            println("Invalid Input")
            continue
        }

        // determine from - to field
        val from = input.substring(0, 2)
        val to = input.substring(2)

        // validate fields
        // 'from' field needs to have a piece of active player
        if (getField(board, from) != activePlayer.token) {
            println("No ${activePlayer.color} pawn at $from")
            continue
        }

        if (!isValidMove(board, from, to, activePlayer)) {
            println("Invalid Input")
            continue
        }

        // move is valid, so now update board
        setField(board, from, NO_PIECE)
        setField(board, to, activePlayer.token)

        print(board)

        // continue with other player
        activePlayer = if (activePlayer == player1) player2 else player1
    }
}

fun isValidMove(board: List<List<Char>>, from: String, to: String, activePlayer: Player): Boolean {
    // piece has to remain in one column/file, e.g. e2e3
    if (from[0] != to[0]) {
        return false
    }

    // target field needs to be empty
    if (getField(board, to) != NO_PIECE) {
        return false
    }

    val toRow = to[1].toString().toInt()
    val fromRow = from[1].toString().toInt()

    if (activePlayer.token == WHITE_PIECE) {
        // move from start row can be 2 fields
        if (fromRow == 2 && toRow == 4) {
            return true
        }
        // white can only move up, that means 'row' has to increase by 1
        if (toRow - fromRow == 1) {
            return true
        }
    } else {
        // move from start row can be 2 fields
        if (fromRow == 7 && toRow == 5) {
            return true
        }
        // black can only move down, that means 'row' has to decrease by 1
        if (fromRow - toRow == 1) {
            return true
        }
    }

    // everything else is not allowed
    return false
}

// field: is something like "a3" or "f5"
fun getField(board: List<List<Char>>, field: String): Char {
    val file = field[0] // a-h
    val rank = field[1].toString().toInt() // 1-8
    return board[rank - 1][file.code - 97]
}

// field: is something like "a3" or "f5"
fun setField(board: List<MutableList<Char>>, field: String, newToken: Char) {
    val file = field[0] // a-h
    val rank = field[1].toString().toInt() // 1-8
    board[rank - 1][file.code - 97] = newToken
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