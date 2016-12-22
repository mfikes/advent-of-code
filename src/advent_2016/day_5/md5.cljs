(ns advent-2016.day-5.md5
  (:require [goog.crypt :as gcrypt])
  (:import (goog.crypt Md5)))

(defn md5-hex
  [s]
  (-> (doto (Md5.) (.update (gcrypt/stringToUtf8ByteArray s)))
    .digest
    gcrypt/byteArrayToHex))
