# spring-virtual-threads
Demonstrates Virtual Threads using Spring

This demo invokes https://httpbin.org/delay/{seconds} endpoint.

### oha load tester
Make use of oha load tester to test this for Java Platform & Virtual Threads

oha -c 20 -n 60 http://localhost:9090/3 (3 is three seconds delay to pass to httpbin endpoint

### How to change between Platform or Virtual
Platform -> pass `false` parameter to `runWithVirtualThreads()`

Virtual -> pass `true` parameter to `runWithVirtualThreads()`
```
class AppTest {
   runWithVirtualThreads(true);
}
```
