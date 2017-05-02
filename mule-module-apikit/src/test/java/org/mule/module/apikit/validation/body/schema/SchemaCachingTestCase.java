/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.apikit.validation.body.schema;

import static com.jayway.restassured.RestAssured.given;

import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.test.runner.ArtifactClassLoaderRunnerConfig;

import com.jayway.restassured.RestAssured;

import org.junit.Rule;
import org.junit.Test;

@ArtifactClassLoaderRunnerConfig
public class SchemaCachingTestCase extends MuleArtifactFunctionalTestCase
{
    @Rule
    public DynamicPort serverPort = new DynamicPort("serverPort");

    @Override
    public int getTestTimeoutSecs()
    {
        return 6000;
    }

    @Override
    protected void doSetUp() throws Exception
    {
        RestAssured.port = serverPort.getNumber();
        super.doSetUp();
    }

    @Override
    protected String getConfigResources()
    {
        return "org/mule/module/apikit/validation/body/schema/schema-cache-config.xml";
    }

    @Test
    public void putOnBoth() throws Exception
    {
        given()
            .body("{\"name\":\"gbs\"}").contentType("application/json")
            .expect().statusCode(200)//204
            .when().put("/cam/currentuser");

        given()
            .body("{\"id\":\"gbs\"}").contentType("application/json")
            .expect().statusCode(200)//204
            .when().put("/peaks/currentuser");
    }

}
