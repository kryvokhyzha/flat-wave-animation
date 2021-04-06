# flat-wave-animation
This repository contains flat surface "wave" animation mini-project.

## Assignment
Imagine,... you are observing a picture on the bottom of a borderless aquarium through a rectangular hole.<br>
Periodically you can see a flat wave spreading over the surface of water.<br>
Task is:
1. to simulate a smooth flat wave visual effect with a proper algorithm (as much
realistic as possible)
2. to optimize it for multi-core environment (_optional_)

## Basic requirements
0. The result should be a Win64 application (In C or C++ optimally, Java / Python are also acceptable).
1. It should get A background image as an input (BMP, JPEG, etc).
2. The application has to provide a way for tuning parameters of wave animation (e.g. speed of propagation, etc) and mean for distortion function n(x).
3. The angle α has random value for every pass of wave (0 α < 360), for first pass it must be 45 o (direction must be as shown on the figure above).
4. Every pass of the wave animation has to start from random corner pixel of the background image.
5. The application has to provide switching between parallel and sequential animation mode (optional).
6. Any external 3D libraries (OpenGL, etc) must not be used.

Two variants have to be implemented:
1. sequential algorithm;
2. parallelized algorithm (optional);

## Sources
+ [The Java Tutorials Lesson: Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)
+ [FastMPJ User’s Guide](http://gac.udc.es/~rreye/fastmpj/doc/UsersGuide.pdf)
+ [Foster I. Designing and Building Parallel Programs](http://www.mcs.anl.gov/~itf/dbpp/text/book.html)
