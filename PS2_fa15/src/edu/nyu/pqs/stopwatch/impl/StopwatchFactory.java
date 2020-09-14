package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import edu.nyu.pqs.stopwatch.impl.StopWatch;
import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public final class StopwatchFactory {

  private static final ConcurrentHashMap<String, IStopwatch> stopwatches =
      new ConcurrentHashMap<String, IStopwatch>();

  /**
   * Creates and returns a new IStopwatch object
   * 
   * @param id
   *          The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException
   *           if <code>id</code> is empty, null, or already taken.
   */
  public static final IStopwatch getStopwatch(String id) {

    if (id == null) {
      throw new IllegalArgumentException("id is null");
    }
    if (id.length() == 0) {
      throw new IllegalArgumentException("id is empty");
    }

    IStopwatch stopWatch = null;
    synchronized (stopwatches) {
      if (stopwatches.containsKey(id)) {
        throw new IllegalArgumentException("id already exists");
      } else {
        stopWatch = new StopWatch(id);
        stopwatches.put(id, stopWatch);
      }
    }
    return stopWatch;
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all creates IStopwatch objects. Returns an empty list if
   *         no IStopwatches have been created.
   */
  public static final List<IStopwatch> getStopwatches() {
    synchronized (stopwatches) {
      return new ArrayList<IStopwatch>(stopwatches.values());
    }
  }
}
