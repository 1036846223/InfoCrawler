package zero.info.proxy;


import zero.info.item.Page;
import zero.info.item.Request;
import zero.info.item.Task;

/**
 * Proxy provider. <br>
 *     
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     *
     * Return proxy to Provider when complete a download.
     * @param proxy the proxy config contains host,port and identify info
     * @param page the download result
     * @param task the download task
     */
    void returnProxy(Proxy proxy, Page page, Task task);

    /**
     * Get a proxy for task by some strategy.
     * @param task the download task
     * @return proxy 
     * @deprecated Use {@link #getProxy(Request, Task)} instead.
     */
    @Deprecated
    default Proxy getProxy(Task task) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a proxy for the request.
     *
     * @param request the request
     * @param task the download task
     * @return proxy
     * @since 0.9.0
     */
    default Proxy getProxy(Request request, Task task) {
        return this.getProxy(task);
    }

}
