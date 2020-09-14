package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * A thread safe class which can be used to create StopWatch objects. It
 * can be used to stop, start, lap and reset the StopWatch in a multi-threaded
 * environment.
 * 
 * @author Sanchit K
 */
final class StopWatch implements IStopwatch {

  private final String id;
  private long lapStartTime;
  private boolean isRunning;
  private final Object stopWatchLock;
  private final List<Long> lapTimes;

  StopWatch(String id) {
    this.id = id;
    this.stopWatchLock = new Object();
    this.lapStartTime = 0L;
    this.isRunning = false;
    this.lapTimes = Collections.synchronizedList(new ArrayList<Long>());
  }

  @Override
  public String getId() {
    return id;
  }

  private long getLapStartTime() {
    return lapStartTime;
  }

  private boolean isRunning() {
    return isRunning;
  }

  /**
   * Starts the stopwatch. Also records the lap start time.
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (stopWatchLock) {
      if (isRunning()) {
        throw new IllegalStateException("StopWatch is already running!");
      }
      lapStartTime = TimeUnit.MILLISECONDS.toMillis(System.nanoTime());
      isRunning = true;
    }

  }

  /**
   * Stores the time elapsed since the last time lap() was called or since
   * start() was called if this is the first lap.
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (stopWatchLock) {
      if (!isRunning()) {
        throw new IllegalStateException("StopWatch is not running!");
      }
      long currentTime = TimeUnit.MILLISECONDS.toMillis(System.nanoTime());
      lapTimes.add(currentTime - lapStartTime);
      lapStartTime = currentTime;
    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * 
   * @throws IllegalStateException
   *           if called when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (stopWatchLock) {
      if (!isRunning()) {
        throw new IllegalStateException("StopWatch is not running!");
      }
      lap();
      isRunning = false;
    }
  }

  /**
   * Resets the stopwatch. If the stopwatch is running, this method stops the
   * watch and resets it. This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (stopWatchLock) {
      lapStartTime = 0L;
      lapTimes.clear();
      isRunning = false;
    }

  }

  /**
   * Returns a list of lap times (in milliseconds). This method can be called at
   * any time and will not throw an exception.
   * 
   * @return a list of recorded lap times or an empty list if no times are
   *         recorded.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (stopWatchLock) {
      return new ArrayList<Long>(lapTimes);
    }
  }

  /**
   * Return the hascode of the stopwatch object
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /**
   * Can be used to check if two stopwatches are the same or not using their
   * ids.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StopWatch other = (StopWatch) obj;
   if (!id.equals(other.id))
      return false;
    return true;
  }

  /**
   * Overriden string method to display in the appropriate format. 
   */
  @Override
  public String toString() {
    //Synchronized as it calls non-final members.
    synchronized (stopWatchLock) {
      return "StopWatch [id=" + getId() + ", lapStartTime=" + getLapStartTime()
          + ", isRunning=" + isRunning() + ", lapTimes=" + getLapTimes() + "]";
    }
  }

}
