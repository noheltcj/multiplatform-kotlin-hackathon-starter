# Multiplatform Kotlin Hackathon Starter
This project is a starting point for sharing core and presentation logic between iOS and Android.

## Project Structure
The top level project can be imported into Android Studio or IntelliJ as a gradle project.

### common
This contains all the kotlin code to be shared with the other projects.

### native
Used to configure the native output (usually frameworks), extend functionality specific to native platforms, and provide `actual` classes `expect`ed by the common module.

### jvm
Used to configure/publish the output jar, extend jvm functionality, and provide `actual` classes `expect`ed by the common module.

### js
Used to configure/publish the output jar (yes, it's still a jar), extend javascript functionality, and provide `actual` classes `expect`ed by the common module.
