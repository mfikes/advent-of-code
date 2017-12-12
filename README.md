# Advent of Code

[Advent of Code](http://adventofcode.com) in Clojure and Self-Hosted ClojureScript.

## Clojure

This is a conventional project set up for [Leiningen](https://leiningen.org) and the [Clojure CLI](https://clojure.org/guides/deps_and_cli).

## Self-Hosted ClojureScript

Makes use of [Planck](http://planck-repl.org).

1. `script/run` will launch Planck listening on a Socket REPL and with the `src` directory in its classpath, as well as the `test.check` library.
2. `(connect)` in a plain Clojure REPL will connect to Planck.
