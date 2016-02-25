# java-web-stack

Template project for an async web stack using

* Ratpack as HTTP stack
* RxJava as async programming model everywhere
* OKHTTP as HTTP client
* ReactiveCouchbase util libs (common, json, validation, concurrent)
* Typesafe config for config

on the JS side

* React
* Webpack
* Babel
* ESlint

## Run

To run first build the JS part

```
cd src/main/javascript
npm install
npm run build
```

then build the services

```
./gradlew clean shadowJar
# or
./gradlew clean distZip
```

and run it

```
java -jar ./build/libs/webstack-test-1.0.0-all.jar
# or
./gradlew run
# or
unzip ./build/distributions/webstack-test-1.0.0 ./build/distributions/webstack-test-1.0.0.zip
sh ./webstack-test-1.0.0/bin/webstack-test
# then
open http://localhost:8886/
```

# Dev

instead of `npm run build` do `npm start` and then open `http://localhost:8886/dev`
