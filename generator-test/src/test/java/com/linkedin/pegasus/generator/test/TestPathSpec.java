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

import com.linkedin.data.schema.PathSpec;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Josh Walker
 * @version $Revision: $
 */

public class TestPathSpec
{
  @Test
  public void testNestedFieldPathSpec()
  {
    PathSpec p = RecordTest.fields().recordField().location();
    Assert.assertEquals(p.toString(), "/recordField/location");
  }

  @Test
  public void testSelfReferencePathSpec()
  {
    PathSpec p = AliasTest.fields().a1().a1().a1().a1();
    Assert.assertEquals(p.toString(), "/a1/a1/a1/a1");
  }

  @Test
  public void testArrayWildcardPathSpec()
  {
    PathSpec p = ArrayTest.fields().recordInlineArray().items().f();
    Assert.assertEquals(p.toString(), "/recordInlineArray/*/f");
  }

  @Test
  public void testArrayRangePathSpec()
  {
    PathSpec p = ArrayTest.fields().intArray(10, 5);
    Assert.assertEquals(p.toString(), "/intArray?start=10&count=5");

    p = ArrayTest.fields().recordInlineArray(null, 2);
    Assert.assertEquals(p.toString(), "/recordInlineArray?count=2");

    p = ArrayTest.fields().unionArray(8, null);
    Assert.assertEquals(p.toString(), "/unionArray?start=8");

    p = ArrayTest.fields().stringArray(null, null);
    Assert.assertEquals(p.toString(), "/stringArray");
  }

  @Test
  public void testMapWildcardPathSpec()
  {
    PathSpec p = MapTest.fields().recordInlineMap().values().f();
    Assert.assertEquals(p.toString(), "/recordInlineMap/*/f");
  }

  @Test
  public void testUnionPathSpec()
  {
    PathSpec p = UnionTest.fields().unionWithInline().RecordInUnion().a();
    Assert.assertEquals(p.toString(), "/unionWithInline/com.linkedin.pegasus.generator.test.RecordInUnion/a");

    p = UnionTest.fields().unionWithoutNull().RecordBar().location();
    Assert.assertEquals(p.toString(), "/unionWithoutNull/com.linkedin.pegasus.generator.test.RecordBar/location");

    p = UnionTest.fields().unionWithNull().Null();
    Assert.assertEquals(p.toString(), "/unionWithNull/null");

    // Test path specs for Union member with aliases
    p = UnionTest.fields().unionWithAliases().MemRecord().location();
    Assert.assertEquals(p.toString(), "/unionWithAliases/memRecord/location");

    p = UnionTest.fields().unionWithAliases().MemArray();
    Assert.assertEquals(p.toString(), "/unionWithAliases/memArray");

    p = UnionTest.fields().unionWithAliases().MemMap();
    Assert.assertEquals(p.toString(), "/unionWithAliases/memMap");

    p = UnionTest.fields().unionWithAliases().Null();
    Assert.assertEquals(p.toString(), "/unionWithAliases/null");
  }

  @Test
  public void testTyperefPathSpec()
  {
    PathSpec p = TyperefTest.fields().bar1().location();
    Assert.assertEquals(p.toString(), "/bar1/location");

    p = TyperefTest.fields().barRefMap().values().location();
    Assert.assertEquals(p.toString(), "/barRefMap/*/location");
  }

