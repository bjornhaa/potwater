package com.haakenstad.potwater.scheduler;

/**
* Created with IntelliJ IDEA.
* User: bjornhaa
* Date: 11.07.13
* Time: 21:12
* To change this template use File | Settings | File Templates.
*/
interface Command {
    void enable();

    void disable();

    void enableLongRun();

    void enablePump();

    void disableLongRun();
}
