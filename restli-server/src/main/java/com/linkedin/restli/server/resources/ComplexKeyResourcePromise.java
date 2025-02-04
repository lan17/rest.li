/*
   Copyright (c) 2014 LinkedIn Corp.

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

package com.linkedin.restli.server.resources;


import com.linkedin.data.template.RecordTemplate;
import com.linkedin.restli.common.ComplexResourceKey;


/**
 * @deprecated Use {@link ComplexKeyResourceTask} instead.
 * @author kparikh
 */
@Deprecated
public interface ComplexKeyResourcePromise<K extends RecordTemplate, P extends RecordTemplate, V extends RecordTemplate>
    extends CollectionResourcePromise<ComplexResourceKey<K, P>, V>
{
}
