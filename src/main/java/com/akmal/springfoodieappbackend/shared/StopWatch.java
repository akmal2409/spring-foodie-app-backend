package com.akmal.springfoodieappbackend.shared;

/**
 * Shared utility class that simplifies the time measurements and encapsulates the logic in a single
 * place. By default calculates the time using {@link System#nanoTime()} method.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 11/01/2022 - 20:06
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public class StopWatch {
  private long startTime;
  private long endTime;

  /**
   * Static factory method that returns the instance of {@link StopWatch}. The {@link StopWatch} is
   * reset at the creation time.
   *
   * @return {@link StopWatch} new instance.
   */
  public static StopWatch start() {
    return new StopWatch();
  }

  private StopWatch() {
    this.reset();
  }

  /**
   * Resets the stopwatch and sets the start time using {@link System#nanoTime()} method.
   *
   */
  public void reset() {
    this.startTime = System.nanoTime();
    this.endTime = 0L;
  }

  /**
   * Sets the end time of the measurement.
   */
  public void stop() {
    this.endTime = System.nanoTime();
  }

  /**
   * Returns the result in nanoseconds.
   * @return elapsed time
   */
  public long toNano() {
    return endTime - startTime;
  }

  /**
   * Returns the result in milliseconds.
   * @return elapsed time
   */
  public double toMili() {
    return (endTime - startTime) /(double) 1000000;
  }

  /**
   * Retuns the result in seconds.
   * @return elapsed time
   */
  public double toSeconds() {
    return (endTime - startTime) /(double) 1000000000;
  }
}
