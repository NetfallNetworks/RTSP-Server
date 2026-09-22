# RTSP-Server

[![Release](https://jitpack.io/v/pedroSG94/RTSP-Server.svg)](https://jitpack.io/#pedroSG94/RTSP-Server)
[![Documentation](https://img.shields.io/badge/library-documentation-orange)](https://pedroSG94.github.io/RTSP-Server)

Plugin of RootEncoder to stream directly to RTSP player.

## Compile

Require API 16+

To use this library in your project with gradle add this to your build.gradle:

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
dependencies {
  implementation 'com.github.pedroSG94:RTSP-Server:1.4.1'
  implementation 'com.github.pedroSG94.RootEncoder:library:2.7.2'
}

```

### NOTE:

The app example need min API 23+ but the library is compatible with API 16+
---

## Netfall fork notes

This fork exists to pin [NetfallNetworks/RootEncoder](https://github.com/NetfallNetworks/RootEncoder)
by commit SHA rather than tracking upstream. Two facts about that arrangement bite
often enough to be worth writing down.

### The two RootEncoder pins must stay equal

`gradle/libs.versions.toml` here pins `rootEncoderFork`. The consuming app
(NetfallNetworks/RSTP-CCTV-App) pins **both** this repo and RootEncoder directly. If its
RootEncoder pin and the one above disagree, Gradle resolves the conflict silently and the
build runs code nobody chose. Verify with `./gradlew :app:dependencies` in the app, not by
reading the toml.

So a RootEncoder change lands in three steps, in order:

1. Merge the RootEncoder PR.
2. Bump `rootEncoderFork` here (this repo). No source change is usually needed —
   `RtspServerCamera2` extends `Camera2Base`, so new methods arrive by inheritance.
3. Bump **both** pins in the app together.

### JitPack caches build failures and will not retry

Both forks are served by JitPack, which builds each commit on demand. A build that fails
is cached as an error: requesting the artifact again returns 404 without rebuilding, and
it does not clear on its own.

This has already happened once. JitPack's build of this repo failed because its own build
server was rate-limited (HTTP 429) fetching RootEncoder *from JitPack* — the dependency
had just been built, so JitPack was throttling requests to itself. The commit was
permanently unbuildable despite nothing being wrong with it.

When the app fails with `Could not find com.github.NetfallNetworks:RTSP-Server:<sha>`:

- Read the build log at
  `https://jitpack.io/com/github/NetfallNetworks/RTSP-Server/<sha>/build.log`
- If it is a transient failure, someone signed in at
  [jitpack.io](https://jitpack.io/#NetfallNetworks/RTSP-Server) must re-request that commit
- Failing that, a new commit gets a new SHA and therefore a fresh build
