package com.haakenstad.potwater.scheduler;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bjornhaa
 * Date: 06.07.13
 * Time: 01:06
 * To change this template use File | Settings | File Templates.
 */
@DisallowConcurrentExecution
public class PotWaterJob implements Job {

    private static final int SECOND = 1000;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            dojob(jobExecutionContext);
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void dojob(JobExecutionContext jobExecutionContext) throws Exception {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Map<Integer, Integer> valves = new HashMap<Integer, Integer>();
        for (String key : jobDataMap.keySet()) {
            if (StringUtils.isNumeric(key)) {
                valves.put(Integer.parseInt(key), jobDataMap.getInt(key));
            }
        }
        boolean debug = jobDataMap.getBoolean("debug");
        int pump = jobDataMap.getInt("pump");
        System.out.println("pump = " + pump);
        System.out.println("valves = " + valves);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
        String time = dateTimeFormatter.print(System.currentTimeMillis());
        System.out.println("Starting water sequence at " + time);
        Command command;
        for (Integer valve : valves.keySet()) {
            Integer duration = valves.get(valve);
            if (debug) {
                command = new DebugCommand(valve, pump);
            } else {
                command = new PiFaceCommand(valve, pump);
            }
            command.enable();
            Thread.sleep(duration * SECOND);
            command.disable();
            Thread.sleep(SECOND);
        }
        System.out.println("Water sequence done\n");

    }


    private class DebugCommand extends Command {


        public DebugCommand(Integer valve, Integer pump) throws Exception {
            super(valve, pump);
        }

        public void enable() {
            System.out.println("\tDebug enable " + valve);
        }

        public void disable() {
            System.out.println("\tDebug Stopping pump and valve " + valve + ". Has been running for " + formatDuration());
        }

    }
}
