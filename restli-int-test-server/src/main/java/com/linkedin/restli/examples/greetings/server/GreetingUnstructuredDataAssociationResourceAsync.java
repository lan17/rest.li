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


import com.linkedin.common.callback.Callback;
import com.linkedin.restli.common.CompoundKey;
import com.linkedin.restli.server.UnstructuredDataWriter;
import com.linkedin.restli.server.annotations.CallbackParam;
import com.linkedin.restli.server.annotations.Key;
import com.linkedin.restli.server.annotations.RestLiAssociation;
import com.linkedin.restli.server.annotations.UnstructuredDataWriterParam;
import com.linkedin.restli.server.resources.unstructuredData.UnstructuredDataAssociationResourceAsyncTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * This resource models an association resource that produces unstructured data entities as results.
 */
@RestLiAssociation(
  name = "greetingAssociationUnstructuredDataAsync",
  namespace = "com.linkedin.restli.examples.greetings.client",
  assocKeys = {
    @Key(name = "src", type = String.class),
    @Key(name = "dest", type = String.class)
  }
)
public class GreetingUnstructuredDataAssociationResourceAsync extends UnstructuredDataAssociationResourceAsyncTemplate
{
  private static final ScheduledExecutorService _scheduler = Executors.newScheduledThreadPool(1);
  private static final int DELAY = 100;

  @Override
  public void get(CompoundKey key, @UnstructuredDataWriterParam UnstructuredDataWriter writer, @CallbackParam Callback<Void> callback)
  {
    _scheduler.schedule(() ->
                        {
                          try
                          {
                            GreetingUnstructuredDataUtils.respondGoodUnstructuredData(writer);
                            callback.onSuccess(null);
                          }
                          catch (final Throwable throwable)
                          {
                            callback.onError(throwable);
                          }
                        }, DELAY, TimeUnit.MILLISECONDS);
  }
}