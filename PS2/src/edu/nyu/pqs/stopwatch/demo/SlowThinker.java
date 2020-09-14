package edu.nyu.pqs.stopwatch.demo;

import java.util.List;
import java.util.logging.Logger;

import edu.nyu.pqs.stopwatch.api.Stopwatch;
import edu.nyu.pqs.stopwatch.impl.StopwatchFactory;

/**
 * This is a simple program that demonstrates just some of the functionality of the Stopwatch
 * interface and StopwatchFactory class. Just because this class runs successfully does not mean
 * that the assignment is complete. It is up to you to implement the methods of Stopwatch and
 * StopwatchFactory.
 *
 */
public class SlowThinker {

  private static final Logger logger = Logger.getLogger("edu.nyu.pqs.ps4.demo.SlowThinker");

  /**
   * Run the SlowThinker demo application
   */
  public static void main(String[] args) {
    SlowThinker thinker = new SlowThinker();
    thinker.go();
  }

  /**
   * Starts the slowthinker. It will get a stopwatch, set a number of lap times, stop the watch and
   * then print out all the lap times.
   *
   */
  private void go() {
    Runnable runnable = new Runnable() {
      public void run() {
        Stopwatch stopwatch = StopwatchFactory.getStopwatch("ID " + Thread.currentThread().getId());
        stopwatch.start();
        
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ignored) {

          }
          stopwatch.lap();

          try {
            Thread.sleep(2000);
          } catch (InterruptedException ignored) {

          }
        
        stopwatch.stop();
        

        try {
          Thread.sleep(3000);
        } catch (InterruptedException ignored) {

        }
        stopwatch.start();


        List<Long> times = stopwatch.getLapTimes();
        logger.info(times.toString());
      }
    };
    Thread thinkerThread = new Thread(runnable);
    thinkerThread.start();
  }
}
