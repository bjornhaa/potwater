package com.haakenstad.potwater.scheduler;

import com.pi4j.device.piface.PiFace;
import com.pi4j.device.piface.impl.PiFaceDevice;
import com.pi4j.wiringpi.Spi;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bjornhaa
 * Date: 11.07.13
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public class PifaceFactory {

    private static PifaceFactory instance;

    private static PiFaceDevice pifaceDevice;

    private PifaceFactory() throws IOException {
        pifaceDevice = new PiFaceDevice(PiFace.DEFAULT_ADDRESS, Spi.CHANNEL_0);
    }

    public static PifaceFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new PifaceFactory();
        }
        return instance;
    }

    public PiFaceDevice getPifaceDevice() {
        return pifaceDevice;
    }
}
