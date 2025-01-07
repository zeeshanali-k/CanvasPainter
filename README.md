[![Maven Central](https://img.shields.io/maven-central/v/tech.dev-scion/canvaspainter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22tech.dev-scion%22%20AND%20a:%22canvaspainter%22)

# CanvasPainter
A Jetpack Compose Painting Helper Library (Inspired by Drawbox) using Jetpack Compose Canvas with multiple features and flexibility. It also supprots Compose Multiform for Android, iOS, Desktop and Web (Wasm).
<p>Inspired by <a href="https://github.com/akshay2211/DrawBox">Drawbox</a></p>


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
implementation 'tech.dev-scion:canvaspainter:<latest-version>'
```
If you are using compose multiplatform add it to commonMain sourceset's dependencies section:

```kotlin
implementation("tech.dev-scion:canvaspainter:<latest-version>")
```
<p>Replace TAG with library version</p>

<p>Create <b>PainterController</b> Object directly or by using below composable.</p>

```kotlin
val painterController = rememberCanvasPainterController(
    maxStrokeWidth = 200f,
    showToolbar = true,
    Color.Red
)
```

Direct Approach

```kotlin
val painterController = remember {
      PainterController().apply {
        maxStrokeWidth = 100f //Max Stroke a user can set using stroke selection slider
        showToolbar = true,
       )
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

Other PainterController functions:
```kotlin
painterController.getCanvasAsImageBitmap() //You can get the bitmap of canvas using this function
ImageBitmap.toByteArray() //This is an extension function for ImageBitmap included in project to convert ImageBitmap to ByteArray
```

Libraries Used by CanvasPainter:
<a href="https://github.com/skydoves/colorpicker-compose" target="_blank">colorpicker-compose</a>

<a href="https://www.buymeacoffee.com/devscion"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=ZeeshanAli&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff"></a>
