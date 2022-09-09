[![Maven Central](https://img.shields.io/maven-central/v/tech.dev-scion/canvaspainter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22tech.dev-scion%22%20AND%20a:%22canvaspainter%22)

# CanvasPainter
A Jetpack Compose Paint app using Compose Canvas with multiple features and flexibility

## Usage
<p>Add this to your project level "build.gradle" or in newer versions of gradle in "settings.gradle" under repositories section:</p>

 ```groovy
repositories {
 mavenCentral()
}
```
<p>Add this to your module level build.gradle file:</p>

```groovy
implementation 'tech.dev-scion:canvaspainter:TAG'
```
<p>Replace TAG with library version</p>

<p>Create ```PainterController``` Object.</p>
```kotlin
val painterController = remember {
      PainterController().apply {
        maxStrokeWidth = 100f
        showToolbar = true
        storageOptions = StorageOptions(
            "My Painter",
            shouldSaveByDefault = true
        )
     }
  }
```

<p>Add CanvasPainter composabel to your layout</p>
```kotlin
CanvasPainter(
      Modifier.fillMaxSize(),
      painterController
   )
```
