#!/usr/bin/env bash
set -e

./gradlew clean
./gradlew build

if ! [ -x "$(command -v lipo)" ]; then
  echo 'Error: lipo is not installed.' >&2
  exit 1
fi

mkdir -p build/native/release/StarterCore.framework

lipo -create \
  -output build/native/release/StarterCore.framework/StarterCore \
  native/build/konan/bin/ios_arm64/StarterCore.framework/StarterCore \
  native/build/konan/bin/ios_x64/StarterCore.framework/StarterCore \

cp -R native/build/konan/bin/ios_arm64/StarterCore.framework/Headers build/native/release/StarterCore.framework
cp -R native/build/konan/bin/ios_arm64/StarterCore.framework/Modules build/native/release/StarterCore.framework
cp native/build/konan/bin/ios_arm64/StarterCore.framework/Info.plist build/native/release/StarterCore.framework

zip -r build/StarterCore.zip LICENSE build/native/release

if ! [ -x "$(command -v pod)" ]; then
  echo 'Error: Coacoapods is not installed.' >&2
  exit 1
fi

pod lib lint --verbose
