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

package com.linkedin.pegasus.generator.test;

import com.linkedin.data.schema.RecordDataSchema;
import com.linkedin.data.template.DataTemplateUtil;
import com.linkedin.data.template.DoubleArray;
import com.linkedin.data.template.DoubleMap;
import com.linkedin.data.template.IntegerArray;
import com.linkedin.data.template.IntegerMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


/**
 * @author Josh Walker
 * @version $Revision: $
 */

public class TestTypeRefRecordTemplate
{
  @Test
  public void testWrapTypeRef()
  {
    TyperefTest typeref = new TyperefTest();
    typeref.setBar1(new RecordBar());

    //instantiate a new TyperefTest to ensure we start with an empty cache of wrapped objects.
    TyperefTest typeref2 = new TyperefTest(typeref.data());
    RecordBar b = typeref2.getBar1();
    Assert.assertNotNull(b);
  }

  @Test
  public void testArraySchema()
  {
    TyperefTest record = new TyperefTest();
    RecordDataSchema recordDataSchema = record.schema();

    record.setDoubleRefArray(new DoubleArray());
    DoubleArray doubleArray = record.getDoubleRefArray();
    assertEquals(doubleArray.schema(), DataTemplateUtil.getSchema(DoubleArray.class));
    assertNotEquals(recordDataSchema.getField("doubleRefArray").getType(), doubleArray.schema());

    record.setIntArray(new IntegerArray());
    IntegerArray intArray = record.getIntArray();
    assertEquals(intArray.schema(), DataTemplateUtil.getSchema(IntegerArray.class));
    assertNotEquals(recordDataSchema.getField("intArray").getType(), intArray.schema());

    record.setIntRefArray(intArray);
    intArray = record.getIntRefArray();
    assertEquals(intArray.schema(), DataTemplateUtil.getSchema(IntegerArray.class));
    assertNotEquals(recordDataSchema.getField("intRefArray").getType(), intArray.schema());

    assertNotEquals(recordDataSchema.getField("intArray").getType(), recordDataSchema.getField("intRefArray").getType());
  }

  @Test
  public void testMapSchema()
  {
    TyperefTest record = new TyperefTest();
    RecordDataSchema recordDataSchema = record.schema();

    record.setDoubleRefMap(new DoubleMap());
    DoubleMap doubleMap = record.getDoubleRefMap();
    assertEquals(doubleMap.schema(), DataTemplateUtil.getSchema(DoubleMap.class));
    assertNotEquals(recordDataSchema.getField("doubleRefMap").getType(), doubleMap.schema());

    record.setIntMap(new IntegerMap());
    IntegerMap intMap = record.getIntMap();
    assertEquals(intMap.schema(), DataTemplateUtil.getSchema(IntegerMap.class));
    assertNotEquals(recordDataSchema.getField("intMap").getType(), intMap.schema());

    record.setIntRefMap(intMap);
    intMap = record.getIntRefMap();
    assertEquals(intMap.schema(), DataTemplateUtil.getSchema(IntegerMap.class));
    assertNotEquals(recordDataSchema.getField("intRefMap").getType(), intMap.schema());

    assertNotEquals(recordDataSchema.getField("intMap").getType(), recordDataSchema.getField("intRefMap").getType());
  }
}
