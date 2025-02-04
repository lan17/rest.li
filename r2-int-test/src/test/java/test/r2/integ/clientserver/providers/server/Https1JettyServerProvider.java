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
import com.linkedin.r2.transport.common.Server;
import com.linkedin.r2.transport.common.bridge.server.TransportDispatcher;
import com.linkedin.r2.transport.http.server.HttpServerFactory;
import test.r2.integ.clientserver.providers.common.SslContextUtil;

public class Https1JettyServerProvider implements ServerProvider
{
  private final boolean _serverROS;

  public Https1JettyServerProvider(boolean serverROS)
  {
    _serverROS = serverROS;
  }

  @Override
  public Server createServer(FilterChain filters, int sslPort)
  {
    return Bootstrap.createHttpsServer(
      SslContextUtil.getHttpPortFromHttps(sslPort),
      sslPort,
      SslContextUtil.KEY_STORE,
      SslContextUtil.KEY_STORE_PASSWORD,
      filters,
      _serverROS
    );
  }


  @Override
  public Server createServer(FilterChain filters, int sslPort, TransportDispatcher transportDispatcher)
  {
    return Bootstrap.createHttpsServer(
        SslContextUtil.getHttpPortFromHttps(sslPort),
        sslPort,
        SslContextUtil.KEY_STORE,
        SslContextUtil.KEY_STORE_PASSWORD,
        filters,
        _serverROS,
        transportDispatcher
    );
  }

  @Override
  public Server createServer(ServerCreationContext context)
  {
    int sslPort = context.getPort();
    int httpPort = SslContextUtil.getHttpPortFromHttps(sslPort);
    return new HttpServerFactory(context.getFilterChain()).createHttpsServer(httpPort, sslPort, SslContextUtil.KEY_STORE,
        SslContextUtil.KEY_STORE_PASSWORD, context.getContextPath(),
        context.getThreadPoolSize(), context.getTransportDispatcher(), HttpServerFactory.DEFAULT_SERVLET_TYPE,
        context.getServerTimeout(), _serverROS);
  }

  @Override
  public boolean isSsl()
  {
    return true;
  }

  @Override
  public String toString()
  {
    return "[" + getClass().getName() + ", stream=" + _serverROS + "]";
  }
}
