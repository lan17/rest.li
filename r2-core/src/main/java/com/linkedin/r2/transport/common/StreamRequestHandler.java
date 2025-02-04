package com.linkedin.r2.transport.common;

import com.linkedin.common.callback.Callback;
import com.linkedin.r2.message.RequestContext;
import com.linkedin.r2.message.stream.StreamRequest;
import com.linkedin.r2.message.stream.StreamResponse;

/**
 * A request handler for {@link com.linkedin.r2.message.stream.StreamRequest}s.
 *
 * @author Zhenkai Zhu
 */
public interface StreamRequestHandler
{
  /**
   * Handles the supplied request and notifies the supplied callback upon completion.
   *
   * <p>
   * If this is a dispatcher, as defined in the class documentation, then this method should return
   * {@link com.linkedin.r2.message.rest.RestStatus#NOT_FOUND} if no handler can be found for the
   * request.
   *
   * @param request The stream request to process.
   * @param requestContext {@link com.linkedin.r2.message.RequestContext} context for the request
   * @param callback The callback to notify when request processing has completed. When callback with an error, use
   *                 {@link com.linkedin.r2.message.stream.StreamException} to provide custom response status code,
   *                 headers, and response body.
   */
  void handleRequest(StreamRequest request, RequestContext requestContext, Callback<StreamResponse> callback);
}
