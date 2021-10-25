# Constellar
![Gradle Build Workflow](https://github.com/Uranometrical/COnstellar/actions/workflows/gradle-build.yml/badge.svg)
![CodeFactor](https://www.codefactor.io/repository/github/Uranometrical/Constellar/badge)
![Repository size](https://img.shields.io/github/repo-size/Uranometrical/Constellar)
![Total lines of code](https://img.shields.io/tokei/lines/github/Uranometrical/Constellar)
![License](https://img.shields.io/github/license/Uranometrical/Constellar)
![Latest commit](https://img.shields.io/github/last-commit/Uranometrical/Constellar)

Originally meant to be a standalone PvP client with Forge support through tweaking, Constellar is a (currently only 1.8.9) Forge (and eventually Fabric) Minecraft PvP client. Constellar aims to implement a feature-rich environment with modularity as its main goal, remaining as unopinionated as possible in terms of most visuals, allowing people to tweak nearly everything to their liking. A scalable and efficient module system is planned, with the API being exposed for all through Forge.

# Contributing
Just PR lol (TO-DO).

# Building
1. Clone the repo (`git clone https://github.com/Uranometrical/Constellar && cd Constellar`)
2. Open your favorite CLI and run `gradle setupDecompWorkspace` (or your `gradle` equivalent, such as `./gradlew`, etc.)
3. Open the project with an IDE (IntelliJ or Eclipse, skip this step and move to 4 if you do not intend to contribute).
4. If you want to build, simply run `gradle build` to generate a built `.jar` file. If you want to debug and contribute, please read forward.
5. Run `gradle genIntellijRuns` if you're on IntelliJ or `gradle eclipse` if you're on Eclipse.
6. If you're on IntelliJ, you should restart your IntelliJ instance that has Constellar open.
7. Make sure the generated build module for client is `Constellar.main`.
8. You should be good to go.
