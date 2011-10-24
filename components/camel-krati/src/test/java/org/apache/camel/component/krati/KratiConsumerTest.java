/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.component.krati;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class KratiConsumerTest extends CamelTestSupport {

    @Test
    public void testPutAndConsume() throws InterruptedException {
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBodyAndHeader("direct:put", "TEST1", KratiConstants.KEY, "1");
        template.sendBodyAndHeader("direct:put", "TEST2", KratiConstants.KEY, "2");
        template.sendBodyAndHeader("direct:put", "TEST3", KratiConstants.KEY, "3");
        MockEndpoint endpoint = (MockEndpoint) context.getEndpoint("mock:results");
        endpoint.expectedMessageCount(3);
        endpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:put")
                        .to("krati:target/test/consumertest");
                from("krati:target/test/consumertest")
                        .to("mock:results");

            }
        };
    }
}
