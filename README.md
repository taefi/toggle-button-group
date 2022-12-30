# Toggle-Button-Group for Vaadin

This component provides a button group for a single selection among the provided generic options.

It has API for:

- Enabling/Disabling the whole component
- Making it read-only (default: editable)
- Changing buttons to be not toggleable (default: toggleable)
- Setting the Horizontal or Vertical orientation (default: Horizontal)
- Setting any generic objects as items
- Setting label and tooltip for the component
- Support for adding value change listeners

And customizing the buttons in a functional way by:

- Providing item enabled provider
- Providing item label generator
- Providing selected item styling class generator
- Providing item icon generator
- Providing item tooltip generator
- Providing item custom identity provider
- Providing item custom order provider for presentation (preserves the order of the original items as well)

This component follows the Lumo themeing styles, so any customizations to the theme will conveniently affect this component as well.

### Local Deployment of the demo:

Starting the test/demo server:
```
mvn jetty:run
```

This deploys demo at http://localhost:8080
 
### Integration test

To run Integration Tests, execute `mvn verify -Pit,production`.
