## why multi-threads?

* responsiveness through concurrency
  * example: movie player, web server
  * concurrency: program is broken into small tasks, each task is executed by one thread alternately, make illusion of parallelism even with just one core.
* performance through parallelism
    * parallelism: achieved by multiple cores
    * benefits: complex task in less time, more tasks in same period of time.

## big picture:
- OS
  - is just program, 
  - is loaded at startup from disk to RAM
  - works as a intermediate, allows apps to interact with CPU, disks
- Application:
  - is a program
  - when opened, is loaded from disk to RAM as an instance (context)
  - instance has: files, code, heap mem, meta, threads (at least 1 main thread)
  - thread has: 1 stack + 1 instruction pointer

## context switches
- it's not free, comes with costs.
- higher costs for processes, than for threads.
- thrashing happens when too much work devoted to manage threads, other than for productive work.

## thread scheduling:
* naive: longest time first, shortest time first => could cause starvation 
* in real-world
  * epochs: allocate time slices for different threads (no need to be done)
  * dynamic priority = static + bonus 
    * static: set by dev
    * bonus: set by OS

## when to use? 

| multi-threadeded       | multi-processes (microservices) |
|------------------------|---------------------------------|
| shared mem             |                                 |
|                        | security                        |
| easy context switching |                                 |
| | stability                       | 
|                        | unrelated tasks                 |
