package stage4

import kotlin.math.abs

const val BLACK_PIECE = 'B'
const val WHITE_PIECE = 'W'
const val NO_PIECE = ' '
const val BOARD_SIZE = 8

const val VALID_INPUT = "[a-h][1-8][a-h][1-8]"

fun main() {
    println("Pawns-Only Chess")

    println("First Player's name:")
    val firstPlayer = readLine()!!

    println("Second Player's name:")
    val secondPlayer = readLine()!!

    val player1 = Player(firstPlayer, WHITE_PIECE, "white", 2, 1)
    val player2 = Player(secondPlayer, BLACK_PIECE, "black", 7, -1)

    val board = Board()
    board.print()

    var activePlayer = player1
    val gameState = GameState(null)

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
        // e.g. when input = c6c7 -> from = c6 and to = c7
        val from = Field(input.substring(0, 2))
        val to = Field(input.substring(2))

        // validate fields
        // 'from' field needs to have a piece of active player
        if (board.getPiece(from) != activePlayer.piece) {
            println("No ${activePlayer.color} pawn at $from")
            continue
        }

        try {
            executeMove(board, from, to, activePlayer, gameState)
        } catch (ex: InvalidInput) {
            println("Invalid Input")
            continue
        }

        board.print()

        // continue with other player
        activePlayer = if (activePlayer == player1) player2 else player1
    }
}

// small helper class to encapsulate a field (e.g. "b5")
// it provides properties for rowNumber (1-8), column (a-h), rowIndex (0-7) and columnIndex (0-7)
class Field(private val coord: String) {
    // returns 'a' -' h'
    val column get() = coord[0]

    // return 1 - 8
    val rowNumber get() = coord[1].toString().toInt()

    // return: index of row, "d1" -> row number 1 -> rowIndex 0
    // rowIndex is rowNumber - 1
    val rowIndex get() = rowNumber - 1

    // input: field on the board in form "a3" or "g5"
    // return: index of column, "a4" -> column 'a' -> colIndex 0
    val colIndex get() = column.code - 97 // a = 97 in ASCII

    override fun toString(): String {
        return coord
    }
}

// we have to remember if in the previous move the opponent did a move from his start row
// than we have the opportunity to execute an en passant move
class GameState(var enPassantBeatablePiece: Field?)

// startRow is 2 for white and 7 for black
// rowIncr is +1 for white (because white moves "up"   the board towards increasing row numbers: 1, 2, ..., 8)
//        and -1 for black (because black moves "down" the board towards decreasing row numbers: 8, 7, ..., 1)
class Player(val name: String, val piece: Char, val color: String, val startRow: Int, val rowIncr: Int) {
    fun getOpposingColor(): Char {
        return if (piece == WHITE_PIECE) BLACK_PIECE else WHITE_PIECE
    }
}

// class to wrap our board
// the board is an Array of Arrays of Chars
// we build from bottom to top, that means rowIndex is rowNumber - 1
class Board {
    private val board = arrayOf(
        Array(BOARD_SIZE) { NO_PIECE },
        Array(BOARD_SIZE) { WHITE_PIECE },
        Array(BOARD_SIZE) { NO_PIECE },
        Array(BOARD_SIZE) { NO_PIECE },
        Array(BOARD_SIZE) { NO_PIECE },
        Array(BOARD_SIZE) { NO_PIECE },
        Array(BOARD_SIZE) { BLACK_PIECE },
        Array(BOARD_SIZE) { NO_PIECE }
    )

    fun getPiece(field: Field): Char {
        return board[field.rowIndex][field.colIndex]
    }

    private fun setPiece(field: Field, newPiece: Char) {
        board[field.rowIndex][field.colIndex] = newPiece
    }

    fun movePiece(piece: Char, from: Field, to: Field) {
        removePiece(from)
        setPiece(to, piece)
    }

    fun removePiece(field: Field) {
        setPiece(field, NO_PIECE)
    }

    fun print() {
        val dividerLine = "  +---+---+---+---+---+---+---+---+"

        println(dividerLine)

        for (rowNumber in BOARD_SIZE downTo 1) {
            val row = board[rowNumber - 1] // rowIndex = rowNumber - 1
            println("$rowNumber | " + row.joinToString(" | ") + " |")
            println(dividerLine)
        }

        println("    a   b   c   d   e   f   g   h")
    }
}

// board will be updated when move was valid
fun executeMove(board: Board, from: Field, to: Field, player: Player, gameState: GameState) {
    val isStraightMove = from.column == to.column
    val isDiagonalMove = abs(from.colIndex - to.colIndex) == 1

    val isTargetFieldEmpty = board.getPiece(to) == NO_PIECE
    val isTargetFieldOpposingColor = board.getPiece(to) == player.getOpposingColor()

    // can move from its start row 2 fields, straight ahead, into empty field
    if (to.rowNumber == player.startRow + 2 * player.rowIncr && isStraightMove && isTargetFieldEmpty) {
        board.movePiece(player.piece, from, to)

        // opponent can take this piece in next round
        gameState.enPassantBeatablePiece = to
        return
    }

    // can only move one field straight ahead, into empty field
    if (to.rowNumber == from.rowNumber + player.rowIncr && isStraightMove && isTargetFieldEmpty) {
        board.movePiece(player.piece, from, to)

        gameState.enPassantBeatablePiece = null // chance for en passant is gone!
        return
    }

    // can move one field and capture diagonal black piece
    if (to.rowNumber == from.rowNumber + player.rowIncr && isDiagonalMove && isTargetFieldOpposingColor) {
        board.movePiece(player.piece, from, to)

        gameState.enPassantBeatablePiece = null // chance for en passant is gone!
        return
    }

    // en passant
    if (to.rowNumber == from.rowNumber + player.rowIncr && isDiagonalMove && isTargetFieldEmpty &&
        to.column == gameState.enPassantBeatablePiece?.column) {
        board.movePiece(player.piece, from, to)

        board.removePiece(gameState.enPassantBeatablePiece!!)
        gameState.enPassantBeatablePiece = null
        return
    }

    // everything else is not allowed
    throw InvalidInput()
}

fun isInvalid(input: String): Boolean {
    return !VALID_INPUT.toRegex().matches(input)
}

class InvalidInput: IllegalArgumentException()