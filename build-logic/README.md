# Build Logic

The Build Logic useful to centralise task management via convention plugin.

https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html#sec:convention_plugins

Cross project configuration, done through subprojects {} and allprojects {} DSL constructs, 
is a discouraged way to share build logic between subprojects. 
It can make the build logic less clear, prone to complexity, and create maintenance challenges,
potentially affecting project optimizations.
