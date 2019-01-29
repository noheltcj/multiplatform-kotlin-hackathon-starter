#!/usr/bin/env bash
set -e

./gradlew clean
./gradlew build

if ! [ -x "$(command -v lipo)" ]; then
  echo 'Error: lipo is not installed.' >&2
  exit 1
fi

mkdir -p build/native/release/Starter.framework

lipo -create \
  -output build/native/release/Starter.framework/Starter \
  native/build/konan/bin/ios_arm64/Starter.framework/Starter \
  native/build/konan/bin/ios_x64/Starter.framework/Starter\

cp -R native/build/konan/bin/ios_arm64/Starter.framework/Headers build/native/release/Starter.framework
cp -R native/build/konan/bin/ios_arm64/Starter.framework/Modules build/native/release/Starter.framework
cp native/build/konan/bin/ios_arm64/Starter.framework/Info.plist build/native/release/Starter.framework

zip -r build/Starter.zip LICENSE build/native/release

if ! [ -x "$(command -v pod)" ]; then
  echo 'Error: Coacoapods is not installed.' >&2
  exit 1
fi

pod lib lint --verbose
