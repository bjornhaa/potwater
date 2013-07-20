package com.haakenstad.potwater.scheduler;

import com.pi4j.component.switches.SwitchListener;
import com.pi4j.component.switches.SwitchState;
import com.pi4j.component.switches.SwitchStateChangeEvent;
import com.pi4j.device.piface.PiFace;
import com.pi4j.device.piface.PiFaceSwitch;
import com.pi4j.device.piface.impl.PiFaceDevice;
import com.pi4j.wiringpi.Spi;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: bjornhaa
 * Date: 04.07.13
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public class PotwaterScheduler {

    public static void main(String[] args) throws Exception {
        System.out.println("*********** Starting PotWater ****************");


        Logger log = LoggerFactory.getLogger(PotwaterScheduler.class);

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail job = newJob(PotWaterJob.class)
                .withIdentity("wetjob")
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger1")
                .withSchedule(cronSchedule("0 0 20 * * ?"))
                .build();
        Trigger testTrigger = newTrigger()
                .startNow()
                .build();
        Date ft = null;
        ft = sched.scheduleJob(job, trigger);
        //ft = sched.scheduleJob(job, testTrigger);
        log.info(job.getKey() + " has been scheduled to run at: " + ft
                + " and repeat based on expression: "
                + trigger.getCronExpression());

        sched.start();

        log.info("------- Started Scheduler -----------------");



        while (true) ;

    }
}
