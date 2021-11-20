package com.akmal.springfoodieappbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

/**
 * The helper class enables us to run the methods in the same or in a new transaction.
 * The current limitations of spring proxy classes is that if the method is called within
 * the same class, then it will not be intercepted by the proxy, hence will be in a new transactions.
 * This can be overcome by having this helper class through which those said methods will be intercepted.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 20/11/2021 - 5:51 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
public class TransactionRunner {

  /**
   * The method is responsible for taking in a {@link Supplier} function
   * and executing it within the same transaction
   *
   * @param supplier {@link Supplier} instance
   * @param <T>      generic type
   * @return result of the supplier function
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public <T> T runInTransaction(Supplier<T> supplier) {
    return supplier.get();
  }

  /**
   * The method is responsible for taking in a {@link Supplier} function
   * and executing it in a new transaction.
   *
   * @param supplier {@link Supplier} instance
   * @param <T>      generic type
   * @return result of the supplier function
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public <T> T runInNewTransaction(Supplier<T> supplier) {
    return supplier.get();
  }
}
