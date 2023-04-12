import random
def generate_matrices(rows, cols, min_value, max_value):
    A = [[random.randint(min_value, max_value) for _ in range(cols)] for _ in range(rows)]
    B = [[random.randint(min_value, max_value) for _ in range(cols)] for _ in range(rows)]
    return A, B
matrix_A, matrix_B = generate_matrices(8, 8, 0, 10)