# kamon-on-play-27
Example web app with `Play 2.7` and `Kamon 2.x`.

### Start on PROD mode
Run the app using the class `play.core.server.ProdServerStart` as entry point and pass kanela agent by providing the JVM option: `-javaagent:path/to/kanela-agent-{version}.jar` 

### Kamon status
When the app is up you can check kamon status by opening on your browser `http://localhost:5266/`.


### TODO
* Propagation of HTTP headers in DEV mode (it seems to require a change on `kamon-instrumentation-common`).
* Return HTTP tags on response headers.
* Generate new tag value if it's not provided by the request.
