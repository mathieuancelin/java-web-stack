# java-web-stack

Template project for an async web stack using

* Ratpack as HTTP stack
* RxJava as async utils everywhere
* OK HTTP as HTTP client
* JSR 353 for JSON handling
* Typesafe config for config


## Run

To run first build the JS part

```
cd src/main/javascript
npm install
npm run build
```

then build the services

```
./gradlew clean fatjar
```

and run it

```
java -jar ./build/libs/webstack-test-all-1.0.0.jar
open http://localhost:8886/
```

# Dev

instead of `npm run build` do `npm start` and then open `http://localhost:8886/dev`
