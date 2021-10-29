# Demo Spring REST Docs

This project is a simple demonstration of how to use Spring REST Docs.

## Launch

1. Clone this repository

   ```
   git clone
   ```

2. Start a MongoDB instance

   ```
   docker run -p 27017:27017 mongo
   ```
   
3. Launch `DemoSpringRestDocs` application

## Generate documentation

One single command :

```
   mvn package
```

The HTML documentation will be generated in `target/generated-docs` and the snippets in `target/generated-snippets`.