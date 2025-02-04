/*
 * Copyright (c) 2017 LinkedIn Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linkedin.restli.examples.greetings.server;


import com.linkedin.parseq.promise.Promise;
import com.linkedin.parseq.promise.Promises;
import com.linkedin.parseq.promise.SettablePromise;
import com.linkedin.restli.server.UnstructuredDataWriter;
import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.annotations.UnstructuredDataWriterParam;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
@RestLiCollection(name = "greetingCollectionUnstructuredDataPromise", namespace = "com.linkedin.restli.examples.greetings.client")
public class GreetingUnstructuredDataCollectionResourcePromise extends com.linkedin.restli.server.resources.unstructuredData.UnstructuredDataCollectionResourcePromiseTemplate<String>
{ // Use full-qualified classname here since we cannot add @SuppressWarnings("deprecation") in import
  private static final ScheduledExecutorService _scheduler = Executors.newScheduledThreadPool(1);
  private static final int DELAY = 100;

  @Override
  public Promise<Void> get(String key, @UnstructuredDataWriterParam UnstructuredDataWriter writer)
  {
    final SettablePromise<Void> result = Promises.settable();
    _scheduler.schedule(() ->
                        {
                          try
                          {
                            GreetingUnstructuredDataUtils.respondGoodUnstructuredData(writer);
                            result.done(null);
                          }
                          catch (final Throwable throwable)
                          {
                            result.fail(throwable);
                          }
                        }, DELAY, TimeUnit.MILLISECONDS);
    return result;
  }
}