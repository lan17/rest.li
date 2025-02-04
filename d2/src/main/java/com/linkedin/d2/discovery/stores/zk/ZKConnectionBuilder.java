/*
   Copyright (c) 2017 LinkedIn Corp.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.linkedin.d2.discovery.stores.zk;

import com.linkedin.util.ArgumentUtil;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

/**
 * Builder for {@link ZKConnection}
 */
public class ZKConnectionBuilder
{
  private final String _connectString;
  private int _sessionTimeout;
  private boolean _shutdownAsynchronously = false;
  private int _retryLimit = 0;
  private boolean _isSymlinkAware = false;
  private boolean _exponentialBackoff = false;
  private ScheduledExecutorService _retryScheduler = null;
  private long _initInterval = 0;
  private Function<ZooKeeper, ZooKeeper> _zkDecorator = null;
  private boolean _isWaitForConnected = false;

  /**
   * @param connectString comma separated host:port pairs, each corresponding to a zk
   *                      server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002" If
   *                      the optional chroot suffix is used the example would look
   *                      like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
   *                      where the client would be rooted at "/app/a" and all paths
   *                      would be relative to this root - ie getting/setting/etc...
   *                      "/foo/bar" would result in operations being run on
   *                      "/app/a/foo/bar" (from the server perspective).
   *
   *                      The connectString is always required or the class will throw
   *                      a NullException on ZKConnection.start()
   */
  public ZKConnectionBuilder(String connectString)
  {
    ArgumentUtil.notNull(connectString, "connectString");
    _connectString = connectString;
  }

  public ZKConnectionBuilder(ZKConnectionBuilder builder)
  {
    _connectString = builder._connectString;
    _sessionTimeout = builder._sessionTimeout;
    _shutdownAsynchronously = builder._shutdownAsynchronously;
    _retryLimit = builder._retryLimit;
    _isSymlinkAware = builder._isSymlinkAware;
    _exponentialBackoff = builder._exponentialBackoff;
    _retryScheduler = builder._retryScheduler;
    _initInterval = builder._initInterval;
    _zkDecorator = builder._zkDecorator;
    _isWaitForConnected = builder._isWaitForConnected;
  }

  /**
   * @param sessionTimeout session timeout in milliseconds
   */
  public ZKConnectionBuilder setTimeout(int sessionTimeout)
  {
    _sessionTimeout = sessionTimeout;
    return this;
  }

  /**
   * @param shutdownAsynchronously Make the shutdown call asynchronous
   */
  public ZKConnectionBuilder setShutdownAsynchronously(boolean shutdownAsynchronously)
  {
    _shutdownAsynchronously = shutdownAsynchronously;
    return this;
  }

  /**
   * @param retryLimit limit of attempts for RetryZooKeeper reconnection
   */
  public ZKConnectionBuilder setRetryLimit(int retryLimit)
  {
    _retryLimit = retryLimit;
    return this;
  }
  /**
   * @param isSymlinkAware Resolves znodes whose name is prefixed with a
   *                       dollar sign '$' (eg. /$symlink1, /foo/bar/$symlink2)
   */
  public ZKConnectionBuilder setIsSymlinkAware(boolean isSymlinkAware)
  {
    _isSymlinkAware = isSymlinkAware;
    return this;
  }

  /**
   * @param exponentialBackoff enables exponential backoff for the RetryZooKeeper reconnection
   */
  public ZKConnectionBuilder setExponentialBackoff(boolean exponentialBackoff)
  {
    _exponentialBackoff = exponentialBackoff;
    return this;
  }

  /**
   * @param retryScheduler scheduler for retry attempts of RetryZooKeeper
   */
  public ZKConnectionBuilder setScheduler(ScheduledExecutorService retryScheduler)
  {
    _retryScheduler = retryScheduler;
    return this;
  }

  /**
   * @param initInterval sets the initial time interval between retrials
   *                     in the exponential backoff for the RetryZooKeeper reconnection
   */
  public ZKConnectionBuilder setInitInterval(long initInterval)
  {
    _initInterval = initInterval;
    return this;
  }

  /**
   * @param zkDecorator add a decorator to the Base ZooKeeper
   */
  public ZKConnectionBuilder setZooKeeperDecorator(Function<ZooKeeper, ZooKeeper> zkDecorator)
  {
    _zkDecorator = zkDecorator;
    return this;
  }

  /**
   * @param waitForConnected should #start block until the connection establishes
   */
  public ZKConnectionBuilder setWaitForConnected(boolean waitForConnected)
  {
    _isWaitForConnected = waitForConnected;
    return this;
  }

  public ZKConnection build()
  {
    return new ZKConnection(_connectString, _sessionTimeout, _retryLimit, _exponentialBackoff,
                            _retryScheduler, _initInterval, _shutdownAsynchronously, _isSymlinkAware, _zkDecorator, _isWaitForConnected);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ZKConnectionBuilder that = (ZKConnectionBuilder) o;
    return _sessionTimeout == that._sessionTimeout && _shutdownAsynchronously == that._shutdownAsynchronously
        && _retryLimit == that._retryLimit && _isSymlinkAware == that._isSymlinkAware
        && _exponentialBackoff == that._exponentialBackoff && _initInterval == that._initInterval && Objects.equals(
        _connectString, that._connectString) && Objects.equals(_retryScheduler, that._retryScheduler) && Objects.equals(
        _zkDecorator, that._zkDecorator) && _isWaitForConnected == that._isWaitForConnected;
  }

  @Override
  public int hashCode() {

    return Objects.hash(_connectString, _sessionTimeout, _shutdownAsynchronously, _retryLimit, _isSymlinkAware,
                        _exponentialBackoff, _retryScheduler, _initInterval, _zkDecorator, _isWaitForConnected);
  }
}