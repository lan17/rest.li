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

package com.linkedin.restli.server;


import com.linkedin.restli.common.HttpStatus;
import java.util.Objects;


/**
 * @author Keren Jin
 */
public class ActionResult<V>
{
  private final V _value;
  private final HttpStatus _status;

  public ActionResult(V value)
  {
    _value = value;
    _status = HttpStatus.S_200_OK;
  }

  public ActionResult(V value, HttpStatus status)
  {
    _value = value;
    _status = status;
  }

  public ActionResult(HttpStatus status)
  {
    _value = null;
    _status = status;
  }

  public V getValue()
  {
    return _value;
  }

  public HttpStatus getStatus()
  {
    return _status;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    ActionResult<?> that = (ActionResult<?>) o;
    return Objects.equals(_value, that._value) && _status == that._status;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(_value, _status);
  }
}
