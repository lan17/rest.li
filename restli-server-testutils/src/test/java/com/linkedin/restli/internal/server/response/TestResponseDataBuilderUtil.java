/*
   Copyright (c) 2016 LinkedIn Corp.

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

package com.linkedin.restli.internal.server.response;


import com.linkedin.restli.common.CollectionMetadata;
import com.linkedin.restli.common.EmptyRecord;
import com.linkedin.restli.common.HttpStatus;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.server.RestLiResponseData;
import com.linkedin.restli.server.RestLiServiceException;
import java.util.Collections;
import org.testng.annotations.Test;

import static com.linkedin.restli.common.ResourceMethod.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;


public class TestResponseDataBuilderUtil
{
  private RestLiServiceException _exception = new RestLiServiceException(HttpStatus.S_500_INTERNAL_SERVER_ERROR);

  @Test
  public void testRecordResponseDataBuilder()
  {
    verifyRecordResponseData(ResponseDataBuilderUtil.buildGetResponseData(HttpStatus.S_200_OK, new EmptyRecord()), GET);
    verifyExceptionRecordResponseData(ResponseDataBuilderUtil.buildGetResponseData(_exception), GET);

    verifyRecordResponseData(ResponseDataBuilderUtil.buildCreateResponseData(HttpStatus.S_200_OK, new EmptyRecord()),
                             CREATE);
    verifyExceptionRecordResponseData(ResponseDataBuilderUtil.buildCreateResponseData(_exception), CREATE);

    verifyRecordResponseData(ResponseDataBuilderUtil.buildActionResponseData(HttpStatus.S_200_OK, new EmptyRecord()),
                             ACTION);
    verifyExceptionRecordResponseData(ResponseDataBuilderUtil.buildActionResponseData(_exception), ACTION);
  }

  @Test
  public void testBatchCreateResponseDataBuilder()
  {
    verifyBatchCreateResponseData(
        ResponseDataBuilderUtil.buildBatchCreateResponseData(
            HttpStatus.S_200_OK, Collections.emptyList()),
        BATCH_CREATE);
    verifyExceptionBatchCreateResponseData(ResponseDataBuilderUtil.buildBatchCreateResponseData(_exception),
                                           BATCH_CREATE);
  }

  @Test
  public void testCollectionResponseDataBuilder()
  {
    verifyCollectionResponseData(
        ResponseDataBuilderUtil.buildFinderResponseData(
            HttpStatus.S_200_OK, Collections.emptyList(), new CollectionMetadata(), new EmptyRecord()),
        FINDER);
    verifyExceptionCollectionResponseData(ResponseDataBuilderUtil.buildFinderResponseData(_exception), FINDER);

    verifyCollectionResponseData(
        ResponseDataBuilderUtil.buildGetAllResponseData(
            HttpStatus.S_200_OK, Collections.emptyList(), new CollectionMetadata(), new EmptyRecord()),
        GET_ALL);
    verifyExceptionCollectionResponseData(ResponseDataBuilderUtil.buildGetAllResponseData(_exception), GET_ALL);
  }

  @Test
  public void testBatchResponseDataBuilder()
  {
    verifyBatchResponseData(
        ResponseDataBuilderUtil.buildBatchGetResponseData(
            HttpStatus.S_200_OK, Collections.emptyMap()), BATCH_GET);
    verifyExceptionBatchResponseData(ResponseDataBuilderUtil.buildBatchGetResponseData(_exception), BATCH_GET);

    verifyBatchResponseData(
        ResponseDataBuilderUtil.buildBatchUpdateResponseData(
            HttpStatus.S_200_OK, Collections.emptyMap()),
        BATCH_UPDATE);
    verifyExceptionBatchResponseData(ResponseDataBuilderUtil.buildBatchUpdateResponseData(_exception), BATCH_UPDATE);

    verifyBatchResponseData(
        ResponseDataBuilderUtil.buildBatchPartialUpdateResponseData(
            HttpStatus.S_200_OK, Collections.emptyMap()),
        BATCH_PARTIAL_UPDATE);
    verifyExceptionBatchResponseData(ResponseDataBuilderUtil.buildBatchPartialUpdateResponseData(_exception),
                                     BATCH_PARTIAL_UPDATE);

    verifyBatchResponseData(
        ResponseDataBuilderUtil.buildBatchDeleteResponseData(
            HttpStatus.S_200_OK, Collections.emptyMap()),
        BATCH_DELETE);
    verifyExceptionBatchResponseData(ResponseDataBuilderUtil.buildBatchDeleteResponseData(_exception), BATCH_DELETE);
  }

  @Test
  public void testStatusResponseDataBuilder()
  {
    verifyStatusResponseData(ResponseDataBuilderUtil.buildDeleteResponseData(HttpStatus.S_200_OK), DELETE);
    verifyExceptionStatusResponseData(ResponseDataBuilderUtil.buildDeleteResponseData(_exception), DELETE);

    verifyStatusResponseData(ResponseDataBuilderUtil.buildUpdateResponseData(HttpStatus.S_200_OK), UPDATE);
    verifyExceptionStatusResponseData(ResponseDataBuilderUtil.buildUpdateResponseData(_exception), UPDATE);

    verifyStatusResponseData(ResponseDataBuilderUtil.buildPartialUpdateResponseData(HttpStatus.S_200_OK),
                             PARTIAL_UPDATE);
    verifyExceptionStatusResponseData(ResponseDataBuilderUtil.buildPartialUpdateResponseData(_exception),
                                      PARTIAL_UPDATE);

    verifyStatusResponseData(ResponseDataBuilderUtil.buildOptionsResponseData(HttpStatus.S_200_OK), OPTIONS);
    verifyExceptionStatusResponseData(ResponseDataBuilderUtil.buildOptionsResponseData(_exception), OPTIONS);
  }

  private void verifyRecordResponseData(RestLiResponseData<? extends RecordResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNotNull(responseData.getResponseEnvelope().getRecord());
    assertNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyExceptionRecordResponseData(RestLiResponseData<? extends RecordResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNull(responseData.getResponseEnvelope().getRecord());
    assertNotNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyBatchCreateResponseData(RestLiResponseData<BatchCreateResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNotNull(responseData.getResponseEnvelope().getCreateResponses());
    assertNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyExceptionBatchCreateResponseData(RestLiResponseData<BatchCreateResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNull(responseData.getResponseEnvelope().getCreateResponses());
    assertNotNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyCollectionResponseData(RestLiResponseData<? extends CollectionResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNotNull(responseData.getResponseEnvelope().getCollectionResponse());
    assertNotNull(responseData.getResponseEnvelope().getCollectionResponsePaging());
    assertNotNull(responseData.getResponseEnvelope().getCollectionResponseCustomMetadata());
    assertNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyExceptionCollectionResponseData(RestLiResponseData<? extends CollectionResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNull(responseData.getResponseEnvelope().getCollectionResponse());
    assertNull(responseData.getResponseEnvelope().getCollectionResponsePaging());
    assertNull(responseData.getResponseEnvelope().getCollectionResponseCustomMetadata());
    assertNotNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyBatchResponseData(RestLiResponseData<? extends BatchResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNotNull(responseData.getResponseEnvelope().getBatchResponseMap());
    assertNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyExceptionBatchResponseData(RestLiResponseData<? extends BatchResponseEnvelope> responseData, ResourceMethod expectedMethod)
  {
    assertNull(responseData.getResponseEnvelope().getBatchResponseMap());
    assertNotNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyStatusResponseData(RestLiResponseData<?> responseData, ResourceMethod expectedMethod)
  {
    assertNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }

  private void verifyExceptionStatusResponseData(RestLiResponseData<?> responseData, ResourceMethod expectedMethod)
  {
    assertNotNull(responseData.getResponseEnvelope().getException());
    assertEquals(responseData.getResourceMethod(), expectedMethod);
  }
}
