DIR_UP = "u"
DIR_DOWN = "d"
DIR_LEFT = "l"
DIR_RIGHT = "r"
BLANK_PIECE = "Z"


def check_around(board):
    for i in range(len(board) - 1):
        for j in range(len(board[i]) - 1):
            if board[i][j] != BLANK_PIECE and board[i][j] == board[i + 1][j] == board[i][j + 1] == board[i + 1][j + 1]:
                board[i][j], board[i + 1][j], board[i][j + 1], board[i + 1][
                    j + 1] = BLANK_PIECE, BLANK_PIECE, BLANK_PIECE, BLANK_PIECE
                return True
    return False


def fill_gaps(board):
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == BLANK_PIECE:
                for k in range(i, len(board)):
                    if board[k][j] != BLANK_PIECE:
                        board[k][j], board[i][j] = " ", board[k][j]
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == " ":
                board[i][j] = "Z"

    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == BLANK_PIECE:
                for k in range(j, len(board)):
                    if board[i][k] != BLANK_PIECE and board[i][k] != " ":
                        board[i][k], board[i][j] = " ", board[i][k]
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == " ":
                board[i][j] = "Z"


def make_move(board, position, direction):
    new_position = [position[0], position[1]]
    if direction == DIR_UP:
        new_position[0] -= 1
    if direction == DIR_DOWN:
        new_position[0] += 1
    if direction == DIR_LEFT:
        new_position[1] -= 1
    if direction == DIR_RIGHT:
        new_position[1] += 1
    board[position[0]][position[1]], board[new_position[0]][new_position[1]] = board[new_position[0]][new_position[1]], \
    board[position[0]][position[1]]

    while check_around(board):
        fill_gaps(board)
    return board


print(make_move([["C", "A", "B", "C"], ["A", "B", "C", "A"], ["A", "B", "B", "A"], ["A", "C", "A", "A"]], (0, 2), "d"))
