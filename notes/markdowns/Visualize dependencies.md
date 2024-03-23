## Visualize dependencies:
#### Note: graphviz must be installed in order to run following command
https://github.com/ferstl/depgraph-maven-plugin
```bash
mvn com.github.ferstl:depgraph-maven-plugin:aggregate -DcreateImage=true -DreduceEdges=false -Dscope=compile "-Dincludes=com.food.ordering.system*:*"
```

mvn dependency:tree
