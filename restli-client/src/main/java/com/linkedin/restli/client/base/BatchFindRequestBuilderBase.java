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

package com.linkedin.restli.client.base;


import com.linkedin.data.schema.PathSpec;
import com.linkedin.data.template.RecordTemplate;
import com.linkedin.restli.client.BatchFindRequestBuilder;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.common.ResourceSpec;

/**
 * A base class for generating resource specific {@link BatchFindRequestBuilder}
 *
 * @author Jiaqi Guan
 */
public abstract class BatchFindRequestBuilderBase<
        K,
        V extends RecordTemplate,
        RB extends BatchFindRequestBuilderBase<K, V, RB>>
        extends BatchFindRequestBuilder<K, V>
{
  protected BatchFindRequestBuilderBase(String baseUriTemplate,
                                        Class<V> elementClass,
                                        ResourceSpec resourceSpec,
                                        RestliRequestOptions requestOptions)
  {
    super(baseUriTemplate, elementClass, resourceSpec, requestOptions);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB name(String name)
  {
    return (RB) super.name(name);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB assocKey(String key, Object value)
  {
    return (RB) super.assocKey(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB paginate(int start, int count)
  {
    return (RB) super.paginate(start, count);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB paginateStart(int start)
  {
    return (RB) super.paginateStart(start);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB paginateCount(int count)
  {
    return (RB) super.paginateCount(count);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB fields(PathSpec... fieldPaths)
  {
    return (RB) super.fields(fieldPaths);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB metadataFields(PathSpec... metadataFieldPaths)
  {
    return (RB) super.metadataFields(metadataFieldPaths);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB pagingFields(PathSpec... pagingFieldPaths)
  {
    return (RB) super.pagingFields(pagingFieldPaths);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB setHeader(String key, String value)
  {
    return (RB) super.setHeader(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB setParam(String key, Object value)
  {
    return (RB) super.setParam(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB setReqParam(String key, Object value)
  {
    return (RB) super.setReqParam(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB addParam(String key, Object value)
  {
    return (RB) super.addParam(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB addReqParam(String key, Object value)
  {
    return (RB) super.addReqParam(key, value);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public RB pathKey(String name, Object value)
  {
    return (RB) super.pathKey(name, value);
  }
}
