package com.haakenstad.potwater.scheduler;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bjornhaa
 * Date: 06.07.13
 * Time: 01:06
 * To change this template use File | Settings | File Templates.
 */
public class PotWaterJob implements Job {

    private static final int SECOND = 1000;
    private static final int WATER_LENGTH = 25 * SECOND;
    private static final int LONG_WATER_LENGTH = 20 * SECOND;
    private boolean debugMode = false;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            dojob(jobExecutionContext);
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void dojob(JobExecutionContext jobExecutionContext) throws Exception {
        //sendAlert("potwater started");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
        String time = dateTimeFormatter.print(System.currentTimeMillis());
        System.out.println("Starting water sequence at " + time);
        Command command;
        if (debugMode) {
            command = new DebugCommand();
        } else {
            command = new PiFaceCommand();
        }

        command.enableLongRun();
        command.enablePump();
        Thread.sleep(LONG_WATER_LENGTH);
        command.disableLongRun();
        command.enable();
        Thread.sleep(WATER_LENGTH);
        command.disable();
        System.out.println("Water sequence done\n");

    }


    private class DebugCommand implements Command {

        public void enable() {
            System.out.println("\tenable valves");
        }

        public void disable() {
            System.out.println("\tdisable all");
        }

        public void enableLongRun() {
            System.out.println("Enable long run");
        }

        public void enablePump() {
            System.out.println("Enable pump");
        }

        public void disableLongRun() {
            System.out.println("Disable long run");
        }
    }
}
