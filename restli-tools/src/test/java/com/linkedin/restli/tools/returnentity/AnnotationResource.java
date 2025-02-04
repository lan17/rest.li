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

package com.linkedin.restli.tools.returnentity;

import com.linkedin.restli.common.PatchRequest;
import com.linkedin.restli.internal.client.response.BatchEntityResponse;
import com.linkedin.restli.server.BatchCreateKVResult;
import com.linkedin.restli.server.BatchPatchRequest;
import com.linkedin.restli.server.CreateKVResponse;
import com.linkedin.restli.server.UpdateEntityResponse;
import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.annotations.RestMethod;
import com.linkedin.restli.server.annotations.ReturnEntity;
import com.linkedin.restli.server.resources.KeyValueResource;
import com.linkedin.restli.tools.DummyRecord;


/**
 * Simple resource to test IDL generation with "return entity" methods using annotations as indicators.
 *
 * @author Evan Williams
 */
@RestLiCollection(name = "annotation")
public class AnnotationResource implements KeyValueResource<Long, DummyRecord>
{
  @RestMethod.Create
  @ReturnEntity
  public CreateKVResponse<Long, DummyRecord> create()
  {
    return null;
  }

  @RestMethod.BatchCreate
  @ReturnEntity
  public BatchCreateKVResult<Long, DummyRecord> batchCreate()
  {
    return null;
  }

  @RestMethod.PartialUpdate
  @ReturnEntity
  public UpdateEntityResponse<DummyRecord> update(Long id, PatchRequest<DummyRecord> patch)
  {
    return null;
  }

  @RestMethod.BatchPartialUpdate
  @ReturnEntity
  public BatchEntityResponse<Long, DummyRecord> update(BatchPatchRequest<Long, DummyRecord> patches)
  {
    return null;
  }
}
