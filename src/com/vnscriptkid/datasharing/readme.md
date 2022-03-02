## Stack and Heap


| stack (exclusive)     | heap (shared)  |
|-----------------------|----------------|
| local primitive types | static members |
| object references     | class member   |
| n/a                   | object         |


## Share resource
- what is `resource` here?
- why we want to share? give examples?
  - file saver: file
  - worker threads, work dispatcher, queue of jobs
  - microservice, connection object, database

## problem with sharing resource
- multiple threads do non-atomic operations on shared resource
- outstanding example: inventory counter
- `this.items++`:
  - get current value
  - increment value by one
  - assign new value