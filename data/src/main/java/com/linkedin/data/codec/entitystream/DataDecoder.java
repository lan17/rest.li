/*
   Copyright (c) 2018 LinkedIn Corp.

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

package com.linkedin.data.codec.entitystream;

import com.linkedin.data.ByteString;
import com.linkedin.data.DataComplex;
import com.linkedin.entitystream.Reader;

import java.util.concurrent.CompletionStage;


/**
 * A DataDecoder parses bytes to a {@link DataComplex}. It is an entity stream {@link Reader} and reads {@link ByteString}
 * from an {@link com.linkedin.entitystream.EntityStream} in reactive streaming fashion.
 *
 * @param <T> The type of DataComplex. It can be a DataMap or a DataList.
 */
public interface DataDecoder<T extends DataComplex> extends Reader<ByteString>
{
  CompletionStage<T> getResult();
}
