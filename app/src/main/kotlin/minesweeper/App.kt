
package minesweeper

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val filledOutBoard = createFilledBoard()
    val playingBoard = createPlayingBoard()

    addMinesToBoard(filledOutBoard,15)
    checkNeighbours(filledOutBoard)

    var game = true
    while (game) {
        printBoard(filledOutBoard)
        printBoard(playingBoard)
        print("Give a col: ")
        val col = readln().toInt()
        print("Give a row: ")
        val row = readln().toInt()

        if (filledOutBoard[col][row] == "X ") {
            println("Game Over")
            game = false
        }

        floodFill(playingBoard, filledOutBoard, col, row)

    }
}

fun createFilledBoard(): MutableList<MutableList<String>> {
    val board = MutableList(12) { MutableList(22) {". "}}
    for (i in 0 until board.size) {
        for (j in 0 until board[i].size) {
            if (i == 0 || i == 11 || j == 0 || j == 21) {
                board[i][j] = ""
            }
        }
    }
    return board
}

fun createPlayingBoard(): MutableList<MutableList<String>> {
    val board = MutableList(11) { MutableList(21) {"+ "}}
    for (i in 0 until board.size) {
        for (j in 0 until board[i].size) {
            if (i == 0 || i == 11 || j == 0 || j == 21) {
                board[i][j] = ""
            }
        }
    }
    return board
}

fun addMinesToBoard(board : MutableList<MutableList<String>>, num : Int) {
    var numOfMines = num
    while (numOfMines > 0) {
        val randomCol = (1..10).random()
        val randomRow = (1..20).random()
        if (board[randomCol][randomRow] == ". ") {
            board[randomCol][randomRow] = "X "
            numOfMines--
        }
    }
}

fun checkNeighbours(board : MutableList<MutableList<String>>) {
    for (i in 1 until board.size-1) {
        for (j in 1 until board[i].size-1) {
            var numberOfLocalMines = 0
            if (board[i][j] == ". ") {
                if (board[i][j + 1] == "X ") numberOfLocalMines++
                if (board[i + 1][j + 1] == "X ") numberOfLocalMines++
                if (board[i + 1][j] == "X ") numberOfLocalMines++
                if (board[i + 1][j - 1] == "X ") numberOfLocalMines++
                if (board[i][j - 1] == "X ") numberOfLocalMines++
                if (board[i - 1][j - 1] == "X ") numberOfLocalMines++
                if (board[i - 1][j] == "X ") numberOfLocalMines++
                if (board[i - 1][j + 1] == "X ") numberOfLocalMines++
            }
            if (numberOfLocalMines > 0) board[i][j] = "$numberOfLocalMines "
        }
    }
}

fun printBoard(board: MutableList<MutableList<String>>) {
    for (i in 0 until board.size) {
        for (j in 0 until board[i].size) {
            print(board[i][j])
        }
        println()
    }
}

fun floodFill(playingBoard:  MutableList<MutableList<String>>, filledBoard: MutableList<MutableList<String>>, col: Int, row: Int) {
    val numberCheck = arrayOf("1 ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ")
    val colQueue = mutableListOf<Int>()
    val rowQueue = mutableListOf<Int>()
    colQueue.add(col)
    rowQueue.add(row)
    while (colQueue.size > 0 && rowQueue.size > 0) {
        val tempCol = colQueue[0]
        val tempRow = rowQueue[0]
        colQueue.removeAt(0)
        rowQueue.removeAt(0)
        if (filledBoard[tempCol][tempRow] == ". " && playingBoard[tempCol][tempRow] != ". ") {
            playingBoard[tempCol][tempRow] = ". "

            colQueue.add(tempCol)
            rowQueue.add(tempRow+1)

            colQueue.add(tempCol+1)
            rowQueue.add(tempRow+1)

            colQueue.add(tempCol+1)
            rowQueue.add(tempRow)

            colQueue.add(tempCol+1)
            rowQueue.add(tempRow-1)

            colQueue.add(tempCol)
            rowQueue.add(tempRow-1)

            colQueue.add(tempCol-1)
            rowQueue.add(tempRow-1)

            colQueue.add(tempCol-1)
            rowQueue.add(tempRow)

            colQueue.add(tempCol-1)
            rowQueue.add(tempRow+1)
        } else if (numberCheck.contains(filledBoard[tempCol][tempRow])) {
            playingBoard[tempCol][tempRow] = filledBoard[tempCol][tempRow]
        }

    }

}