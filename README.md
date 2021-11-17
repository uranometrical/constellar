# Constellar
![Gradle Build Workflow](https://github.com/Uranometrical/COnstellar/actions/workflows/gradle-build.yml/badge.svg)
![CodeFactor](https://www.codefactor.io/repository/github/Uranometrical/Constellar/badge)
![Repository size](https://img.shields.io/github/repo-size/Uranometrical/Constellar)
![Total lines of code](https://img.shields.io/tokei/lines/github/Uranometrical/Constellar)
![License](https://img.shields.io/github/license/Uranometrical/Constellar)
![Latest commit](https://img.shields.io/github/last-commit/Uranometrical/Constellar)

Experimental MC 1.8.9 PvP client that isn't necessarily focused only on Hypixel (unheard of).

# Contributing
Just PR lol (TO-DO).

# Building
Below are the steps for building. Follow the "Universal" steps, then Standalone/Forge.

`.jar` files produced follow the following format: `Constellar-version-target.jar` and `Constellar.version-target-dep.jar`. Use the `-dep` `.jar` file.

### Universal
1. Clone the repo (`git clone https://github.com/Uranometrical/Constellar && cd Constellar`)
2. Open the project with an IDE (I recommend IntelliJ). Ensure Gradle 4 is being used (provided in `gradle` folder).
3. Run `gradle setupDecompWorkspace`

### Standalone (Mixin Client)
1. Change the `constellar_build_target` property in `gradle.properties` to `standalone`.
2. Ensure Gradle has been refreshed if th ebuild target had to be changed.
3. Run `gradle genIntellijRuns` if you're on IntelliJ.
4. Make sure the generated build module for client is `Constellar.main`.
![image](https://user-images.githubusercontent.com/45357714/142085884-95d34046-a7e6-4b09-b7a5-de675eae668c.png)

4. Make sure to reload all Gradle projects.  
![image](https://user-images.githubusercontent.com/45357714/142085841-dcdc8073-3d2e-4099-b5a1-b53e628f48eb.png)

### Forge (Mod)
1. Change the `constellar_build_target` property in `gradle.properties` to `forge`.
2. Ensure Gradle has been refreshed if the build target had to be changed.
3. Run the `build` Gradle task to generate a `.jar` to use as a mod.
