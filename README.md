# kamon-on-play-27
Example web app with `Play 2.7` and `Kamon 2.x`.

### Start on PROD mode
Run the app using the class `play.core.server.ProdServerStart` as entry point and pass kanela agent by providing the JVM option: `-javaagent:path/to/kanela-agent-{version}.jar` 

**JVM params example for easier setting up:**
```bash
-Dpidfile.path=/dev/null -javaagent:~/.ivy2/cache/io.kamon/kanela-agent/jars/kanela-agent-1.0.0-RC2.jar
```

### Kamon status view
While the app is running you can easily check the kamon instrumentation status by opening on your browser `http://localhost:5266/`.

### Feature of Kamon introduced
* Propagate HTTP headers.
* Generate new tag value if it's not provided by the request headers (implemented by class `support.kamon.HeadersPropagation`).
* Expose tags on `logback`.

### Requests examples

```bash
curl --request GET \
  --url http://localhost:9000/dummy/outgoing-request \
  --header 'x-custom-trace-id: cspinetta-trace-id-1' \
  --header 'x-forwarded-for: cspinetta-laptop-2'
```

### TODO
* Propagate HTTP headers in DEV mode.
  * If the config requires load a class, it fails because the class loader used by Kanela can't see it
  * For any another change, the new config is not visible for `kamon-instrumentation-common`.
* Return HTTP tags on response headers.
* Logback prints old tags (to reproduce the issue, just send 2 requests with different x-custom-trace-id and you'll see the first one header print in the logs of both requests)
