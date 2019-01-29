#!/usr/bin/env bash
set -e

./gradlew clean
./gradlew build

if ! [ -x "$(command -v lipo)" ]; then
  echo 'Error: lipo is not installed.' >&2
  exit 1
fi

mkdir -p build/ios/release/Starter.framework

lipo -create \
  -output build/ios/release/Starter.framework/Starter \
  ios/build/konan/bin/ios_arm64/Starter.framework/Starter \
  ios/build/konan/bin/ios_x64/Starter.framework/Starter\

cp -R ios/build/konan/bin/ios_arm64/Starter.framework/Headers build/ios/release/Starter.framework
cp -R ios/build/konan/bin/ios_arm64/Starter.framework/Modules build/ios/release/Starter.framework
cp ios/build/konan/bin/ios_arm64/Starter.framework/Info.plist build/ios/release/Starter.framework

zip -r build/Starter.zip LICENSE build/ios/release

if ! [ -x "$(command -v pod)" ]; then
  echo 'Error: Coacoapods is not installed.' >&2
  exit 1
fi

pod lib lint --verbose
