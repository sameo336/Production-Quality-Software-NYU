package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects. It maintains
 * references to all created Stopwatch objects and provides a convenient method for getting a list
 * of those objects.
 *
 */
public final class StopwatchFactory {

  private static final Object stopWatchFactoryLock = new Object();
  private static final HashMap<String, Stopwatch> mapOfExistingStopwatches =
      new HashMap<String, Stopwatch>();

  /**
   * Creates and returns a new Stopwatch object
   * 
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already taken. An id is
   *           considered already taken by an existing Stopwatch object if the case-insensitive
   *           string comparison of the ids returns true.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null) {
      throw new IllegalArgumentException("Id cannot be null!");
    }
    if (id.trim().length() == 0) {
      throw new IllegalArgumentException("Id cannot be empty!");
    }
    String trimmedId = id.trim();
    Stopwatch stopWatchObject;
    synchronized (stopWatchFactoryLock) {
      if (mapOfExistingStopwatches.containsKey(trimmedId.toLowerCase())) {
        throw new IllegalArgumentException("Id is already being used!");
      }
      stopWatchObject = new StopwatchImplementation(trimmedId);
      mapOfExistingStopwatches.put(trimmedId.toLowerCase(), stopWatchObject);
    }
    return stopWatchObject;
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all created Stopwatch objects. Returns an empty list if no Stopwatches have
   *         been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopWatchFactoryLock) {
      return new ArrayList<Stopwatch>(mapOfExistingStopwatches.values());
    }
  }

  /*
   * Overriden toString method to display the contents of a StopwatchFactory instance.
   * 
   * @return the StopwatchFactory instance details in a proper format.
   */
  @Override
  public String toString() {
    StringBuilder stopWatchFactoryStringBuilder = new StringBuilder();
    synchronized (stopWatchFactoryLock) {
      stopWatchFactoryStringBuilder.append(mapOfExistingStopwatches.values());
    }
    return stopWatchFactoryStringBuilder.toString();
  }
}
