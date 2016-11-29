(ns uglybird.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [uglybird.core-test]))

(doo-tests 'uglybird.core-test)
