#include <iostream>
#include <chrono>
#include <omp.h>
#include "EasyBMP.hpp"

using namespace std;

const int MAX_ITERATIONS = 1000;

void compute_mandelbrot(int width, int height);

int calculateCurrentIteration(double re, double im, double x, double y);

int main() {

    int width;
    cout << "Enter width" << endl;
    std::cin >> width;

    int height;
    cout << "Enter height" << endl;
    std::cin >> height;

    cout << "Width: " << width << ", Height: " << height << " || Computing Mandelbrot Set..." << endl;

    auto start = chrono::steady_clock::now();
    compute_mandelbrot(width, height);
    auto end = chrono::steady_clock::now();


    auto diff = end - start;

    cout << chrono::duration <double, milli> (diff).count() << " ms" << endl;
    return 0;
}

void compute_mandelbrot(int width, int height) {
    omp_set_num_threads(4);
    EasyBMP::Image img(width, height, "sample.bmp");

    for (int col = 0; col < width; ++col) {
        for (int row = 0; row < height; ++row) {
            double c_re = (col - width / 2) * 4.0 / width;
            double c_im = (row - height / 2) * 4.0 / width;

            double x = 0;
            double y = 0;


            // #pragma omp parallel default(none) private(c_re, c_im, x, y) shared(img, row, col)
            //{
                int currentIteration = calculateCurrentIteration(c_re, c_im, x, y);

                if (currentIteration < MAX_ITERATIONS/10) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(0, 205, 0));
                } else if (currentIteration < MAX_ITERATIONS/8) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(0, 238, 0));
                } else if (currentIteration == MAX_ITERATIONS/6) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(102, 205, 0));
                } else if (currentIteration == MAX_ITERATIONS/4) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(154, 255, 154));
                } else if (currentIteration == MAX_ITERATIONS/2) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(0, 255, 150));
                } else if (currentIteration < MAX_ITERATIONS) {
                    img.SetPixel(col, row, EasyBMP::RGBColor(0, 255, 200));
                } else {
                    img.SetPixel(col, row, EasyBMP::RGBColor(0, 0, 0));
                }
            //}
        }

    }
    img.Write();
}

int calculateCurrentIteration(double re, double im, double x, double y) {
    int currentIteration = 0;
    while ((x * x + y * y) < 4 && currentIteration < MAX_ITERATIONS) {
        double x_new = x * x - y * y + re;

        y = 2 * x * y + im;
        x = x_new;

        currentIteration++;
    }
    return currentIteration;
}
