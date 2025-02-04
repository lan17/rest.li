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

package com.linkedin.restli.internal.server.methods.arguments;

import com.linkedin.common.callback.Callback;
import com.linkedin.data.schema.IntegerDataSchema;
import com.linkedin.data.template.DataTemplateUtil;
import com.linkedin.r2.message.rest.RestRequest;
import com.linkedin.restli.common.ComplexResourceKey;
import com.linkedin.restli.common.CompoundKey;
import com.linkedin.restli.common.EmptyRecord;
import com.linkedin.restli.common.PatchRequest;
import com.linkedin.restli.common.test.MyComplexKey;
import com.linkedin.restli.internal.server.RoutingResult;
import com.linkedin.restli.internal.server.ServerResourceContext;
import com.linkedin.restli.internal.server.model.AnnotationSet;
import com.linkedin.restli.internal.server.model.Parameter;
import com.linkedin.restli.internal.server.model.ResourceMethodDescriptor;
import com.linkedin.restli.internal.server.model.ResourceModel;
import com.linkedin.restli.internal.server.util.DataMapUtils;
import com.linkedin.restli.server.Key;
import com.linkedin.restli.server.RestLiRequestData;
import com.linkedin.restli.server.UnstructuredDataReactiveReader;
import com.linkedin.restli.server.resources.CollectionResourceAsyncTemplate;
import com.linkedin.restli.server.resources.unstructuredData.UnstructuredDataCollectionResourceReactive;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * @author Soojung Ha
 */
public class TestUpdateArgumentBuilder
{
  @DataProvider(name = "argumentData")
  private Object[][] argumentData()
  {
    Parameter<?> myComplexKeyParam = new Parameter<>(
        "",
        MyComplexKey.class,
        DataTemplateUtil.getSchema(MyComplexKey.class),
        false,
        null,
        Parameter.ParamType.POST,
        false,
        new AnnotationSet(new Annotation[]{}));

    List<Parameter<?>> collectionResourceParams = new ArrayList<>();
    collectionResourceParams.add(new Parameter<>(
        "myComplexKeyCollectionId",
        Integer.class,
        new IntegerDataSchema(),
        false,
        null,
        Parameter.ParamType.RESOURCE_KEY,
        false,
        new AnnotationSet(new Annotation[]{})));
    collectionResourceParams.add(myComplexKeyParam);

    List<Parameter<?>> simpleResourceParams = new ArrayList<>();
    simpleResourceParams.add(myComplexKeyParam);

    List<Parameter<?>> associationResourceParams = new ArrayList<>();
    associationResourceParams.add(new Parameter<>(
        "myComplexKeyAssociationId",
        CompoundKey.class,
        null,
        false,
        null,
        Parameter.ParamType.RESOURCE_KEY,
        false,
        new AnnotationSet(new Annotation[]{})));
    associationResourceParams.add(myComplexKeyParam);

    List<Parameter<?>> complexResourceKeyParams = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    Parameter<ComplexResourceKey> complexResourceKeyParam = new Parameter<>(
        "complexKeyTestId",
        ComplexResourceKey.class,
        null,
        false,
        null,
        Parameter.ParamType.RESOURCE_KEY,
        false,
        new AnnotationSet(new Annotation[]{}));
    complexResourceKeyParams.add(complexResourceKeyParam);
    complexResourceKeyParams.add(myComplexKeyParam);

    return new Object[][]
        {
            {
                collectionResourceParams,
                new Key("myComplexKeyCollectionId", Integer.class, new IntegerDataSchema()),
                "myComplexKeyCollectionId",
                4545
            },
            {
                simpleResourceParams,
                null,
                null,
                null
            },
            {
                associationResourceParams,
                new Key("myComplexKeyAssociationId", CompoundKey.class, null),
                "myComplexKeyAssociationId",
                new CompoundKey().append("string1", "apples").append("string2", "oranges")
            },
            {
                complexResourceKeyParams,
                new Key("complexKeyTestId", ComplexResourceKey.class, null),
                "complexKeyTestId",
                new ComplexResourceKey<>(
                    new MyComplexKey().setA("keyString").setB(1234L), new EmptyRecord())
            }
        };
  }

