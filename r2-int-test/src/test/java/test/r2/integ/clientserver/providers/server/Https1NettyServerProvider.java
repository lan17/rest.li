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
import java.io.IOException;
import test.r2.integ.clientserver.providers.common.SslContextUtil;

public class Https1NettyServerProvider implements ServerProvider
{
  public Https1NettyServerProvider()
  {
  }

  @Override
  public Server createServer(FilterChain filters, int port) throws Exception
  {
    final TransportDispatcher dispatcher = getTransportDispatcher();
    return createServer(filters, port, dispatcher);
  }

  @Override
  public Server createServer(FilterChain filters, int port, TransportDispatcher dispatcher) throws Exception
  {
    Server httpServer = new Http1NettyServerProvider().createServer(filters, SslContextUtil.getHttpPortFromHttps(port));
    Server httpsServer = new HttpNettyServerBuilder()
        .port(port)
        .filters(filters)
        .transportDispatcher(dispatcher)
        .sslContext(SslContextUtil.getContext()).build();

    // start both an http and https server
    return new HttpAndHttpsServer(httpServer, httpsServer);
  }

  @Override
  public Server createServer(ServerCreationContext context) throws Exception
  {
    return createServer(context.getFilterChain(), context.getPort(), context.getTransportDispatcher());
  }

  protected TransportDispatcher getTransportDispatcher()
  {
    return new TransportDispatcherBuilder()
      .addRestHandler(Bootstrap.getEchoURI(), new RestEchoServer(new EchoServiceImpl()))
      .build();
  }

  @Override
  public boolean isSsl()
  {
    return true;
  }

  @Override
  public String toString()
  {
    return "[" + getClass().getName() + "]";
  }

  private class HttpAndHttpsServer implements Server
  {
    private final Server _httpServer;
    private final Server _httpsServer;

    public HttpAndHttpsServer(Server httpServer, Server httpsServer)
    {

      _httpServer = httpServer;
      _httpsServer = httpsServer;
    }

    @Override
    public void start() throws IOException
    {
      _httpServer.start();
      _httpsServer.start();
    }

    @Override
    public void stop() throws IOException
    {
      try
      {
        _httpServer.stop();
      }
      catch (Exception ex)
      {
        // DO NOTHING
      }

      try
      {
        _httpsServer.stop();
      }
      catch (Exception ex)
      {
        // DO NOTHING
      }
    }

    @Override
    public void waitForStop() throws InterruptedException
    {
      try
      {
        _httpServer.waitForStop();
      }
      catch (Exception ex)
      {
        // DO NOTHING
      }

      try
      {
        _httpsServer.waitForStop();
      }
      catch (Exception ex)
      {
        // DO NOTHING
      }
    }
  }

}
