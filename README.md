# My First Plugin

An example plugin for a PaperMC Minecraft server.

## Gradle Tasks

- `runServer`
  - launch a local paper server which includes the plugin for quick testing
- `test`
  - run all unit tests
- `build`
  - build the obfuscated jar and resource pack

## Folder Structure

- `/buildSrc`
  - Hosts local gradle build scripts / plugins.
- `/gradle`
  - The standard gradle wrapper check in
- `/lib`
  - The actual plugin source + tests