  @Test(dataProvider = "argumentData")
  public void testArgumentBuilderSuccess(List<Parameter<?>> params, Key key, String keyName, Object keyValue)
      throws IOException, NoSuchMethodException {
    RestRequest request = RestLiArgumentBuilderTestHelper.getMockRequest(false, "{\"a\":\"xyz\",\"b\":123}");
    ResourceModel model = RestLiArgumentBuilderTestHelper.getMockResourceModel(MyComplexKey.class, key, true);
    ResourceMethodDescriptor descriptor;
    if (key != null)
    {
      descriptor = RestLiArgumentBuilderTestHelper.getMockResourceMethodDescriptor(model, 3, params, CollectionResourceAsyncTemplate.class.getMethod("update", Object.class, PatchRequest.class, Callback.class));
    }
    else
    {
      descriptor = RestLiArgumentBuilderTestHelper.getMockResourceMethodDescriptor(model, 2, params, CollectionResourceAsyncTemplate.class.getMethod("update", Object.class, PatchRequest.class, Callback.class));
    }
    ServerResourceContext context = RestLiArgumentBuilderTestHelper.getMockResourceContext(keyName, keyValue, null, true);
    RoutingResult routingResult;
    if (key != null)
    {
      routingResult = RestLiArgumentBuilderTestHelper.getMockRoutingResult(descriptor, 5, context, 2);
    }
    else
    {
      routingResult = RestLiArgumentBuilderTestHelper.getMockRoutingResult(descriptor, 4, context, 1);
    }

    RestLiArgumentBuilder argumentBuilder = new UpdateArgumentBuilder();
    RestLiRequestData requestData = argumentBuilder.extractRequestData(routingResult,
        DataMapUtils.readMapWithExceptions(request));
    Object[] args = argumentBuilder.buildArguments(requestData, routingResult);

    if (keyValue != null)
    {
      assertEquals(args.length, 2);
      assertEquals(args[0], keyValue);
    }

    assertTrue(args[args.length - 1] instanceof MyComplexKey);
    assertEquals(((MyComplexKey)args[args.length - 1]).getA(), "xyz");
    assertEquals((long) ((MyComplexKey)args[args.length - 1]).getB(), 123L);

    verify(request, model, descriptor, context, routingResult);
  }

  @Test(dataProvider = "argumentData")
  public void testUnstructuredDataArgumentBuilder(List<Parameter<?>> params, Key key, String keyName, Object keyValue) throws IOException, NoSuchMethodException
  {
    params.remove(0);
    RestRequest request = RestLiArgumentBuilderTestHelper.getMockRequest(false, "{\"a\":\"xyz\",\"b\":123}");
    ResourceModel model = RestLiArgumentBuilderTestHelper.getMockResourceModel(MyComplexKey.class, key, true);
    ResourceMethodDescriptor descriptor;
    if (key != null)
    {
      descriptor = RestLiArgumentBuilderTestHelper.getMockResourceMethodDescriptor(model, 3, params, UnstructuredDataCollectionResourceReactive.class.getMethod("update", Object.class, UnstructuredDataReactiveReader.class, Callback.class));
    }
    else
    {
      descriptor = RestLiArgumentBuilderTestHelper.getMockResourceMethodDescriptor(model, 2, params, UnstructuredDataCollectionResourceReactive.class.getMethod("update", Object.class, UnstructuredDataReactiveReader.class, Callback.class));
    }
    ServerResourceContext context = RestLiArgumentBuilderTestHelper.getMockResourceContext(keyName, keyValue, null, true);
    RoutingResult routingResult;
    if (key != null)
    {
      routingResult = RestLiArgumentBuilderTestHelper.getMockRoutingResult(descriptor, 5, context, 2);
    }
    else
    {
      routingResult = RestLiArgumentBuilderTestHelper.getMockRoutingResult(descriptor, 4, context, 1);
    }

    RestLiArgumentBuilder argumentBuilder = new UpdateArgumentBuilder();
    RestLiRequestData requestData = argumentBuilder.extractRequestData(routingResult,
        DataMapUtils.readMapWithExceptions(request));
    Object[] args = argumentBuilder.buildArguments(requestData, routingResult);
    if (keyValue != null)
    {
      assertEquals(args.length, 1);
      assertEquals(args[0], keyValue);
    }
    assertEquals(requestData.hasEntity(), false);
  }
}
