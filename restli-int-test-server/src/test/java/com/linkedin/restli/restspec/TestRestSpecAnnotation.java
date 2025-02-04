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

package com.linkedin.restli.restspec;


import com.linkedin.data.DataMap;
import com.linkedin.restli.internal.server.util.DataMapUtils;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * This test does not generate the idl files.
 * Instead, it relies on the fact that test idl files are generated before tests start in gradle build.
 *
 * @author Keren Jin
 */
public class TestRestSpecAnnotation
{
  public TestRestSpecAnnotation()
  {
    GENERATED_IDL_DIR = System.getProperty(IDL_DIR_PROP) + File.separator;

    String projectDir = System.getProperty(PROJECT_DIR_PROP);
    if (projectDir == null)
    {
      projectDir = "restli-int-test-server";
    }

    EXPECTED_IDL_DIR = projectDir + File.separator + RESOURCES_SUFFIX;
  }

  @Test(dataProvider = "annotationFiles")
  public void testExport(String annotationFile) throws FileNotFoundException
  {
    final DataMap actualRestSpecData = DataMapUtils.readMap(
        new FileInputStream(GENERATED_IDL_DIR + annotationFile), Collections.emptyMap());
    final DataMap expectedRestSpecData = DataMapUtils.readMap(
        new FileInputStream(EXPECTED_IDL_DIR + annotationFile), Collections.emptyMap());
    Assert.assertEquals(actualRestSpecData, expectedRestSpecData);
  }

  @Test(dataProvider = "annotationFiles")
  public void testParse(String annotationFile) throws FileNotFoundException
  {
    final DataMap generatedRestSpecData = DataMapUtils.readMap(
        new FileInputStream(GENERATED_IDL_DIR + annotationFile), Collections.emptyMap());
    final ResourceSchema schema = new ResourceSchema(generatedRestSpecData);
    Assert.assertTrue(schema.hasAnnotations());
    final DataMap parsedRestSpecData = schema.getAnnotations().data();
    Assert.assertEquals(parsedRestSpecData, generatedRestSpecData.getDataMap("annotations"));
  }

  private static final String RESOURCES_SUFFIX = "src" + File.separator + "test" + File.separator + "resources" + File.separator;
  private static final String PROJECT_DIR_PROP = "test.projectDir";
  private static final String IDL_DIR_PROP = "test.idlDir";

  @DataProvider
  private Object[][] annotationFiles() {
    return new Object[][] {
      { "com.linkedin.restli.restspec.testAnnotation.restspec.json" },
      { "com.linkedin.restli.restspec.testDeprecationAnnotation.restspec.json" }
    };
  }

  private final String GENERATED_IDL_DIR;
  private final String EXPECTED_IDL_DIR;
}
