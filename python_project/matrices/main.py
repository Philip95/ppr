#! /usr/bin/env python3
import time
import numpy as np
import concurrent.futures
import multiprocessing
from helper import generate_matrices

custom_context = multiprocessing.get_context("fork")


def test(a, b):
    start = time.process_time()
    c = a @ b
    end = time.process_time()
    delta = end - start
    return start, end, OUTPUT, delta


class Matrix(list):
    # Matrix multiplication A @ B
    def __matmul__(self, b):
        matrix_a = self
        matrix_b = b
        matrix_width = len(matrix_a)
        matrix_height_a = len(matrix_a[0])
        matrix_height_b = len(matrix_b[0])

        result = []
        for i in range(matrix_width):
            row = [0] * matrix_height_b
            result.append(row)

        for i in range(matrix_width):
            for j in range(matrix_height_b):
                for k in range(matrix_height_a):
                    result[i][j] += matrix_a[i][k] * matrix_b[k][j]
        return result


class ParallelMatrix_Threadpool(list):

    def __matmul__(self, matrix_b):

        def matmul_element(args):
            i, j, a, b = args
            result = 0
            for k in range(len(a[0])):
                result += a[i][k] * b[k][j]
            return i, j, result

        matrix_a = self
        matrix_width = len(matrix_a)
        matrix_height_a = len(matrix_a[0])
        matrix_height_b = len(matrix_b[0])
        if matrix_height_a != len(matrix_b):
            raise ValueError("Matrix dimensions are not compatible for multiplication")
        result = [[0] * matrix_height_b for _ in range(matrix_width)]
        with concurrent.futures.ThreadPoolExecutor(max_workers=8) as executor:
            futures = [
                executor.submit(matmul_element, (i, j, matrix_a, matrix_b))
                for i in range(matrix_width)
                for j in range(matrix_height_b)
            ]
            for future in concurrent.futures.as_completed(futures):
                i, j, value = future.result()
                result[i][j] = value

        return result


class ParallelMatrix_Process(list):

    def matmul_element(self, args):
        i, j, a, b = args
        result = 0
        for k in range(len(a[0])):
            result += a[i][k] * b[k][j]
        return i, j, result

    def __matmul__(self, matrix_b):
        matrix_a = self
        matrix_width = len(matrix_a)
        matrix_height_a = len(matrix_a[0])
        matrix_height_b = len(matrix_b[0])
        if matrix_height_a != len(matrix_b):
            raise ValueError("Matrix dimensions are not compatible for multiplication")
        result = [[0] * matrix_height_b for _ in range(matrix_width)]
        with concurrent.futures.ProcessPoolExecutor(max_workers=8, mp_context=custom_context) as executor:
            futures = [
                executor.submit(self.matmul_element, (i, j, matrix_a, matrix_b))
                for i in range(matrix_width)
                for j in range(matrix_height_b)
            ]
            for future in concurrent.futures.as_completed(futures):
                i, j, value = future.result()
                result[i][j] = value

        return result


OUTPUT = "Runtime "
# input1 = [[8, 10, 3, 4, 10, 9, 2, 3], [7, 0, 7, 4, 2, 10, 5, 9], [8, 6, 4, 6, 4, 10, 5, 2], [9, 0, 2, 10, 3, 6, 3, 1],
#          [0, 4, 1, 5, 2, 2, 6, 0], [8, 9, 6, 4, 4, 5, 7, 8], [2, 2, 8, 2, 9, 6, 10, 7], [0, 1, 7, 7, 3, 4, 5, 7]]
# input2 = [[5, 10, 1, 6, 5, 10, 3, 7], [6, 5, 8, 4, 8, 9, 5, 10], [9, 2, 7, 10, 10, 3, 7, 8], [5, 6, 2, 3, 9, 5, 5, 3],
#          [4, 2, 3, 3, 9, 0, 5, 8], [0, 4, 9, 0, 3, 10, 9, 1], [0, 7, 6, 7, 0, 1, 2, 8], [4, 7, 4, 4, 8, 6, 8, 8]]
# input10 = [[8, 10], [3, 4]]
# input20 = [[1, 2], [5, 6]]
input1, input2 = generate_matrices(1024, 1024, 0, 10)

if __name__ == '__main__':
    # Basic
    print("Basic Sequential")
    A = Matrix(input1)
    B = Matrix(input2)
    print(test(A, B))
    # parallel with ThreadPoolExecutor
    print("parallel with ThreadPoolExecutor")
    D = ParallelMatrix_Threadpool(input1)
    E = ParallelMatrix_Threadpool(input2)
    print(test(D, E))
    # parallel with ProcessPoolExecutor
    print("parallel with ProcessPoolExecutor")
    F = ParallelMatrix_Process(input1)
    G = ParallelMatrix_Process(input2)
    print(test(F, G))
    # numpy
    print("Numpy")
    I = np.array(input1)
    J = np.array(input2)
    print(test(I, J))
