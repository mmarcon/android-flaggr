# Flaggr

Flaggr is a library that can be used in Android applications when there is the need for [feature flags](http://en.wikipedia.org/wiki/Feature_toggle). Flaggr helps toggling feature flags at runtime.

With Flaggr flags can be specified in a XML resource file and are overridable at runtime when the name starts with `_`.

Once the library is complete (**feel free to contribute!**) it will also be possible to toggle flags directly from the backend, via [GCM](http://developer.android.com/google/gcm/index.html) or by polling your flags API when the application starts.

For now it is possible to toggle flags directly inside the target application, e.g. via a settings screen or from a separate application (see the togglr example in the project) by sending an intent.

I have not had much time to work on this and not everything is well documented, but the 2 examples (*demo* and *togglr*) should be enough to understand most of it.

## MIT License

Copyright (c) 2014 Massimiliano Marcon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
