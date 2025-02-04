/*
   Copyright (c) 2012 LinkedIn Corp.

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

/**
 * $Id: $
 */

package com.linkedin.restli.server;

import java.util.Collections;
import java.util.Map;

/**
 * @author Josh Walker
 * @version $Revision: $
 */
public class BatchUpdateResult<K, V>
{
  private final Map<K, UpdateResponse>         _results;
  private final Map<K, RestLiServiceException> _errors;

  public BatchUpdateResult(final Map<K, UpdateResponse> results)
  {
    this(results, Collections.emptyMap());
  }

  /**
   * Constructs a <code>BatchUpdateResult</code> with the given results and errors. It is expected
   * that, if a <code>RestLiServiceException</code> is provided for a given key in the errors map,
   * no <code>UpdateResponse</code> should be provided for the same key in the results map. In case
   * both an <code>UpdateResponse</code> and a <code>RestLiServiceException</code> are provided for
   * the same key, the <code>RestLiServiceException</code> takes precedence.
   */
  public BatchUpdateResult(final Map<K, UpdateResponse> results,
                           final Map<K, RestLiServiceException> errors)
  {
    _results = results;
    _errors = errors;
  }

  public Map<K, UpdateResponse> getResults()
  {
    return _results;
  }

  public Map<K, RestLiServiceException> getErrors()
  {
    return _errors;
  }
}
