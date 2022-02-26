package com.vnscriptkid.thread.creation;

public class CreateThreadEx {
    public static void main(String[] args) {
        // todo: create new thread
        Thread thread = new Thread(new Runnable() {
            @java.lang.Override
            public void run() {

            }
        });


        // todo: name it `my thread`
        // todo: run that thread from main thread
        // todo: log before and after triggering run
        // todo: print name of thread while running that thread
        // todo: give my thread highest priority, what is the range?
        // todo: print priority of thread while running that thread
        // todo: set breakpoints and run with debug, see threads and their states
        // todo: set exception handler for thread, manually throw an error inside thread see handler kicks in
        //  2 ways to create thread
        // 1) create thread object, create runnable object
        // 2) create class inherited from Thread, pass code in through run()
        // todo: implement 2nd way to create thread
    }
}
