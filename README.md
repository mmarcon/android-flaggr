# Flaggr

Flaggr is a library that can be used in Android applications when there is the need for [feature flags](http://en.wikipedia.org/wiki/Feature_toggle). Flaggr helps toggling feature flags at runtime.

With Flaggr flags can be specified in a XML resource file and are overridable at runtime when the name starts with `_`.

Once the library is complete (**feel free to contribute!**) it will also be possible to toggle flags directly from the backend, via [GCM](http://developer.android.com/google/gcm/index.html) or by polling your flags API when the application starts.

For now it is possible to toggle flags directly inside the target application, e.g. via a settings screen or from a separate application (see the togglr example in the project) by sending an intent.

I have not had much time to work on this and not everything is well documented, but the 2 examples (*demo* and *togglr*) should be enough to understand most of it.

