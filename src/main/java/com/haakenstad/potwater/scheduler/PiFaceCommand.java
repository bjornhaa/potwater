package com.haakenstad.potwater.scheduler;

import com.pi4j.device.piface.PiFace;
import com.pi4j.device.piface.impl.PiFaceDevice;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.wiringpi.Spi;

import java.io.IOException;

/**
* Created with IntelliJ IDEA.
* User: bjornhaa
* Date: 11.07.13
* Time: 21:12
* To change this template use File | Settings | File Templates.
*/
class PiFaceCommand implements Command {

  private final GpioPinDigitalOutput valve1;
  private final GpioPinDigitalOutput valve2;
  private final GpioPinDigitalOutput valve3;
  private final GpioPinDigitalOutput valve4;
  private final GpioPinDigitalOutput pump;

    private static PiFaceDevice pifaceDevice;

  public PiFaceCommand() throws Exception {
pifaceDevice = new PiFaceDevice(PiFace.DEFAULT_ADDRESS, Spi.CHANNEL_0);
      valve1 = pifaceDevice.getOutputPin(7);
      valve2 = pifaceDevice.getOutputPin(6);
      valve3 = pifaceDevice.getOutputPin(5);
      valve4 = pifaceDevice.getOutputPin(4);
      pump = pifaceDevice.getOutputPin(2);

  }

  public void enable() {
      System.out.println("Starting valves and pump");
      valve1.high();
      valve2.high();
      valve3.high();
  }

  public void disable() {
      System.out.println("Stopping pump");
      pump.low();
      System.out.println("Disabling valves");
      valve1.low();
      valve2.low();
      valve3.low();
      valve4.low();
  }

  public void enableLongRun() {
      valve4.high();
  }

  public void enablePump() {
      pump.high();
  }

  public void disableLongRun() {
      valve4.low();
  }
}