package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopWatchImplementation class is a thread-safe implementation of a Stopwatch. It implements
 * the Stopwatch interface. It can be used to create Stopwatch objects for timings tasks. It
 * supports the typical operations of a stopwatch: start, record laps, stop and reset/restart.
 * 
 * @author Anisha Bhatla
 *
 */
final class StopwatchImplementation implements Stopwatch {

  private final String stopWatchId;
  private boolean isStopwatchRunning;
  private boolean wasStopwatchLastStopped;
  private long lastLapStartTimeInMillis;
  private final List<Long> listOfLapTimesInMillis;
  private final Object stopWatchLock;

  StopwatchImplementation(String stopWatchId) {
    this.stopWatchId = stopWatchId;
    stopWatchLock = new Object();
    listOfLapTimesInMillis = new ArrayList<Long>();
  }

  /**
   * Returns the Id of this stopwatch
   * 
   * @return the Id of this stopwatch. Will never be empty or null.
   */
  @Override
  public String getId() {
    return stopWatchId;
  }

  /**
   * Starts the stopwatch.
   * 
   * @throws IllegalStateException thrown when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (stopWatchLock) {
      if (!isStopwatchRunning) {
        isStopwatchRunning = true;
        lastLapStartTimeInMillis = System.currentTimeMillis();
      } else {
        throw new IllegalStateException("Stopwatch is already running!");
      }
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called or since start() was called if
   * this is the first lap.
   * 
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (stopWatchLock) {
      if (isStopwatchRunning) {
        long currentTimeInMillis = System.currentTimeMillis();
        long currentLapTimeInMillis = currentTimeInMillis - lastLapStartTimeInMillis;
        if (!wasStopwatchLastStopped) {
          listOfLapTimesInMillis.add(currentLapTimeInMillis);
        } else {
          /*
           * In order to continue timing the lap from where the stopwatch was last stopped, last
           * recorded lap time is removed from the list and added to the current lap time. The time
           * between when the stopwatch was stopped and then started again is ignored.
           */
          long lastRecordedLapTimeInMillis =
              listOfLapTimesInMillis.remove(listOfLapTimesInMillis.size() - 1);
          listOfLapTimesInMillis.add(currentLapTimeInMillis + lastRecordedLapTimeInMillis);
          wasStopwatchLastStopped = false;
        }
        lastLapStartTimeInMillis = currentTimeInMillis;
      } else {
        throw new IllegalStateException("Stopwatch is not running!");
      }
    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * 
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (stopWatchLock) {
      if (isStopwatchRunning) {
        lap();
        isStopwatchRunning = false;
        wasStopwatchLastStopped = true;
      } else {
        throw new IllegalStateException("Stopwatch is not running!");
      }
    }
  }

  /**
   * Resets the stopwatch. If the stopwatch is running, this method stops the watch and resets it.
   * This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (stopWatchLock) {
      isStopwatchRunning = false;
      wasStopwatchLastStopped = false;
      lastLapStartTimeInMillis = 0L;
      listOfLapTimesInMillis.clear();
    }
  }

  /**
   * Returns a list of lap times (in milliseconds). This method can be called at any time and will
   * not throw an exception.
   * 
   * @return a list of recorded lap times or an empty list.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (stopWatchLock) {
      return new ArrayList<Long>(listOfLapTimesInMillis);
    }
  }

  /*
   * Overriden toString method to display the contents of a StopwatchImplementation instance.
   * 
   * @return the StopwatchImplementation instance details in a proper format.
   */
  @Override
  public String toString() {
    StringBuilder stopWatchStringBuilder = new StringBuilder();
    synchronized (stopWatchLock) {
      stopWatchStringBuilder.append("Stopwatch ID: ").append(stopWatchId)
          .append(", Last Lap Start Time In Milliseconds: ").append(lastLapStartTimeInMillis)
          .append(", Is Stopwatch Running: ").append(isStopwatchRunning)
          .append(", Lap Times In Milliseconds: ").append(listOfLapTimesInMillis);
    }
    return stopWatchStringBuilder.toString();
  }
}