  @Test
  public void testPathSpecs()
  {
    checkPathSpec(AliasTest.fields().a1().a1(), "/a1/a1");
    checkPathSpec(AliasTest.fields().a2().a1(), "/a2/a1");
    checkPathSpec(AliasTest.fields().a3().a1(), "/a3/a1");
    checkPathSpec(AliasTest.fields().a4().a1(), "/a4/a1");
    checkPathSpec(AliasTest.fields().a5().a1(), "/a5/a1");
    checkPathSpec(AliasTest.fields().a6().a1(), "/a6/a1");
    checkPathSpec(AliasTest.fields().a7().b1(), "/a7/b1");

    checkPathSpec(ArrayTest.fields().intArray(), "/intArray");
    checkPathSpec(ArrayTest.fields().longArray(), "/longArray");
    checkPathSpec(ArrayTest.fields().floatArray(), "/floatArray");
    checkPathSpec(ArrayTest.fields().doubleArray(), "/doubleArray");
    checkPathSpec(ArrayTest.fields().booleanArray(), "/booleanArray");
    checkPathSpec(ArrayTest.fields().stringArray(), "/stringArray");
    checkPathSpec(ArrayTest.fields().bytesArray(), "/bytesArray");
    checkPathSpec(ArrayTest.fields().intMapArray(), "/intMapArray");
    checkPathSpec(ArrayTest.fields().longMapArray(), "/longMapArray");
    checkPathSpec(ArrayTest.fields().floatMapArray(), "/floatMapArray");
    checkPathSpec(ArrayTest.fields().doubleMapArray(), "/doubleMapArray");
    checkPathSpec(ArrayTest.fields().booleanMapArray(), "/booleanMapArray");
    checkPathSpec(ArrayTest.fields().stringMapArray(), "/stringMapArray");
    checkPathSpec(ArrayTest.fields().bytesMapArray(), "/bytesMapArray");
    checkPathSpec(ArrayTest.fields().enumFruitsArray(), "/enumFruitsArray");
    checkPathSpec(ArrayTest.fields().enumInlineArray(), "/enumInlineArray");
    checkPathSpec(ArrayTest.fields().recordArray().items().location(), "/recordArray/*/location");
    checkPathSpec(ArrayTest.fields().recordInlineArray().items().f(), "/recordInlineArray/*/f");
    checkPathSpec(ArrayTest.fields().fixedArray(), "/fixedArray");
    checkPathSpec(ArrayTest.fields().fixedInlineArray(), "/fixedInlineArray");
    checkPathSpec(ArrayTest.fields().unionArray().items().Null(), "/unionArray/*/null");
    checkPathSpec(ArrayTest.fields().unionArray().items().Int(), "/unionArray/*/int");
    checkPathSpec(ArrayTest.fields().unionArray().items().String(), "/unionArray/*/string");
    checkPathSpec(ArrayTest.fields().unionArray().items().Array(), "/unionArray/*/array");
    checkPathSpec(ArrayTest.fields().unionArray().items().Map(), "/unionArray/*/map");
    checkPathSpec(ArrayTest.fields().unionArray().items().EnumFruits(), "/unionArray/*/com.linkedin.pegasus.generator.test.EnumFruits");
    checkPathSpec(ArrayTest.fields().unionArray().items().RecordBar().location(), "/unionArray/*/com.linkedin.pegasus.generator.test.RecordBar/location");
    checkPathSpec(ArrayTest.fields().unionArray().items().FixedMD5(), "/unionArray/*/com.linkedin.pegasus.generator.test.FixedMD5");

    checkPathSpec(CircularImport.fields().a().link().CircularImportA().link(), "/a/link/com.linkedin.pegasus.generator.test.CircularImportA/link");
    checkPathSpec(CircularImport.fields().a().link().CircularImportB().link(), "/a/link/com.linkedin.pegasus.generator.test.CircularImportB/link");
    checkPathSpec(CircularImport.fields().a().link().Null(), "/a/link/null");

    checkPathSpec(JavaReservedTest.fields().if_(), "/if");
    checkPathSpec(JavaReservedTest.fields().then(), "/then");
    checkPathSpec(JavaReservedTest.fields().for_(), "/for");
    checkPathSpec(JavaReservedTest.fields().while_(), "/while");
    checkPathSpec(JavaReservedTest.fields().case_(), "/case");
    checkPathSpec(JavaReservedTest.fields().break_(), "/break");
    checkPathSpec(JavaReservedTest.fields().try_(), "/try");
    checkPathSpec(JavaReservedTest.fields().union(), "/union");

    checkPathSpec(MapTest.fields().intMap(), "/intMap");
    checkPathSpec(MapTest.fields().longMap(), "/longMap");
    checkPathSpec(MapTest.fields().floatMap(), "/floatMap");
    checkPathSpec(MapTest.fields().doubleMap(), "/doubleMap");
    checkPathSpec(MapTest.fields().booleanMap(), "/booleanMap");
    checkPathSpec(MapTest.fields().stringMap(), "/stringMap");
    checkPathSpec(MapTest.fields().bytesMap(), "/bytesMap");
    checkPathSpec(MapTest.fields().intArrayMap(), "/intArrayMap");
    checkPathSpec(MapTest.fields().longArrayMap(), "/longArrayMap");
    checkPathSpec(MapTest.fields().floatArrayMap(), "/floatArrayMap");
    checkPathSpec(MapTest.fields().doubleArrayMap(), "/doubleArrayMap");
    checkPathSpec(MapTest.fields().booleanArrayMap(), "/booleanArrayMap");
    checkPathSpec(MapTest.fields().stringArrayMap(), "/stringArrayMap");
    checkPathSpec(MapTest.fields().bytesArrayMap(), "/bytesArrayMap");
    checkPathSpec(MapTest.fields().enumFruitsMap(), "/enumFruitsMap");
    checkPathSpec(MapTest.fields().enumInlineMap(), "/enumInlineMap");
    checkPathSpec(MapTest.fields().recordMap().values().location(), "/recordMap/*/location");
    checkPathSpec(MapTest.fields().recordInlineMap().values().f(), "/recordInlineMap/*/f");
    checkPathSpec(MapTest.fields().fixedMap(), "/fixedMap");
    checkPathSpec(MapTest.fields().fixedInlineMap(), "/fixedInlineMap");
    checkPathSpec(MapTest.fields().unionMap().values().Null(), "/unionMap/*/null");
    checkPathSpec(MapTest.fields().unionMap().values().Int(), "/unionMap/*/int");
    checkPathSpec(MapTest.fields().unionMap().values().String(), "/unionMap/*/string");
    checkPathSpec(MapTest.fields().unionMap().values().Array(), "/unionMap/*/array");
    checkPathSpec(MapTest.fields().unionMap().values().Map(), "/unionMap/*/map");
    checkPathSpec(MapTest.fields().unionMap().values().EnumFruits(), "/unionMap/*/com.linkedin.pegasus.generator.test.EnumFruits");
    checkPathSpec(MapTest.fields().unionMap().values().RecordBar(), "/unionMap/*/com.linkedin.pegasus.generator.test.RecordBar");
    checkPathSpec(MapTest.fields().unionMap().values().FixedMD5(), "/unionMap/*/com.linkedin.pegasus.generator.test.FixedMD5");
  }

  @Test
  public void testValidatePathSpecString()
  {
    Object[][] testStrings = new Object[][]
        {
            {
                "/field1/field2",
                true,
            },
            {
                "/field1",
                true,
            },
            {
                "/field1/*/field2", //field inside map
                true,
            },
            {
                "/field1/$key", // map key field
                true,
            },
            {
                "/field1/*", // array items
                true,
            },
            {
                "/intArray?start=10&count=5", // array pathSpec with range
                true,
            },
            {
                "/field1/*/$key", //nested map
                true,
            },
            {
                "",
                false,
            },
            {
                "/",
                false,
            },
            {
                "field1",
                false,
            },
            {
                "field1/",
                false,
            },
            {
                "/field1/",
                false,
            },
            {
                "field1/field2",
                false,
            },
            {
                "field1/field2/",
                false,
            },
            {
                "/field1//field2",
                false,
            },
        };
    for (Object[] validationPairs: testStrings)
    {
      Assert.assertEquals(validationPairs[1], PathSpec.validatePathSpecString((String) validationPairs[0]));
    }
  }

  private void checkPathSpec(PathSpec p, String expected)
  {
    Assert.assertEquals(p.toString(), expected);
  }
}
