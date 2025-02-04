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

package test.r2.integ.clientserver.providers.server;

import com.linkedin.r2.filter.FilterChain;
import com.linkedin.r2.sample.Bootstrap;
import com.linkedin.r2.sample.echo.EchoServiceImpl;
import com.linkedin.r2.sample.echo.rest.RestEchoServer;
import com.linkedin.r2.transport.common.Server;
import com.linkedin.r2.transport.common.bridge.server.TransportDispatcher;
import com.linkedin.r2.transport.common.bridge.server.TransportDispatcherBuilder;
import com.linkedin.r2.transport.http.server.HttpNettyServerBuilder;

public class Http1NettyServerProvider implements ServerProvider
{
  public Http1NettyServerProvider(){}

  @Override
  public Server createServer(FilterChain filters, int port)
  {
    final TransportDispatcher dispatcher = getTransportDispatcher();

    return new HttpNettyServerBuilder().filters(filters).port(port).transportDispatcher(dispatcher).build();
  }

  @Override
  public Server createServer(FilterChain filters, int port, TransportDispatcher dispatcher) throws Exception
  {
    return new HttpNettyServerBuilder().filters(filters).port(port).transportDispatcher(dispatcher).build();
  }

  @Override
  public Server createServer(ServerCreationContext context)
  {
    return new HttpNettyServerBuilder().filters(context.getFilterChain()).port(context.getPort()).
        transportDispatcher(context.getTransportDispatcher()).build();
  }

  protected TransportDispatcher getTransportDispatcher()
  {
    return new TransportDispatcherBuilder()
      .addRestHandler(Bootstrap.getEchoURI(), new RestEchoServer(new EchoServiceImpl()))
      .build();
  }

  @Override
  public String toString()
  {
    return "[" + getClass().getName() + "]";
  }
}
