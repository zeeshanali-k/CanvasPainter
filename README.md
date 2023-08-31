[![Maven Central](https://img.shields.io/maven-central/v/tech.dev-scion/canvaspainter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22tech.dev-scion%22%20AND%20a:%22canvaspainter%22)

# CanvasPainter
A Jetpack Compose Painting Helper Library (Inspired by Drawbox) using Jetpack Compose Canvas with multiple features and flexibility
Inspired by <a href="https://github.com/akshay2211/DrawBox">Drawbox</a>


<img src="/screenshots/ss_1.png" width="250" height="500"> <img src="/screenshots/ss_2.png" width="250" height="500">
<img src="/screenshots/ss_3.png" width="250" height="500">

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

<p>Create <b>PainterController</b> Object directly or by using below composable.</p>

```kotlin
val painterController = rememberCanvasPainterController(
    maxStrokeWidth = 200f,
    showToolbar = true, storageOptions = StorageOptions(shouldSaveByDefault = false),//setting false will return bitmap in below callback
    Color.Red
) {
 // This will only be called when you set "shouldSaveByDefault = false" in storage options
}
```

Direct Approach

```kotlin
val painterController = remember {
      PainterController().apply {
        maxStrokeWidth = 100f //Max Stroke a user can set using stroke selection slider
        showToolbar = true
        //Optional
        storageOptions = StorageOptions( 
            "My Painter", //Your directory name where images should be saved
            shouldSaveByDefault = true // "true" means you want to save image on clicking save and "false" want a bitmap returned when clicked save
        ),
        onBitmapGenerated = {
          //Bitmap is returned here. This will only be called when you set "shouldSaveByDefault = false" in storage options
        })
     }
  }
```

<p>Add CanvasPainter composable to your layout</p>

```kotlin
CanvasPainter(
      Modifier.fillMaxSize(),
      painterController
   )
```
